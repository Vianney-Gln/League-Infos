import { Component, OnDestroy, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { GetChampionsService } from './services/champions/get-champions.service';
import { NavigationComponent } from './navigation/navigation-component';
import { Subscription, switchMap, tap } from 'rxjs';
import { GetVersionsService } from './services/versions/get-versions.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavigationComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App implements OnInit, OnDestroy {
  constructor(private getChampionService: GetChampionsService, private getVersionsService: GetVersionsService) {}

  title: string = 'league-infos';
  ChampioninfosSubscription: Subscription | null = null;

  ngOnInit() {
    this.getVersionsService
      .getAllVersionsLol()
      .pipe(
        switchMap((versions) => {
          this.getVersionsService.lastVersionlolDTOSignal.set(versions[0]);
          this.getVersionsService.lastTwentyVersionslolSignal.set(versions.slice(0, 20));
          return this.getChampionService.getAllChampionsInfos(versions[0]);
        })
      )
      .subscribe({
        next: (data) => {
          this.getChampionService.championDataSignal.set(data);
        },
        error: (error) => {
          this.getChampionService.isFreeChampErrorSignal.set(true);
          console.log(error);
        },
      });
  }

  ngOnDestroy(): void {
    if (this.ChampioninfosSubscription) {
      this.ChampioninfosSubscription.unsubscribe();
    }
  }
}
