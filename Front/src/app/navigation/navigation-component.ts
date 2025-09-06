import { Component, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { INVALID_SEARCH_SUMMONER_ERROR } from '../common/constants/errors';
import { NgbTooltipModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-navigation-component',
  standalone: true,
  imports: [NgbTooltipModule],
  templateUrl: './navigation-component.html',
  styleUrl: './navigation-component.scss',
})
export class NavigationComponent {
  @ViewChild('searchSummoner') searchSummonerInputRef!: ElementRef<HTMLInputElement>;

  constructor(private router: Router) {}

  INVALID_SEARCH_SUMMONER_ERROR: string = '';

  redirectHome() {
    this.router.navigate(['/Accueil']);
  }

  isValidRiotId(input: string): boolean {
    const riotIdRegex = /^.{3,16}#.{3,5}$/u;
    return riotIdRegex.test(input);
  }

  onSearch() {
    const value = this.searchSummonerInputRef.nativeElement.value;
    if (value && this.isValidRiotId(value)) {
      this.router.navigate(['/Detail-summoner', value]);
      this.INVALID_SEARCH_SUMMONER_ERROR = '';
    } else {
      this.INVALID_SEARCH_SUMMONER_ERROR = INVALID_SEARCH_SUMMONER_ERROR;
    }
  }
}
