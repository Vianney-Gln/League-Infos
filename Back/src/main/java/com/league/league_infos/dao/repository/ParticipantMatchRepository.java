package com.league.league_infos.dao.repository;

import com.league.league_infos.dao.entity.ParticipantMatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantMatchRepository extends JpaRepository<ParticipantMatchEntity, Long> {

    @Query("""
            SELECT p FROM ParticipantMatchEntity p
            JOIN p.infoMatchEntity i
            WHERE p.puuid = :puuid
            AND i.gameId = :gameId
            """)
    ParticipantMatchEntity getParticipantByGameIdAndPuuid(@Param("gameId") Long gameId, @Param("puuid") String puuid);
}
