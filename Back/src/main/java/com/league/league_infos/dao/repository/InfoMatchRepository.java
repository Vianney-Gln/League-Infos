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
    List<InfoMatchEntity> findRecentsMatchByPuuidAndQueue(@Param("puuid") String puuid, @Param("oneHourAgo") LocalDateTime oneHourAgo, @Param("queue") Integer queue);

    @Query("SELECT infoMatch FROM ParticipantMatchEntity participant " +
            "JOIN participant.infoMatchEntity infoMatch " +
            "where participant.puuid = :puuid " +
            "AND infoMatch.queueId = :queue " +
            "ORDER BY infoMatch.gameCreation DESC " +
            "LIMIT 10"
    )
    List<InfoMatchEntity> findAllMatchByPuuidAndQueue(@Param("puuid") String puuid, @Param("queue") Integer queue);

    @Query("SELECT infoMatch FROM ParticipantMatchEntity participant " +
            "JOIN participant.infoMatchEntity infoMatch " +
            "where participant.puuid = :puuid " +
            "AND infoMatch.queueId = :queue " +
            "AND infoMatch.gameCreation < :gameCreation " +
            "ORDER BY infoMatch.gameCreation DESC " +
            "LIMIT 10"
    )
    List<InfoMatchEntity> findAllMatchByPuuidAndQueueBeforeGivenDate(@Param("puuid") String puuid, @Param("gameCreation") Long gameCreation,
                                                                     @Param("queue") Integer queue);
}
