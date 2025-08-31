package com.league.league_infos.dao.repository;

import com.league.league_infos.dao.entity.InfoMatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InfoMatchRepository extends JpaRepository<InfoMatchEntity, Long> {
    InfoMatchEntity findByGameId(long gameId);

    @Query("SELECT infoMatch FROM ParticipantMatchEntity participant " +
            "JOIN participant.infoMatchEntity infoMatch " +
            "where participant.puuid = :puuid " +
            "AND infoMatch.creationDate >= :oneHourAgo " +
            "AND infoMatch.queueId = :queue")
    List<InfoMatchEntity> findMostRecentMatchByPuuid(@Param("puuid") String puuid, @Param("oneHourAgo") LocalDateTime oneHourAgo, @Param("queue") Integer queue);
}
