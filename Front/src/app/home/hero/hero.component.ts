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

  leagueChallengerSubscription: Subscription | null = null;
  getAccountSubscription: Subscription | null = null;
  firstChallengerPlayerSoloQSignal: Signal<LeagueItemDTO | undefined> = signal<LeagueItemDTO | undefined>(undefined);
  firstChallengerSummonerSoloQSignal: WritableSignal<SummonerDTO | undefined> = signal<SummonerDTO | undefined>(undefined);
  leagueBestPlayer: string | undefined = '';
  gameNameBestChalPlayerSoloQ: string | undefined = '';
  isDataBestSoloQPlayerLoading: boolean = false;
  lastVersionLolSignal: Signal<string> = signal('');

  ngOnInit(): void {
    this.lastVersionLolSignal = this.getVersionsService.lastVersionlolDTOSignal;
    this.getDataBestSoloqPlayer();
  }

  private getDataBestSoloqPlayer(): void {
    this.isDataBestSoloQPlayerLoading = true;
    this.leagueChallengerSubscription = this.playersService
      .getLeagueChallengerDataSoloQ()
      .pipe(
        tap((leagueListDTO) => {
          this.playersService.leagueChallengerListDTOSignal.set(leagueListDTO);
        }),
        switchMap((leagueListDTO) => {
          this.leagueBestPlayer = this.playersService.leagueChallengerListDTOSignal()?.tier;
          this.firstChallengerPlayerSoloQSignal = computed(() => this.playersService.leagueChallengerListDTOSignal()?.entries[0]);
          return this.playersService.getAccountByPuuid(leagueListDTO.entries[0].puuid);
        }),
        switchMap((account: AccountDTO) => {
          this.gameNameBestChalPlayerSoloQ = account.gameName;
          this.isDataBestSoloQPlayerLoading = false;
          return this.playersService.getSummonerByPuuid(account.puuid);
        })
      )
      .subscribe({
        next: (summoner: SummonerDTO) => {
          this.firstChallengerSummonerSoloQSignal.set(summoner);
        },
        error: (err) => {
          console.log(err);
          this.isDataBestSoloQPlayerLoading = false;
        },
      });
  }

  ngOnDestroy(): void {
    if (this.leagueChallengerSubscription) {
      this.leagueChallengerSubscription.unsubscribe();
    }

    if (this.getAccountSubscription) {
      this.getAccountSubscription.unsubscribe();
    }
  }
}
