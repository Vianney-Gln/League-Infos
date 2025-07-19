import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PlayerDetailsComponent } from './details/player-details/player-details.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { ChampionDetailsComponent } from './details/champion-details/champion-details.component';

export const routes: Routes = [
  { path: 'Accueil', component: HomeComponent, title: 'LeagueInfos - Accueil' },
  { path: '', redirectTo: 'Accueil', pathMatch: 'full' },
  { path: 'Detail-summoner/:summoner', component: PlayerDetailsComponent, title: 'LeagueInfos - Invocateur' },
  { path: 'Detail-champion/:champion', component: ChampionDetailsComponent, title: 'LeagueInfos - Champion' },
  { path: 'NotFound', component: NotFoundComponent, title: 'LeagueInfos - ressource non trouvée' },
];
