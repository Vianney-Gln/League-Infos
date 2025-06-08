import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';

export const routes: Routes = [
  { path: 'Accueil', component: HomeComponent, title: 'Accueil' },
  { path: '', redirectTo: 'Accueil', pathMatch: 'full' },
];
