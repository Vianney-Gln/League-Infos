export class ChampionData {
  type!: string;
  format!: string;
  version!: string;
  data!: {
    [championName: string]: Champion;
  };
}

export class Champion {
  version!: string;
  id!: string;
  key!: string;
  name!: string;
  title!: string;
  blurb!: string;
  info!: ChampionInfo;
  image!: ChampionImage;
  tags!: string[];
  partype!: string;
  stats!: ChampionStats;
}

export class ChampionInfo {
  attack!: number;
  defense!: number;
  magic!: number;
  difficulty!: number;
}

export class ChampionImage {
  full!: string;
  sprite!: string;
  group!: string;
  x!: number;
  y!: number;
  w!: number;
  h!: number;
}

export class ChampionStats {
  hp!: number;
  hpperlevel!: number;
  mp!: number;
  mpperlevel!: number;
  movespeed!: number;
  armor!: number;
  armorperlevel!: number;
  spellblock!: number;
  spellblockperlevel!: number;
  attackrange!: number;
  hpregen!: number;
  hpregenperlevel!: number;
  mpregen!: number;
  mpregenperlevel!: number;
  crit!: number;
  critperlevel!: number;
  attackdamage!: number;
  attackdamageperlevel!: number;
  attackspeedperlevel!: number;
  attackspeed!: number;
}
