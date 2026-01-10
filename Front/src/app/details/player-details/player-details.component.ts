import { ChangeDetectionStrategy, Component, computed, OnDestroy, Signal, signal, WritableSignal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayersService } from '../../services/players/players.service';
import { catchError, concatMap, finalize, map, of, Subscription, switchMap, takeUntil, tap, throwError } from 'rxjs';
import { SummonerDTO } from '../../common/models/summonerDTO';
import { GetVersionsService } from '../../services/versions/get-versions.service';
import { LeagueEntryDTO } from '../../common/models/LeagueEntryDTO';
import { CommonModule } from '@angular/common';
import { ChampionMasteryDto } from '../../common/models/ChampionMasteryDto';
import { QueueTypeEnum } from '../../common/constants/queueTypeEnum';
import { HistoryService } from '../../services/games-history/history.service';
import { MatchDTO, ParticipantMatchDTO } from '../../common/models/games-history/matchDTO';
import { HistoryItemsComponent } from '../../games-history/history-items/history-items.component';
import { NO_MORE_HISTORIQUE_GAME } from '../../common/constants/errors';
import { TranslateFillPipe } from '../../common/pipes/translate-fill.pipe';

@Component({
  selector: 'app-player-details',
  standalone: true,
  imports: [CommonModule, HistoryItemsComponent, TranslateFillPipe],
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

  RANKED_SOLO_5x5 = { libelle: QueueTypeEnum.RANKED_SOLO_5x5, code: 420 };
  RANKED_FLEX_SR = { libelle: QueueTypeEnum.RANKED_FLEX_SR, code: 440 };

  gameName: string | undefined = '';
  summonerDto: SummonerDTO = new SummonerDTO();
  lastVersionLolSignal: Signal<string> = signal('');
  leagueEntriesSignal: WritableSignal<LeagueEntryDTO[]> = signal([]);
  leagueEntry: WritableSignal<LeagueEntryDTO | null> = signal(null);
  queueTypeSignal: WritableSignal<string> = signal(QueueTypeEnum.RANKED_SOLO_5x5);
  leagueEntrySoloQSignal = computed(() => this.leagueEntriesSignal().find((entry) => entry.queueType === QueueTypeEnum.RANKED_SOLO_5x5));
  leagueEntryFlexQSignal = computed(() => this.leagueEntriesSignal().find((entry) => entry.queueType === QueueTypeEnum.RANKED_FLEX_SR));
  nbGameSoloQPlayed = computed(() => this.leagueEntrySoloQSignal()?.wins! + this.leagueEntrySoloQSignal()?.losses!);
  nbGameFlexQPlayed = computed(() => this.leagueEntryFlexQSignal()?.wins! + this.leagueEntryFlexQSignal()?.losses!);
  winRateSoloQ = computed(() => this.calculateWinRate(this.leagueEntrySoloQSignal()?.wins!, this.nbGameSoloQPlayed()));
  winRateFlexQ = computed(() => this.calculateWinRate(this.leagueEntryFlexQSignal()?.wins!, this.nbGameFlexQPlayed()));

  listMatchDataFlexQSignal: WritableSignal<MatchDTO[]> = signal([]);
  listMatchDataSoloQSignal: WritableSignal<MatchDTO[]> = signal([]);

  championMasteriesSignal: WritableSignal<ChampionMasteryDto[]> = signal([]);
  urlBackgroundBannerSignal: WritableSignal<string> = signal('');
  currentMatchsParticipantsSoloQSignal: WritableSignal<ParticipantMatchDTO[]> = signal([]);
  currentMatchsParticipantsFlexQSignal: WritableSignal<ParticipantMatchDTO[]> = signal([]);
  isUnrankedFlex = false;
  isUnrankedSoloQ = false;
  tagLine: string = '';
  isLoading: WritableSignal<boolean> = signal(false);
  isGetMoreButtonSoloQVisibleSignal: WritableSignal<boolean> = signal(true);
  isGetMoreButtonFlexQVisibleSignal: WritableSignal<boolean> = signal(true);
  noMoreHistoriqueMessageSoloQSignal: WritableSignal<string> = signal('');
  noMoreHistoriqueMessageFlexQSignal: WritableSignal<string> = signal('');

  displayInfosPlayerSubscription: Subscription = new Subscription();
  gamesHistorySubscription: Subscription = new Subscription();

  ngOnInit() {
    this.isLoading.set(true);
    this.lastVersionLolSignal = this.versionService.lastVersionlolDTOSignal;
    this.getDisplayedPlayerInfos();
    this.route.paramMap.subscribe({
      next: (params) => {
        this.initGetMoreButtonAndMessageState();
        const summonerId = params.get('summoner');
        if (summonerId) {
          this.isLoading.set(true);
          this.initGetMoreButtonAndMessageState();
        }
      },
      error: () => {
        console.log('error');
      },
    });
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

  computeTierEmblem(tier: string | undefined): string {
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
      default:
        '';
    }
    return urlEmblem;
  }

  private getDisplayedPlayerInfos(): void {
    this.displayInfosPlayerSubscription = this.route.paramMap
      .pipe(
        concatMap((param) => {
          const riotId = param.get('summoner');
          return this.getDataPlayerdetail(riotId ? riotId : '');
        }),
        catchError(() => {
          this.gameName = '';
          return of(new SummonerDTO());
        }),
        concatMap((summoner: SummonerDTO) => {
          this.summonerDto = summoner;
          if (!summoner.puuid) {
            this.router.navigate(['/NotFound']);
          }
          return this.playerService.getLeagueEntryByPuuid(summoner.puuid).pipe(map((leagueEntries) => ({ leagueEntries, puuid: summoner.puuid })));
        }),
        concatMap(({ leagueEntries, puuid }) => {
          this.leagueEntriesSignal.set(leagueEntries);
          this.isUnrankedFlex = leagueEntries.find((entry) => entry.queueType === QueueTypeEnum.RANKED_FLEX_SR) ? false : true;
          this.isUnrankedSoloQ = leagueEntries.find((entry) => entry.queueType === QueueTypeEnum.RANKED_SOLO_5x5) ? false : true;
          return this.playerService.getChampionMasteriesDTO(puuid).pipe(map((championMasteries) => ({ championMasteries, puuid })));
        }),
        concatMap(({ championMasteries }) => {
          this.championMasteriesSignal.set(championMasteries);
          this.resetListHistoryItems();
          if (championMasteries.length) {
            this.urlBackgroundBannerSignal.set(`url(https://lolg-cdn.porofessor.gg/img/d/champion-banners/${championMasteries[0].championId}.jpg)`);
          } else {
            this.urlBackgroundBannerSignal.set(`url(images/default-banner.png)`);
          }
          return this.getHistory(this.RANKED_SOLO_5x5.libelle);
        }),
        concatMap((res: MatchDTO[]) => {
          this.listMatchDataSoloQSignal.set(res);
          this.currentMatchsParticipantsSoloQSignal.set(this.getCurrentMatchParticipant(res));
          return this.getHistory(this.RANKED_FLEX_SR.libelle);
        })
      )
      .subscribe({
        next: (res: MatchDTO[]) => {
          this.listMatchDataFlexQSignal.set(res);
          this.currentMatchsParticipantsFlexQSignal.set(this.getCurrentMatchParticipant(res));
          this.isLoading.set(false);
        },
        error: (error) => {
          this.isLoading.set(false);
          console.log(error);
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

  private getHistory(queueType: string) {
    return this.historyService.getHistoryByPuuidAndQueueType(this.summonerDto.puuid, this.computeQueueId(queueType));
  }

  onSwitch(queue: string) {
    this.queueTypeSignal.set(queue);
  }

  moreHistory(queueType: string) {
    const queueId = this.computeQueueId(queueType);
    const currGamesByQueue = queueId === this.RANKED_FLEX_SR.code ? this.listMatchDataFlexQSignal() : this.listMatchDataSoloQSignal();

    let olderGame = null;
    if (currGamesByQueue.length) {
      olderGame = currGamesByQueue.reduce((prev, current) => {
        return prev.info.gameCreation < current.info.gameCreation ? prev : current;
      });
    }

    if (!olderGame) {
      return;
    }

    this.historyService.getMoreHistory(this.summonerDto.puuid, olderGame.info.gameCreation, this.computeQueueId(queueType)).subscribe({
      next: (res: MatchDTO[]) => {
        if (!res.length) {
          if (queueId === this.RANKED_SOLO_5x5.code) {
            this.isGetMoreButtonSoloQVisibleSignal.set(false);
            this.noMoreHistoriqueMessageSoloQSignal.set(NO_MORE_HISTORIQUE_GAME);
          }

          if (queueId === this.RANKED_FLEX_SR.code) {
            this.isGetMoreButtonFlexQVisibleSignal.set(false);
            this.noMoreHistoriqueMessageFlexQSignal.set(NO_MORE_HISTORIQUE_GAME);
          }

          return;
        }

        if (queueId === this.RANKED_SOLO_5x5.code) {
          this.listMatchDataSoloQSignal.update((prev) => [...prev, ...res]);
          this.currentMatchsParticipantsSoloQSignal.set(this.getCurrentMatchParticipant(this.listMatchDataSoloQSignal()));
        }
        if (queueId === this.RANKED_FLEX_SR.code) {
          this.listMatchDataFlexQSignal.update((prev) => [...prev, ...res]);
          this.currentMatchsParticipantsFlexQSignal.set(this.getCurrentMatchParticipant(this.listMatchDataFlexQSignal()));
        }
      },
    });
  }

  private initGetMoreButtonAndMessageState() {
    this.isGetMoreButtonFlexQVisibleSignal.set(true);
    this.noMoreHistoriqueMessageFlexQSignal.set('');
    this.isGetMoreButtonSoloQVisibleSignal.set(true);
    this.noMoreHistoriqueMessageSoloQSignal.set('');
  }

  isMultipleOf10(value: number) {
    return value !== 0 && value % 10 === 0;
  }

  private calculateWinRate(nbVictory: number, nbTotalGame: number) {
    return ((nbVictory / nbTotalGame) * 100).toFixed(2);
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
