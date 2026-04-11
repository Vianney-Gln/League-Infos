package com.league.league_infos.services.api;

import com.league.league_infos.dto.ia.EventMatchDTO;

import java.util.List;

public interface MatchTimelineService {

    List<EventMatchDTO> getMatchEventsByMatchId(String matchId) throws Exception;
}
