import { Component, Input, OnInit, signal, Signal } from '@angular/core';
import { MatchDTO, ParticipantMatchDTO } from '../../common/models/games-history/matchDTO';
import { CommonModule } from '@angular/common';
import { GetVersionsService } from '../../services/versions/get-versions.service';
import { DDRAGON_BASE_CDN } from '../../common/constants/api-urls';
import { HistoryService } from '../../services/games-history/history.service';
import { formatTimestampToDateStr } from '../../common/utils/date-utils';
import { ItemUrl } from '../../common/types/types';
import { durationSecondeToStr } from '../../common/utils/time-utils';
import { summonerSpellIdToNameMap } from '../../common/constants/summoners';

@Component({
  selector: 'app-history-items',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './history-items.component.html',
  styleUrl: './history-items.component.scss',
})
export class HistoryItemsComponent implements OnInit {
  constructor(private versionService: GetVersionsService, private historyService: HistoryService) {}

  @Input() currMatchParticipant!: ParticipantMatchDTO;
  DDRAGON_BASE_CDN = DDRAGON_BASE_CDN;
  lastVersionLolSignal: Signal<string> = signal('');
  listUrlItems: ItemUrl[] = [];
  iconChampionUrl = '';
  nbCsKilled: number = 0;
  listMatchDataSignal: Signal<MatchDTO[]> = signal([]);
  currentMatch?: MatchDTO;
  dateCreationGameStr: string = '';
  gameDuration: string = '';
  summoner1IconUrl: string = '';
  summoner2IconUrl: string = '';
  SUMMONER_SPELL_ID_TO_NAME_MAP = summonerSpellIdToNameMap;

  ngOnInit(): void {
    this.lastVersionLolSignal = this.versionService.lastVersionlolDTOSignal;
    this.listUrlItems = this.getListUrlItems();
    this.iconChampionUrl = this.computeIconChampionUrl();
    this.nbCsKilled = this.computeNbCsKilled();
    this.listMatchDataSignal = this.historyService.listMatchDataSignal;
    this.currentMatch = this.getCurrentMatch();
    this.dateCreationGameStr = formatTimestampToDateStr(this.currentMatch!.info.gameCreation);
    this.gameDuration = durationSecondeToStr(this.currentMatch!.info.gameDuration);
    this.summoner1IconUrl = this.computeSummonerSpellUrl(this.currMatchParticipant.summoner1Id);
    this.summoner2IconUrl = this.computeSummonerSpellUrl(this.currMatchParticipant.summoner2Id);
  }

  private getCurrentMatch(): MatchDTO | undefined {
    return this.listMatchDataSignal().find((match) => match.metadata.matchId === this.currMatchParticipant.matchId);
  }

  private getListUrlItems(): ItemUrl[] {
    return [
      { url: this.computeIconItemUrl(this.currMatchParticipant?.item0), itemId: this.currMatchParticipant?.item0 },
      { url: this.computeIconItemUrl(this.currMatchParticipant?.item1), itemId: this.currMatchParticipant?.item1 },
      { url: this.computeIconItemUrl(this.currMatchParticipant?.item2), itemId: this.currMatchParticipant?.item2 },
      { url: this.computeIconItemUrl(this.currMatchParticipant?.item3), itemId: this.currMatchParticipant?.item3 },
      { url: this.computeIconItemUrl(this.currMatchParticipant?.item4), itemId: this.currMatchParticipant?.item4 },
      { url: this.computeIconItemUrl(this.currMatchParticipant?.item5), itemId: this.currMatchParticipant?.item5 },
      { url: this.computeIconItemUrl(this.currMatchParticipant?.item6), itemId: this.currMatchParticipant?.item6 },
    ];
  }

  private computeNbCsKilled() {
    return this.currMatchParticipant.neutralMinionsKilled + this.currMatchParticipant.totalMinionsKilled;
  }

  private computeIconChampionUrl() {
    return this.DDRAGON_BASE_CDN + this.lastVersionLolSignal() + '/img/champion/' + this.currMatchParticipant.championName + '.png';
  }

  private computeIconItemUrl(itemId: number) {
    return this.DDRAGON_BASE_CDN + this.lastVersionLolSignal() + '/img/item/' + itemId + '.png';
  }

  private computeSummonerSpellUrl(summonerSpellId: number) {
    return this.DDRAGON_BASE_CDN + this.lastVersionLolSignal() + '/img/spell/' + this.SUMMONER_SPELL_ID_TO_NAME_MAP[summonerSpellId] + '.png';
  }

  get role(): string {
    switch (this.currMatchParticipant.teamPosition) {
      case 'TOP':
        return 'Top';
      case 'JUNGLE':
        return 'Jungle';
      case 'MIDDLE':
        return 'Mid';
      case 'BOTTOM':
        return 'ADC';
      case 'UTILITY':
        return 'Support';
      default:
        return 'Inconnu'; // ou "", ou ne rien afficher
    }
  }
}
