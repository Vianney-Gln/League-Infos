insert into t_infos_match (creation_date, end_of_game_result, game_creation, game_duration, game_end_timestamp, game_id, game_mode, game_name, game_type, game_version, map_id, queue_id)
    values (timestamp '2025-09-28 01:00', 'GameComplete', 1756249870149, 1742, 1756251627166, 7508515244, 'CLASSIC', 'teambuilder-match-7508515244', 'MATCHED_GAME', '15.16.706.7476', 11, 420);

insert into t_challenges (game_length, kda) values (1742.83544921875, 4.199999809265137);
insert into t_meta_data (data_version,game_id,match_id) values ('2', 7508515244, 'EUW1_7508515244');

insert into t_participant_match (assists,
challenges_id,
champ_level,
champion_id,
champion_name,
champion_transform,
deaths,
double_kills,
gold_earned,
participant_id,
info_match_id,
item_0,
item_1,
item_2,
item_3,
item_4,
item_5,
item_6,
kills,
lane,
meta_data_id,
neutral_minions_killed,
penta_kills,
profile_icon,
puuid,
quadra_kills,
role,
summoner1_id,
summoner2_id,
team_id,
team_position,
total_minions_killed,
triple_kills,
win)
values (11, 1, 16, 126, 'Jayce', 0, 5, 0, 18565, 1, 1, 3142, 3042, 3111, 6694, 3134, 1055, 3363, 10, 'TOP', 1, 0, 0, 1114, 'zb1DuHxFttxhq_01ETiTwyjkBzaCq9JcysGVfW_J6u45vNLQi0AQTdZE3cvS0gsYHK_VKLAbtWDpmQ', 0, 'SOLO', 12, 4, 100, 'TOP', 174, 0, true);

insert into t_teams (info_match_id, team_id_numero, win) values (1, 100, true);