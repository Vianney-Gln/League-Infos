package com.league.league_infos.services.ia;

import com.league.league_infos.common.enums.ObjectivesEnum;
import com.league.league_infos.common.utils.StringUtils;
import com.league.league_infos.dto.ia.ContextGenerationCommentaryDTO;
import com.league.league_infos.dto.ia.EventMatchDTO;
import com.league.league_infos.dto.ia.GenerationCommentaryDTO;
import com.league.league_infos.dto.ia.ParticipantGenerationCommentaryDTO;
import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.services.api.GenerationCommentaryService;
import com.league.league_infos.services.api.MatchTimelineService;
import com.league.league_infos.services.spi.HistoryPersistence;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerationCommentaryServiceImpl implements GenerationCommentaryService {

    private final MatchTimelineService matchTimelineService;
    private final HistoryPersistence historyPersistence;

    public GenerationCommentaryServiceImpl(MatchTimelineService matchTimelineService, HistoryPersistence historyPersistence) {
        this.matchTimelineService = matchTimelineService;
        this.historyPersistence = historyPersistence;
    }

    @Override
    public GenerationCommentaryDTO getGenerationCommentaryDTO(Long gameId, String puuid, String matchId, String pseudo) throws Exception {

        ContextGenerationCommentaryDTO contextGenerationCommentaryDTO = new ContextGenerationCommentaryDTO.Builder()
                .puuidFocusedPlayer(puuid)
                .pseudoFocusedPlayer(pseudo)
                .build();

        List<EventMatchDTO> eventMatchDTOS = matchTimelineService.getMatchEventsByMatchId(matchId);
        eventMatchDTOS.forEach(ev -> {
            ev.setTime(StringUtils.milliSecondsToTimeStr(ev.getTimestamp()));
            computeMonsterType(ev);
            computeDragonType(ev);
            computeTurretOrInhibType(ev);
        });

        MatchDTO matchDTO = historyPersistence.findMatchByGameId(gameId);

        List<ParticipantGenerationCommentaryDTO> participantRequestCommentaries = matchDTO.getInfo().getParticipants().stream().map(participant ->
                new ParticipantGenerationCommentaryDTO.Builder()
                        .puuid(participant.getPuuid())
                        .kills(participant.getKills())
                        .assists(participant.getAssists())
                        .deaths(participant.getDeaths())
                        .goldEarned(participant.getGoldEarned())
                        .participantId(participant.getParticipantId())
                        .role(participant.getTeamPosition())
                        .championName(participant.getChampionName())
                        .win(participant.getWin())
                        .build()
        ).toList();

        contextGenerationCommentaryDTO.setParticipants(participantRequestCommentaries);
        return new GenerationCommentaryDTO.Builder()
                .contextGenerationCommentaryDTO(contextGenerationCommentaryDTO)
                .eventsMatchDto(eventMatchDTOS)
                .build();
    }

    private void computeMonsterType(EventMatchDTO eventMatch) {
        if (eventMatch.getMonsterType() != null && !ObjectivesEnum.DRAGON.getCode().equals(eventMatch.getMonsterType())) {
            eventMatch.setMonsterType(ObjectivesEnum.fromCode(eventMatch.getMonsterType()).getCode());
            eventMatch.setMonsterSubType(ObjectivesEnum.fromCode(eventMatch.getMonsterType()).getLibelle());
        }
    }

    private void computeDragonType(EventMatchDTO eventMatch) {
        if (eventMatch.getMonsterType() != null && ObjectivesEnum.DRAGON.getCode().equals(eventMatch.getMonsterType())) {
            eventMatch.setDragonType(ObjectivesEnum.fromCode(eventMatch.getMonsterSubType()).getLibelle());
        }
    }

    private void computeTurretOrInhibType(EventMatchDTO eventMatch) {
        if (ObjectivesEnum.BUILDING_KILL.getCode().equals(eventMatch.getType())) {
            if (ObjectivesEnum.TOWER_BUILDING.getCode().equals(eventMatch.getBuildingType())) {
                eventMatch.setTowerType(ObjectivesEnum.fromCode(eventMatch.getTowerType()).getLibelle());
            }

            if (ObjectivesEnum.INHIBITOR_BUILDING.getCode().equals(eventMatch.getBuildingType())) {
                eventMatch.setTowerType(ObjectivesEnum.INHIBITOR.getLibelle());
            }
        }
    }
}
