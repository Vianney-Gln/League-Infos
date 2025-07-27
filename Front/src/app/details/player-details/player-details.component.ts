import { Component, OnDestroy, Signal, signal, WritableSignal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayersService } from '../../services/players/players.service';
import { catchError, map, of, Subscription, switchMap, tap } from 'rxjs';
import { SummonerDTO } from '../../common/models/summonerDTO';
import { GetVersionsService } from '../../services/versions/get-versions.service';
import { LeagueEntryDTO } from '../../common/models/LeagueEntryDTO';
import { CommonModule } from '@angular/common';
import { ChampionMasteryDto } from '../../common/models/ChampionMasteryDto';
import { QueueTypeEnum } from '../../common/constants/queueTypeEnum';
import { HistoryService } from '../../services/games-history/history.service';
import { MatchDTO, ParticipantMatchDTO } from '../../common/models/games-history/matchDTO';
import { HistoryItemsComponent } from '../../games-history/history-items/history-items.component';

@Component({
  selector: 'app-player-details',
  standalone: true,
  imports: [CommonModule, HistoryItemsComponent],
  templateUrl: './player-details.component.html',
  styleUrl: './player-details.component.scss',
})
export class PlayerDetailsComponent implements OnDestroy {
  constructor(
    private route: ActivatedRoute,
    private playerService: PlayersService,
    private versionService: GetVersionsService,
    private router: Router,
    private historyService: HistoryService
  ) {}

  gameName: string | undefined = '';
  summonerDto: SummonerDTO = new SummonerDTO();
  lastVersionLolSignal: Signal<string> = signal('');
  leagueEntriesSignal: WritableSignal<LeagueEntryDTO[]> = signal([]);
  championMasteriesSignal: WritableSignal<ChampionMasteryDto[]> = signal([]);
  urlBackgroundBannerSignal: WritableSignal<string> = signal('');
  currentMatchsParticipantsSoloQSignal: WritableSignal<ParticipantMatchDTO[]> = signal([]);
  currentMatchsParticipantsFlexQSignal: WritableSignal<ParticipantMatchDTO[]> = signal([]);
  isUnrankedFlex = false;
  isUnrankedSoloQ = false;
  tagLine: string = '';
  isLoading = false;

  displayInfosPlayerSubscription: Subscription = new Subscription();
  gamesHistorySubscription: Subscription = new Subscription();

  RANKED_SOLO_5x5 = { libelle: QueueTypeEnum.RANKED_SOLO_5x5, code: 420 };
  RANKED_FLEX_SR = { libelle: QueueTypeEnum.RANKED_FLEX_SR, code: 440 };

  ngOnInit() {
    this.lastVersionLolSignal = this.versionService.lastVersionlolDTOSignal;
    this.isLoading = true;
    this.getDisplayedPlayerInfos();
  }

  private getDataPlayerdetail(riotId: string) {
    let gameName: string = '';
    let tagLine: string = '';
    if (riotId) {
      [gameName, tagLine] = riotId.split('#');
      this.tagLine = tagLine;
    }
    if (gameName && tagLine) {
      return this.playerService.getAccountByRiotId(gameName, tagLine).pipe(
        switchMap((account) => {
          this.gameName = account.gameName;
          return this.playerService.getSummonerByPuuid(account.puuid);
        }),
        catchError(() => {
          this.gameName = '';
          return of(new SummonerDTO());
        })
      );
    }
    return of();
  }

  computeTierEmblem(tier: string): string {
    let urlEmblem = '';
    switch (tier) {
      case 'IRON':
        urlEmblem = 'images/Emblems/emblem-iron.png';
        break;
      case 'BRONZE':
        urlEmblem = 'images/Emblems/emblem-bronze.png';
        break;
      case 'SILVER':
        urlEmblem = 'images/Emblems/emblem-silver.png';
        break;
      case 'GOLD':
        urlEmblem = 'images/Emblems/emblem-gold.png';
        break;
      case 'PLATINUM':
        urlEmblem = 'images/Emblems/emblem-platinum.png';
        break;
      case 'EMERALD':
        urlEmblem = 'images/Emblems/emblem-emerald.png';
        break;
      case 'DIAMOND':
        urlEmblem = 'images/Emblems/emblem-diamond.png';
        break;
      case 'MASTER':
        urlEmblem = 'images/Emblems/emblem-master.png';
        break;
      case 'GRANDMASTER':
        urlEmblem = 'images/Emblems/emblem-grandmaster.png';
        break;
      case 'CHALLENGER':
        urlEmblem = 'images/Emblems/emblem-challenger.png';
        break;
    }
    return urlEmblem;
  }

