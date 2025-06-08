export class LeagueItemDTO {
  summonerId!: string;
  puuid!: string;
  leaguePoints!: number;
  rank!: string;
  wins!: number;
  losses!: number;
  veteran!: boolean;
  inactive!: boolean;
  freshBlood!: boolean;
  hotStreak!: boolean;
  miniSeries?: MiniSeriesDTO;
}

export class MiniSeriesDTO {
  losses!: number;
  progress!: string;
  target!: number;
  wins!: number;
}

export class LeagueListDTO {
  leagueId!: string;
  tier!: string;
  entries!: LeagueItemDTO[];
  queue!: string;
  name!: string;
}
