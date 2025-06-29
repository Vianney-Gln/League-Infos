import { Component, computed, OnDestroy, OnInit, Signal, signal, ViewEncapsulation, WritableSignal } from '@angular/core';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';
import { PlayersService } from '../../services/players/players.service';
import { LeagueItemDTO, LeagueListDTO } from '../../common/models/leagueListDTO';
import { map, Subscription, switchMap, tap } from 'rxjs';
import { AccountDTO } from '../../common/models/accountDTO';
import { SummonerDTO } from '../../common/models/summonerDTO';
import { GetVersionsService } from '../../services/versions/get-versions.service';
import { CommonModule } from '@angular/common';
import { ChampionMasteryDto } from '../../common/models/ChampionMasteryDto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hero-component',
  standalone: true,
  imports: [NgbCarouselModule, CommonModule],
  templateUrl: './hero.component.html',
  styleUrl: './hero.component.scss',
  encapsulation: ViewEncapsulation.None,
})
export class HeroComponent implements OnInit, OnDestroy {
  constructor(private playersService: PlayersService, private getVersionsService: GetVersionsService, private router: Router) {}

  leagueChallengerSoloQSubscription: Subscription | null = null;
  leagueChallengerFlexSubscription: Subscription | null = null;
  getAccountSubscription: Subscription | null = null;

  firstChallengerPlayerSoloQSignal: Signal<LeagueItemDTO | undefined> = signal<LeagueItemDTO | undefined>(undefined);
  firstChallengerSummonerSoloQSignal: WritableSignal<SummonerDTO | undefined> = signal<SummonerDTO | undefined>(undefined);
  firstChallengerSummonerFlexSignal: WritableSignal<SummonerDTO | undefined> = signal<SummonerDTO | undefined>(undefined);
  firstChallengerPlayerFlexSignal: Signal<LeagueItemDTO | undefined> = signal<LeagueItemDTO | undefined>(undefined);
  lastVersionLolSignal: Signal<string> = signal('');

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

  ngOnInit(): void {
    this.lastVersionLolSignal = this.getVersionsService.lastVersionlolDTOSignal;
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

  ngOnDestroy(): void {
    if (this.leagueChallengerSoloQSubscription) {
      this.leagueChallengerSoloQSubscription.unsubscribe();
    }

    if (this.leagueChallengerFlexSubscription) {
      this.leagueChallengerFlexSubscription.unsubscribe();
    }
  }
}