  private getDisplayedPlayerInfos(): void {
    this.displayInfosPlayerSubscription = this.route.paramMap
      .pipe(
        switchMap((param) => {
          const riotId = param.get('summoner');
          return this.getDataPlayerdetail(riotId ? riotId : '');
        }),
        catchError(() => {
          this.gameName = '';
          return of(new SummonerDTO());
        })
      )
      .pipe(
        switchMap((summoner: SummonerDTO) => {
          this.summonerDto = summoner;
          if (!summoner.puuid) {
            this.router.navigate(['/NotFound']);
          }
          return this.playerService.getLeagueEntryByPuuid(summoner.puuid).pipe(map((leagueEntries) => ({ leagueEntries, puuid: summoner.puuid })));
        })
      )
      .pipe(
        switchMap(({ leagueEntries, puuid }) => {
          this.leagueEntriesSignal.set(leagueEntries);
          this.isUnrankedFlex = leagueEntries.find((entry) => entry.queueType === QueueTypeEnum.RANKED_FLEX_SR) ? false : true;
          this.isUnrankedSoloQ = leagueEntries.find((entry) => entry.queueType === QueueTypeEnum.RANKED_SOLO_5x5) ? false : true;
          return this.playerService.getChampionMasteriesDTO(puuid);
        })
      )
      .subscribe({
        next: (championMasteries) => {
          this.isLoading = false;
          this.championMasteriesSignal.set(championMasteries);
          this.resetListHistoryItems();
          if (championMasteries.length) {
            this.urlBackgroundBannerSignal.set(`url(https://lolg-cdn.porofessor.gg/img/d/champion-banners/${championMasteries[0].championId}.jpg)`);
          } else {
            this.urlBackgroundBannerSignal.set(`url(images/default-banner.png)`);
          }
        },
        error: (error) => {
          console.log(error);
          this.isLoading = false;
        },
      });
  }

  private resetListHistoryItems() {
    this.currentMatchsParticipantsFlexQSignal.set([]);
    this.currentMatchsParticipantsSoloQSignal.set([]);
  }

  private computeQueueId(queueType: string): number {
    let queueId = 0;
    if (this.RANKED_SOLO_5x5.libelle === queueType) {
      queueId = this.RANKED_SOLO_5x5.code;
    }
    if (this.RANKED_FLEX_SR.libelle === queueType) {
      queueId = this.RANKED_FLEX_SR.code;
    }
    return queueId;
  }

  private getCurrentMatchParticipant(matchDTO: MatchDTO[]): ParticipantMatchDTO[] {
    return matchDTO
      .map((match) => {
        const participant = match.info.participants.find((participant) => participant.puuid === this.summonerDto.puuid);
        participant!.matchId = match.metadata.matchId;
        return participant;
      })
      .filter((participant): participant is ParticipantMatchDTO => participant !== undefined);
  }

  public getHistory(queueType: string) {
    if (queueType === this.RANKED_SOLO_5x5.libelle && this.currentMatchsParticipantsSoloQSignal().length) {
      return;
    }
    if (queueType === this.RANKED_FLEX_SR.libelle && this.currentMatchsParticipantsFlexQSignal().length) {
      return;
    }
    this.gamesHistorySubscription = this.historyService.getHistoryByPuuidAndQueueType(this.summonerDto.puuid, this.computeQueueId(queueType)).subscribe({
      next: (res: MatchDTO[]) => {
        this.historyService.listMatchDataSignal.set(res);
        const queueType = res[0].info.queueId;
        if (queueType === this.RANKED_SOLO_5x5.code) {
          this.currentMatchsParticipantsSoloQSignal.set(this.getCurrentMatchParticipant(res));
        }
        if (queueType === this.RANKED_FLEX_SR.code) {
          this.currentMatchsParticipantsFlexQSignal.set(this.getCurrentMatchParticipant(res));
        }
      },
      error: (err) => console.log(err),
    });
  }

  ngOnDestroy(): void {
    if (this.displayInfosPlayerSubscription) {
      this.displayInfosPlayerSubscription.unsubscribe();
    }

    if (this.gamesHistorySubscription) {
      this.gamesHistorySubscription.unsubscribe();
    }
  }
}
