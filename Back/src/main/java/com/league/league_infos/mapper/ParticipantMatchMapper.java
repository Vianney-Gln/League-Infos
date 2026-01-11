package com.league.league_infos.mapper;

import com.league.league_infos.dao.entity.InfoMatchEntity;
import com.league.league_infos.dao.entity.ParticipantMatchEntity;
import com.league.league_infos.dto.match.MetadataDTO;
import com.league.league_infos.dto.match.ParticipantMatchDTO;

import java.util.Objects;

public class ParticipantMatchMapper {

    public static ParticipantMatchEntity participantMatchDtoToEntity(ParticipantMatchDTO participantMatchDTO,
                                                                     InfoMatchEntity infoMatchEntity,
                                                                     MetadataDTO metadataDTO,
                                                                     Long gameId) {
        Objects.requireNonNull(participantMatchDTO, "participantMatchDTO cannot be null");
        Objects.requireNonNull(participantMatchDTO.getParticipantId(), "participantMatchDTO.participantId cannot be null");
        Objects.requireNonNull(metadataDTO, "metadataDTO cannot be null");
        Objects.requireNonNull(gameId, "gameId cannot be null");

        ParticipantMatchEntity participantMatchEntity = new ParticipantMatchEntity();
        participantMatchEntity.setIdParticipant(Long.parseLong(String.valueOf(participantMatchDTO.getParticipantId())));
        participantMatchEntity.setInfoMatchEntity(infoMatchEntity);
        participantMatchEntity.setAssists(participantMatchDTO.getAssists());
        participantMatchEntity.setChampionId(participantMatchDTO.getChampionId());
        participantMatchEntity.setDeaths(participantMatchDTO.getDeaths());
        participantMatchEntity.setChampionName(participantMatchDTO.getChampionName());
        participantMatchEntity.setChampionTransform(participantMatchDTO.getChampionTransform());
        participantMatchEntity.setChampLevel(participantMatchDTO.getChampLevel());
        participantMatchEntity.setDoubleKills(participantMatchDTO.getDoubleKills());
        participantMatchEntity.setTripleKills(participantMatchDTO.getTripleKills());
        participantMatchEntity.setQuadraKills(participantMatchDTO.getQuadraKills());
        participantMatchEntity.setPentaKills(participantMatchDTO.getPentaKills());
        participantMatchEntity.setGoldEarned(participantMatchDTO.getGoldEarned());
        participantMatchEntity.setKills(participantMatchDTO.getKills());
        participantMatchEntity.setLane(participantMatchDTO.getLane());
        participantMatchEntity.setRole(participantMatchDTO.getRole());
        participantMatchEntity.setNeutralMinionsKilled(participantMatchDTO.getNeutralMinionsKilled());
        participantMatchEntity.setTotalMinionsKilled(participantMatchDTO.getTotalMinionsKilled());
        participantMatchEntity.setPuuid(participantMatchDTO.getPuuid());
        participantMatchEntity.setPseudo(participantMatchDTO.getPseudo());
        participantMatchEntity.setProfileIcon(participantMatchDTO.getProfileIcon());
        participantMatchEntity.setTeamId(participantMatchDTO.getTeamId());
        participantMatchEntity.setTeamPosition(participantMatchDTO.getTeamPosition());
        participantMatchEntity.setSummonerName(participantMatchDTO.getSummonerName());
        participantMatchEntity.setSummoner1Id(participantMatchDTO.getSummoner1Id());
        participantMatchEntity.setSummoner2Id(participantMatchDTO.getSummoner2Id());
        participantMatchEntity.setWin(participantMatchDTO.getWin());
        participantMatchEntity.setItem0(participantMatchDTO.getItem0());
        participantMatchEntity.setItem1(participantMatchDTO.getItem1());
        participantMatchEntity.setItem2(participantMatchDTO.getItem2());
        participantMatchEntity.setItem3(participantMatchDTO.getItem3());
        participantMatchEntity.setItem4(participantMatchDTO.getItem4());
        participantMatchEntity.setItem5(participantMatchDTO.getItem5());
        participantMatchEntity.setItem6(participantMatchDTO.getItem6());
        participantMatchEntity.setChallengesEntity(ChallengesMapper.challengesDtoToEntity(participantMatchDTO.getChallenges(), infoMatchEntity));
        participantMatchEntity.setMetaDataEntity(MetaDataMapper.metaDatadtoToEntity(metadataDTO, gameId));
        return participantMatchEntity;
    }

