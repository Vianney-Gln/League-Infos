import { Component, computed, effect, OnDestroy, OnInit, Signal, signal, WritableSignal } from '@angular/core';
import { GetChampionsService } from '../services/champions/get-champions.service';
import { FreeChampionsDTO } from '../common/models/freeChampionsDTO';
import { Champion } from '../common/models/championsInfos';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeroComponent } from './hero/hero.component';
import { Subscription } from 'rxjs';
import { GetVersionsService } from '../services/versions/get-versions.service';
import { CHAMPS_ERRORS } from '../common/constants/errors';

@Component({
  selector: 'app-home-component',
  standalone: true,
  imports: [CommonModule, NgbModule, HeroComponent],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  constructor(private getChampionService: GetChampionsService, private getVersionsService: GetVersionsService) {
    this.getChampionsDataEffect();
  }

  freeChampionSubscription: Subscription | null = null;
  freeChampionsDTOSignal: WritableSignal<FreeChampionsDTO | null> = signal<FreeChampionsDTO | null>(null);
  freeChampionsDataSignal: WritableSignal<Champion[] | null> = signal<Champion[] | null>(null);
  freeChampionsForBeginnersDataSignal: WritableSignal<Champion[] | null> = signal<Champion[] | null>(null);
  isFreeChampsErrorSignal!: Signal<boolean>;
  lastVersionLolSignal: Signal<string> = signal('');
  champsErrorMsg = CHAMPS_ERRORS;
  listChampions: Champion[] | null = [];

  CHAMPION_TYPES = [
    { id: 0, type: '', libelle: 'Tous' },
    { id: 1, type: 'Assassin', libelle: 'Assassin' },
    { id: 2, type: 'Fighter', libelle: 'Combattant' },
    { id: 3, type: 'Mage', libelle: 'Mage' },
    { id: 4, type: 'Marksman', libelle: 'Tireur' },
    { id: 5, type: 'Support', libelle: 'Support' },
    { id: 6, type: 'Tank', libelle: 'Tank' },
    { id: 7, type: 'Free', libelle: 'Gratuits' },
    { id: 8, type: 'FreeBeginners', libelle: 'Gratuits jusque lv10' },
  ];

  ngOnInit() {
    this.isFreeChampsErrorSignal = this.getChampionService.isFreeChampErrorSignal;
    this.lastVersionLolSignal = this.getVersionsService.lastVersionlolDTOSignal;
    this.freeChampionSubscription = this.getChampionService.getFreeChampions().subscribe({
      next: (freeChamp) => {
        const freeChampNames: string[] = this.findChampionsNameByIds(freeChamp, false);
        const freeChampNamesForBeginners: string[] = this.findChampionsNameByIds(freeChamp, true);
        this.freeChampionsDTOSignal.set(freeChamp);

        freeChampNames.forEach((freeChampName) => {
          this.findChampDataFreeByNames(freeChampName, false);
        });

        freeChampNamesForBeginners.forEach((freeChampName) => {
          this.findChampDataFreeByNames(freeChampName, true);
        });
      },
      error: (err) => {
        this.getChampionService.isFreeChampErrorSignal.set(true);
        console.log(err);
      },
    });
  }

  private findChampionsNameByIds(freeChamp: FreeChampionsDTO, isBeginner: boolean): string[] {
    const dataFreeChampionsDDragon = this.getChampionService.championDataSignal()?.data;
    const actualfreeChampionsIds = isBeginner ? freeChamp.freeChampionIdsForNewPlayers : freeChamp.freeChampionIds;

    let freeChampionsName: string[] = [];

    if (dataFreeChampionsDDragon) {
      Object.values(dataFreeChampionsDDragon).forEach((val) => {
        actualfreeChampionsIds?.forEach((freeChampId) => {
          if (val.key.toString() === freeChampId.toString()) {
            freeChampionsName.push(val.id);
          }
        });
      });
    }
    return freeChampionsName;
  }

  private findChampDataFreeByNames(freeChampNames: string, isBeginner: boolean) {
    const dataFreeChampionsDDragon = this.getChampionService.championDataSignal()?.data;
    if (dataFreeChampionsDDragon) {
      if (isBeginner) {
        this.freeChampionsForBeginnersDataSignal.update((curr) => {
          const filtered = Object.values(dataFreeChampionsDDragon).filter((champion: Champion) => champion.id === freeChampNames);
          return curr ? [...curr, ...filtered] : [...filtered];
        });
      } else {
        this.freeChampionsDataSignal.update((curr) => {
          const filtered = Object.values(dataFreeChampionsDDragon).filter((champion: Champion) => champion.id === freeChampNames);
          return curr ? [...curr, ...filtered] : [...filtered];
        });
      }
    }
  }

  private getChampionsDataEffect() {
    effect(() => {
      const championDataSignal = this.getChampionService.championDataSignal();
      if (championDataSignal) {
        this.listChampions = Object.values(championDataSignal.data);
      }
    });
  }

  sortTypeChamp(event: Event) {
    const select = event.target as HTMLSelectElement;
    const type = select.selectedOptions[0].getAttribute('value');
    const championDataSignal = this.getChampionService.championDataSignal();
    if (championDataSignal) {
      if (type) {
        if (type === 'Free') {
          this.listChampions = this.freeChampionsDataSignal() !== null ? this.freeChampionsDataSignal() : [];
        } else if (type === 'FreeBeginners') {
          this.listChampions = this.freeChampionsDataSignal() !== null ? this.freeChampionsForBeginnersDataSignal() : [];
        } else {
          this.listChampions = Object.values(championDataSignal.data).filter((champ) => champ.tags.includes(type));
        }
      } else {
        this.listChampions = Object.values(championDataSignal.data);
      }
    }
  }

  ngOnDestroy(): void {
    if (this.freeChampionSubscription) {
      this.freeChampionSubscription.unsubscribe();
    }
  }
}
