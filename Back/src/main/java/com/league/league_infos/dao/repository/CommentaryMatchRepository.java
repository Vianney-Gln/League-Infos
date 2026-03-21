package com.league.league_infos.dao.repository;

import com.league.league_infos.dao.entity.CommentaryMatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentaryMatchRepository extends JpaRepository<CommentaryMatchEntity, Long> {

    @Query("""
            select commentaryMatchEntity FROM InfoMatchEntity infoMatch
            join infoMatch.participantMatchEntityList participant
            join participant.commentaryMatchEntity commentaryMatchEntity
            where participant.puuid = :puuid
            and infoMatch.gameId = :gameId
            """)
    Optional<CommentaryMatchEntity> getByGameIdAndPuuid(@Param("gameId") Long gameId, @Param("puuid") String puuid);
}
