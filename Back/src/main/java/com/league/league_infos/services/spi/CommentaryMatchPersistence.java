package com.league.league_infos.services.spi;

import com.league.league_infos.dto.ia.CommentaryMatchDTO;

import java.util.Optional;

public interface CommentaryMatchPersistence {

    Optional<CommentaryMatchDTO> getCommentaryByGameIdAndPuuid(Long gameId, String puuid);

    CommentaryMatchDTO persist(CommentaryMatchDTO commentaryMatchDTO, Long gameId, String puuid);
}
