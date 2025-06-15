import { Component, Signal, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayersService } from '../../services/players/players.service';
import { catchError, of, switchMap, tap } from 'rxjs';
import { SummonerDTO } from '../../common/models/summonerDTO';
import { GetVersionsService } from '../../services/versions/get-versions.service';

@Component({
  selector: 'app-player-details',
  standalone: true,
  imports: [],
  templateUrl: './player-details.component.html',
  styleUrl: './player-details.component.scss',
})
export class PlayerDetailsComponent {
  constructor(
    private route: ActivatedRoute,
    private playerService: PlayersService,
    private versionService: GetVersionsService,
    private router: Router
  ) {}
  gameName: string | undefined = '';
  summonerDto: SummonerDTO = new SummonerDTO();
  lastVersionLolSignal: Signal<string> = signal('');

  ngOnInit() {
    this.lastVersionLolSignal = this.versionService.lastVersionlolDTOSignal;
    this.route.paramMap
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
      .subscribe({
        next: (summoner: SummonerDTO) => {
          this.summonerDto = summoner;
          if (!summoner.id) {
            this.router.navigate(['/NotFound']);
          }
        },
        error: () => console.log('error'),
      });
  }

  private getDataPlayerdetail(riotId: string) {
    let gameName: string = '';
    let tagLine: string = '';
    if (riotId) {
      [gameName, tagLine] = riotId.split('#');
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
}
