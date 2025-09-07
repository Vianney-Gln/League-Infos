CREATE TABLE T_META_DATA (
    meta_data_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    data_version VARCHAR(255) NOT NULL,
    match_id VARCHAR(255) NOT NULL,
    game_id BIGINT NOT NULL
);

CREATE TABLE T_INFOS_MATCH (
    info_match_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    end_of_game_result VARCHAR(255) NOT NULL,
    game_mode VARCHAR(255) NOT NULL,
    game_name VARCHAR(255),
    game_type VARCHAR(255) NOT NULL,
    game_version VARCHAR(255) NOT NULL,
    game_creation BIGINT NOT NULL,
    game_duration BIGINT NOT NULL,
    game_end_timestamp BIGINT NOT NULL,
    game_id BIGINT NOT NULL,
    map_id INTEGER NOT NULL,
    queue_id INTEGER NOT NULL,
    creation_date TIMESTAMP NOT NULL
);

CREATE TABLE T_TEAMS (
    team_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_id_numero INTEGER NOT NULL,
    win boolean NOT NULL,
    info_match_id BIGINT NOT NULL
);

CREATE TABLE T_CHALLENGES (
    challenges_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    game_length FLOAT NOT NULL,
    kda FLOAT NOT NULL
);

CREATE TABLE T_PARTICIPANT_MATCH (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    participant_id BIGINT,
    profile_icon INTEGER,
    assists INTEGER NOT NULL,
    kills INTEGER NOT NULL,
    deaths INTEGER NOT NULL,
    champ_level INTEGER NOT NULL,
    champion_id INTEGER NOT NULL,
    gold_earned INTEGER NOT NULL,
    item_0 INTEGER NOT NULL,
    item_1 INTEGER NOT NULL,
    item_2 INTEGER NOT NULL,
    item_3 INTEGER NOT NULL,
    item_4 INTEGER NOT NULL,
    item_5 INTEGER NOT NULL,
    item_6 INTEGER NOT NULL,
    champion_transform INTEGER NOT NULL,
    penta_kills INTEGER NOT NULL,
    quadra_kills INTEGER NOT NULL,
    triple_kills INTEGER NOT NULL,
    double_kills INTEGER NOT NULL,
    summoner1_id INTEGER NOT NULL,
    summoner2_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    total_minions_killed INTEGER NOT NULL,
    neutral_minions_killed INTEGER NOT NULL,
    champion_name VARCHAR(255) NOT NULL,
    summoner_name VARCHAR(255),
    lane VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    team_position VARCHAR(255) NOT NULL,
    puuid VARCHAR(255) NOT NULL,
    win boolean NOT NULL,
    challenges_id BIGINT NOT NULL,
    info_match_id BIGINT NOT NULL,
    meta_data_id BIGINT NOT NULL
);

ALTER TABLE T_PARTICIPANT_MATCH
ADD CONSTRAINT fk_participant_match_challenges
FOREIGN KEY (challenges_id)
REFERENCES T_CHALLENGES(challenges_id);

ALTER TABLE T_PARTICIPANT_MATCH
ADD CONSTRAINT fk_participant_info_match
FOREIGN KEY (info_match_id)
REFERENCES T_INFOS_MATCH(info_match_id);

ALTER TABLE T_TEAMS
ADD CONSTRAINT fk_teams_info_match
FOREIGN KEY (info_match_id)
REFERENCES T_INFOS_MATCH(info_match_id);

ALTER TABLE T_PARTICIPANT_MATCH
ADD CONSTRAINT fk_meta_data_participant_match
FOREIGN KEY (meta_data_id)
REFERENCES T_META_DATA(meta_data_id);