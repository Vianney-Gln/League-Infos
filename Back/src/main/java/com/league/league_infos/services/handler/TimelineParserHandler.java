package com.league.league_infos.services.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.league.league_infos.common.enums.MatchEventEnum;
import com.league.league_infos.dto.ia.EventMatchDTO;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TimelineParserHandler {

    public List<EventMatchDTO> parseTimeline(InputStream jsonStream) throws Exception {
        List<EventMatchDTO> eventsList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        List<String> targetEvents = List.of(MatchEventEnum.BUILDING_KILL.getLibelle(), MatchEventEnum.CHAMPION_KILL.getLibelle(),
                MatchEventEnum.ELITE_MONSTER_KILL.getLibelle());

        JsonNode rootNode = mapper.readTree(jsonStream);
        JsonNode infoNode = rootNode.get("info");
        JsonNode frames = infoNode != null ? infoNode.get("frames") : null;

        if (infoNode == null || infoNode.isEmpty() || frames == null || frames.isEmpty()) {
            return Collections.emptyList();
        }
        frames.forEach(frame -> {
            JsonNode events = frame.get("events");
            if (events != null) {
                events.forEach(eventNode -> {
                    try {
                        EventMatchDTO eventMatchDTO = mapper.treeToValue(eventNode, EventMatchDTO.class);
                        if (targetEvents.contains(eventMatchDTO.getType())) {
                            eventsList.add(eventMatchDTO);
                        }
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
        return eventsList;
    }
}
