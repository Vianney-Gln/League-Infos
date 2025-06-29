import { LeagueItemDTO } from './leagueListDTO';

export class LeagueEntryDTO extends LeagueItemDTO {
  leagueId!: string;
  tier!: string;
  queueType!: string;
}
