package com.league.league_infos.services.riot;

import com.league.league_infos.dto.ia.EventMatchDTO;
import com.league.league_infos.services.api.MatchTimelineService;
import com.league.league_infos.services.handler.TimelineParserHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static com.league.league_infos.common.constants.ApiRiotUrls.MATCH_TIMELINE_BASE_URL;
import static com.league.league_infos.common.constants.ApiRiotUrls.MATCH_TIMELINE_END;

@Service
public class RiotMatchTimelineService implements MatchTimelineService {

    private final RestTemplate restTemplate;
    private final TimelineParserHandler timelineParserHandler;

    public RiotMatchTimelineService(@Qualifier("riotRestTemplate") RestTemplate restTemplate, TimelineParserHandler timelineParserHandler) {
        this.restTemplate = restTemplate;
        this.timelineParserHandler = timelineParserHandler;
    }

    @Override
    public List<EventMatchDTO> getMatchEventsByMatchId(String matchId) throws Exception {

        String url = MATCH_TIMELINE_BASE_URL + matchId + MATCH_TIMELINE_END;
        ResponseEntity<String> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                String.class
        );
        if (result.getBody() == null || result.getBody().isEmpty()) {
            return Collections.emptyList();
        }

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(result.getBody().getBytes(StandardCharsets.UTF_8))) {
            return timelineParserHandler.parseTimeline(inputStream);
        }
    }
}
