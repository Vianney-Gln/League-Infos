import { Component, Signal, signal, WritableSignal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayersService } from '../../services/players/players.service';
import { catchError, map, of, switchMap, tap } from 'rxjs';
import { SummonerDTO } from '../../common/models/summonerDTO';
import { GetVersionsService } from '../../services/versions/get-versions.service';
import { LeagueEntryDTO } from '../../common/models/LeagueEntryDTO';
import { CommonModule } from '@angular/common';
import { ChampionMasteryDto } from '../../common/models/ChampionMasteryDto';

@Component({
  selector: 'app-player-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './player-details.component.html',
  styleUrl: './player-details.component.scss',
})
export class PlayerDetailsComponent {
  constructor(private route: ActivatedRoute, private playerService: PlayersService, private versionService: GetVersionsService, private router: Router) {}
  gameName: string | undefined = '';
  summonerDto: SummonerDTO = new SummonerDTO();
  lastVersionLolSignal: Signal<string> = signal('');
  leagueEntriesSignal: WritableSignal<LeagueEntryDTO[]> = signal([]);
  championMasteriesSignal: WritableSignal<ChampionMasteryDto[]> = signal([]);
  urlBackgroundBannerSignal: WritableSignal<string> = signal('');
  tagLine: string = '';
  isLoading = false;

  ngOnInit() {
    this.lastVersionLolSignal = this.versionService.lastVersionlolDTOSignal;
    this.isLoading = true;
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
      .pipe(
        switchMap((summoner: SummonerDTO) => {
          this.summonerDto = summoner;
          this.summonerDto = summoner;
          if (!summoner.id) {
            this.router.navigate(['/NotFound']);
          }
          return this.playerService.getLeagueEntryByPuuid(summoner.puuid).pipe(map((leagueEntries) => ({ leagueEntries, puuid: summoner.puuid })));
        })
      )
      .pipe(
        switchMap(({ leagueEntries, puuid }) => {
          this.leagueEntriesSignal.set(leagueEntries);
          return this.playerService.getChampionMasteriesDTO(puuid);
        })
      )
      .subscribe({
        next: (championMasteries) => {
          this.isLoading = false;
          this.championMasteriesSignal.set(championMasteries);
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
}
