import { Component, computed, OnDestroy, OnInit, Signal, signal, ViewEncapsulation, WritableSignal } from '@angular/core';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';
import { PlayersService } from '../../services/players/players.service';
import { LeagueItemDTO, LeagueListDTO } from '../../common/models/leagueListDTO';
import { Subscription, switchMap, tap } from 'rxjs';
import { AccountDTO } from '../../common/models/accountDTO';
import { SummonerDTO } from '../../common/models/summonerDTO';
import { GetVersionsService } from '../../services/versions/get-versions.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-hero-component',
  standalone: true,
  imports: [NgbCarouselModule, CommonModule],
  templateUrl: './hero.component.html',
  styleUrl: './hero.component.scss',
  encapsulation: ViewEncapsulation.None,
})
export class HeroComponent implements OnInit, OnDestroy {
  constructor(private playersService: PlayersService, private getVersionsService: GetVersionsService) {}

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
  isCarouselLoading: boolean = false;
  isCarouselError: boolean = false;

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
          this.isCarouselLoading = false;
          return this.playersService.getSummonerByPuuid(account.puuid);
        })
      )
      .subscribe({
        next: (summoner: SummonerDTO) => {
          this.firstChallengerSummonerSoloQSignal.set(summoner);
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
          this.isCarouselLoading = false;
          return this.playersService.getSummonerByPuuid(account.puuid);
        })
      )
      .subscribe({
        next: (summoner: SummonerDTO) => {
          this.firstChallengerSummonerFlexSignal.set(summoner);
        },
        error: (err) => {
          console.log(err);
          this.isCarouselLoading = false;
          this.isCarouselError = true;
        },
      });
  }

  ngOnDestroy(): void {
    if (this.leagueChallengerSoloQSubscription) {
      this.leagueChallengerSoloQSubscription.unsubscribe();
    }

    if (this.leagueChallengerFlexSubscription) {
      this.leagueChallengerFlexSubscription.unsubscribe();
    }

    if (this.getAccountSubscription) {
      this.getAccountSubscription.unsubscribe();
    }
  }
}