    public static ParticipantMatchDTO participantMatchEntityToDTO(ParticipantMatchEntity participantMatchEntity) {
        Objects.requireNonNull(participantMatchEntity, "participantMatchEntity cannot be null");
        Objects.requireNonNull(participantMatchEntity.getIdParticipant(), "participantMatchEntity.participantId cannot be null");

        ParticipantMatchDTO participantMatchDTO = new ParticipantMatchDTO();
        participantMatchDTO.setParticipantId(Math.toIntExact(participantMatchEntity.getIdParticipant()));
        participantMatchDTO.setAssists(participantMatchEntity.getAssists());
        participantMatchDTO.setChampionId(participantMatchEntity.getChampionId());
        participantMatchDTO.setDeaths(participantMatchEntity.getDeaths());
        participantMatchDTO.setChampionName(participantMatchEntity.getChampionName());
        participantMatchDTO.setChampionTransform(participantMatchEntity.getChampionTransform());
        participantMatchDTO.setChampLevel(participantMatchEntity.getChampLevel());
        participantMatchDTO.setDoubleKills(participantMatchEntity.getDoubleKills());
        participantMatchDTO.setTripleKills(participantMatchEntity.getTripleKills());
        participantMatchDTO.setQuadraKills(participantMatchEntity.getQuadraKills());
        participantMatchDTO.setPentaKills(participantMatchEntity.getPentaKills());
        participantMatchDTO.setGoldEarned(participantMatchEntity.getGoldEarned());
        participantMatchDTO.setKills(participantMatchEntity.getKills());
        participantMatchDTO.setLane(participantMatchEntity.getLane());
        participantMatchDTO.setRole(participantMatchEntity.getRole());
        participantMatchDTO.setNeutralMinionsKilled(participantMatchEntity.getNeutralMinionsKilled());
        participantMatchDTO.setTotalMinionsKilled(participantMatchEntity.getTotalMinionsKilled());
        participantMatchDTO.setPuuid(participantMatchEntity.getPuuid());
        participantMatchDTO.setProfileIcon(participantMatchEntity.getProfileIcon());
        participantMatchDTO.setTeamId(participantMatchEntity.getTeamId());
        participantMatchDTO.setTeamPosition(participantMatchEntity.getTeamPosition());
        participantMatchDTO.setSummonerName(participantMatchEntity.getSummonerName());
        participantMatchDTO.setSummoner1Id(participantMatchEntity.getSummoner1Id());
        participantMatchDTO.setSummoner2Id(participantMatchEntity.getSummoner2Id());
        participantMatchDTO.setWin(participantMatchEntity.isWin());
        participantMatchDTO.setItem0(participantMatchEntity.getItem0());
        participantMatchDTO.setItem1(participantMatchEntity.getItem1());
        participantMatchDTO.setItem2(participantMatchEntity.getItem2());
        participantMatchDTO.setItem3(participantMatchEntity.getItem3());
        participantMatchDTO.setItem4(participantMatchEntity.getItem4());
        participantMatchDTO.setItem5(participantMatchEntity.getItem5());
        participantMatchDTO.setItem6(participantMatchEntity.getItem6());
        participantMatchDTO.setChallenges(ChallengesMapper.challengesEntityToDTO(participantMatchEntity.getChallengesEntity()));
        participantMatchDTO.setPseudo(participantMatchEntity.getPseudo());
        return participantMatchDTO;
    }
}
