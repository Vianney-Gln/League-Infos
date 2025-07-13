import { Component, computed, effect, OnDestroy, OnInit, Signal, signal, ViewEncapsulation, WritableSignal } from '@angular/core';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';
import { PlayersService } from '../../services/players/players.service';
import { LeagueItemDTO, LeagueListDTO } from '../../common/models/leagueListDTO';
import { forkJoin, map, Subscription, switchMap, tap } from 'rxjs';
import { AccountDTO } from '../../common/models/accountDTO';
import { SummonerDTO } from '../../common/models/summonerDTO';
import { GetVersionsService } from '../../services/versions/get-versions.service';
import { CommonModule } from '@angular/common';
import { ChampionMasteryDto } from '../../common/models/ChampionMasteryDto';
import { Router } from '@angular/router';
import { GetChampionsService } from '../../services/champions/get-champions.service';
import { Champion, ChampionData } from '../../common/models/championsInfos';

@Component({
  selector: 'app-hero-component',
  standalone: true,
  imports: [NgbCarouselModule, CommonModule],
  templateUrl: './hero.component.html',
  styleUrl: './hero.component.scss',
  encapsulation: ViewEncapsulation.None,
})
export class HeroComponent implements OnInit, OnDestroy {
  leagueChallengerSoloQSubscription: Subscription | null = null;
  leagueChallengerFlexSubscription: Subscription | null = null;
  getAccountSubscription: Subscription | null = null;

  firstChallengerPlayerSoloQSignal: Signal<LeagueItemDTO | undefined> = signal<LeagueItemDTO | undefined>(undefined);
  firstChallengerSummonerSoloQSignal: WritableSignal<SummonerDTO | undefined> = signal<SummonerDTO | undefined>(undefined);
  firstChallengerSummonerFlexSignal: WritableSignal<SummonerDTO | undefined> = signal<SummonerDTO | undefined>(undefined);
  firstChallengerPlayerFlexSignal: Signal<LeagueItemDTO | undefined> = signal<LeagueItemDTO | undefined>(undefined);
  lastVersionLolSignal: Signal<string> = signal('');
  lastTwentyVersionsLolSignal: Signal<string[]> = signal([]);
  mostRecentChampionDtoSignal: WritableSignal<Champion | undefined> = signal(undefined);

  leagueBestPlayerSoloQ: string | undefined = '';
  leagueBestPlayerFlex: string | undefined = '';
  gameNameBestChalPlayerSoloQ: string | undefined = '';
  gameNameBestChalPlayerFlex: string | undefined = '';
  tagLineChalFlexPlayer: string | undefined = '';
  tagLineChalSoloQPlayer: string | undefined = '';
  isCarouselLoading: boolean = false;
  isCarouselError: boolean = false;

  urlBackgroundBannerSoloQSignal: WritableSignal<string> = signal('');
  urlBackgroundBannerFlexQSignal: WritableSignal<string> = signal('');
  urlBackgroundMostRecentChampion: WritableSignal<string> = signal('');

  NB_PREVIOUS_VERSIONS = 20;

  constructor(
    private playersService: PlayersService,
    private getVersionsService: GetVersionsService,
    private router: Router,
    private getChampionsService: GetChampionsService
  ) {
    effect(() => {
      this.lastVersionLolSignal = this.getVersionsService.lastVersionlolDTOSignal;
      this.lastTwentyVersionsLolSignal = this.getVersionsService.lastTwentyVersionslolSignal;
      this.getMostRecentChampion();
    });
  }

  ngOnInit(): void {
    this.getDataBestSoloqPlayer();
    this.getDataBestFlexPlayer();
  }

  private getDataBestSoloqPlayer(): void {
    this.isCarouselLoading = true;
    this.leagueChallengerSoloQSubscription = this.playersService
      .getLeagueChallengerDataSoloQ()
      .pipe(
        tap((leagueListDTO) => {
          this.playersService.leagueChallengerSoloQListDTOSignal.set(leagueListDTO);
        }),
        switchMap((leagueListDTO) => {
          this.leagueBestPlayerSoloQ = this.playersService.leagueChallengerSoloQListDTOSignal()?.tier;
          this.firstChallengerPlayerSoloQSignal = computed(() => this.playersService.leagueChallengerSoloQListDTOSignal()?.entries[0]);
          return this.playersService.getAccountByPuuid(leagueListDTO.entries[0].puuid);
        }),
        switchMap((account: AccountDTO) => {
          this.gameNameBestChalPlayerSoloQ = account.gameName;
          this.tagLineChalSoloQPlayer = account.tagLine;
          return this.playersService.getSummonerByPuuid(account.puuid);
        }),
        switchMap((summoner: SummonerDTO) => {
          return this.playersService
            .getChampionMasteriesDTO(summoner.puuid)
            .pipe(map((championMasteries: ChampionMasteryDto[]) => ({ championMasteries, summoner })));
        })
      )
      .subscribe({
        next: ({ championMasteries, summoner }) => {
          this.isCarouselLoading = false;
          this.firstChallengerSummonerSoloQSignal.set(summoner);
          if (championMasteries.length) {
            this.urlBackgroundBannerSoloQSignal.set(`url(https://lolg-cdn.porofessor.gg/img/d/champion-banners/${championMasteries[0].championId}.jpg)`);
          } else {
            this.urlBackgroundBannerSoloQSignal.set(`url(images/default-banner.png)`);
          }
        },
        error: (err) => {
          console.log(err);
          this.isCarouselLoading = false;
          this.isCarouselError = true;
        },
      });
  }

