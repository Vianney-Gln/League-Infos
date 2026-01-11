export class MatchDTO {
  metadata!: MetadataDTO;
  info!: InfoMatchDTO;
}

export class MetadataDTO {
  dataVersion!: string;
  matchId!: string;
  participants!: string[];
}

export class InfoMatchDTO {
  endOfGameResult!: string;
  gameMode!: string;
  gameName!: string;
  gameType!: string;
  gameVersion!: string;
  gameCreation!: number;
  gameDuration!: number;
  gameEndTimestamp!: number;
  gameId!: number;
  mapId!: number;
  queueId!: number;
  participants!: ParticipantMatchDTO[];
  teams!: TeamDTO[];
}

export class TeamDTO {
  teamId!: number;
  win!: boolean;
}

export class ParticipantMatchDTO {
  participantId!: number;
  profileIcon!: number;
  assists!: number;
  kills!: number;
  deaths!: number;
  champLevel!: number;
  championId!: number;
  goldEarned!: number;
  item0!: number;
  item1!: number;
  item2!: number;
  item3!: number;
  item4!: number;
  item5!: number;
  item6!: number;
  championTransform!: number; // used only for champion Kayn
  pentaKills!: number;
  quadraKills!: number;
  tripleKills!: number;
  doubleKills!: number;
  summoner1Id!: number;
  summoner2Id!: number;
  teamId!: number;
  totalMinionsKilled!: number;
  neutralMinionsKilled!: number;
  championName!: string;
  summonerName!: string;
  lane!: string;
  role!: string;
  puuid!: string;
  pseudo!: string;
  teamPosition!: string;
  win!: boolean;
  challenges!: ChallengeDTO;
  matchId!: string;
}

export class ChallengeDTO {
  gameLength!: number;
  kda!: number;
}