  private getDataBestFlexPlayer(): void {
    this.isCarouselLoading = true;
    this.leagueChallengerFlexSubscription = this.playersService
      .getLeagueChallengerDataFlexQ()
      .pipe(
        tap((leagueListDTO) => {
          this.playersService.leagueChallengerFlexListDTOSignal.set(leagueListDTO);
        }),
        switchMap((leagueListDTO) => {
          this.leagueBestPlayerFlex = this.playersService.leagueChallengerFlexListDTOSignal()?.tier;
          this.firstChallengerPlayerFlexSignal = computed(() => this.playersService.leagueChallengerFlexListDTOSignal()?.entries[0]);
          return this.playersService.getAccountByPuuid(leagueListDTO.entries[0].puuid);
        }),
        switchMap((account: AccountDTO) => {
          this.gameNameBestChalPlayerFlex = account.gameName;
          this.tagLineChalFlexPlayer = account.tagLine;
          return this.playersService.getSummonerByPuuid(account.puuid);
        }),
        switchMap((summoner: SummonerDTO) => {
          return this.playersService
            .getChampionMasteriesDTO(summoner.puuid)
            .pipe(map((championMasteries: ChampionMasteryDto[]) => ({ championMasteries, summoner })));
        })
      )
      .subscribe({
        next: ({ championMasteries, summoner }) => {
          this.isCarouselLoading = false;
          this.firstChallengerSummonerFlexSignal.set(summoner);
          if (championMasteries.length) {
            this.urlBackgroundBannerFlexQSignal.set(`url(https://lolg-cdn.porofessor.gg/img/d/champion-banners/${championMasteries[0].championId}.jpg)`);
          } else {
            this.urlBackgroundBannerFlexQSignal.set(`url(images/default-banner.png)`);
          }
        },
        error: (err) => {
          console.log(err);
          this.isCarouselLoading = false;
          this.isCarouselError = true;
        },
      });
  }

  goToPlayerDataLeagueEntries(gameName: string, tagLine: string) {
    const url = `Detail-summoner/${gameName}#${tagLine}`;
    this.router.navigate([url]);
  }

  private getMostRecentChampion(): void {
    const observables = this.lastTwentyVersionsLolSignal().map((version) => {
      return this.getChampionsService.getAllChampionsInfos(version);
    });

    forkJoin(observables).subscribe({
      next: (listChampData: ChampionData[]) => {
        const listMostRecentsChampions: [string, Champion][] = [];
        if (listChampData.length === this.NB_PREVIOUS_VERSIONS) {
          listChampData.forEach((champData, index) => {
            if (index > 0) {
              const prevChampData = listChampData[index - 1];
              const currentChampCount = Object.keys(champData.data).length;
              const prevChampCount = Object.keys(prevChampData.data).length;
              if (prevChampCount > currentChampCount) {
                const missingChampions = Object.keys(prevChampData.data).filter((champKey) => !(champKey in champData.data));
                const foundChampion = Object.entries(prevChampData.data).find(([, champion]) => champion.name === missingChampions[0]);
                if (foundChampion) {
                  console.log(foundChampion);
                  listMostRecentsChampions.push(foundChampion);
                }
              }
            }
          });
          this.mostRecentChampionDtoSignal.set(listMostRecentsChampions[0][1]);
          this.urlBackgroundMostRecentChampion.set(`url(https://lolg-cdn.porofessor.gg/img/d/champion-banners/${this.mostRecentChampionDtoSignal()?.key}.jpg)`);
        }
      },
      error: (err) => console.log(err),
    });
  }

  ngOnDestroy(): void {
    if (this.leagueChallengerSoloQSubscription) {
      this.leagueChallengerSoloQSubscription.unsubscribe();
    }

    if (this.leagueChallengerFlexSubscription) {
      this.leagueChallengerFlexSubscription.unsubscribe();
    }
  }
}
