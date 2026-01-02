package com.league.league_infos.services.persistence;

import com.league.league_infos.dao.entity.InfoMatchEntity;
import com.league.league_infos.dao.repository.InfoMatchRepository;
import com.league.league_infos.dto.match.MatchDTO;
import com.league.league_infos.mapper.InfoMatchMapper;
import com.league.league_infos.mapper.MatchMapper;
import com.league.league_infos.services.spi.HistoryPersistence;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryPersistenceBdd implements HistoryPersistence {

    private final InfoMatchRepository infoMatchRepository;

    @Autowired
    public HistoryPersistenceBdd(InfoMatchRepository infoMatchRepository) {
        this.infoMatchRepository = infoMatchRepository;
    }

    @Override
    @Transactional()
    public void persistMatchHistory(List<MatchDTO> listMatch) {
        List<InfoMatchEntity> infoMatchEntitiesToPersist = new ArrayList<>();

        listMatch.forEach(match -> {
            InfoMatchEntity infoMatchEntity = infoMatchRepository.findByGameId(match.getInfo().getGameId());
            if (infoMatchEntity == null) {
                infoMatchEntitiesToPersist.add(InfoMatchMapper.infoMatchdtoToEntity(match));
            }
        });
        infoMatchEntitiesToPersist.forEach(match -> match.setCreationDate(LocalDateTime.now()));
        infoMatchRepository.saveAll(infoMatchEntitiesToPersist);
    }

    @Override
    public List<MatchDTO> getMatchHistoryByGameIds(List<String> gameIds) {
        List<InfoMatchEntity> infoMatchs = new ArrayList<>();
        gameIds.forEach(gameId -> {
            String gameIdFormat = gameId.split("_")[1];
            InfoMatchEntity infoMatchEntity = infoMatchRepository.findByGameId(Long.parseLong(gameIdFormat));
            if (infoMatchEntity != null) {
                infoMatchs.add(infoMatchEntity);
            }
        });
        return infoMatchs.stream().map(MatchMapper::infoMatchEntityToMatchDTO).toList();
    }

    @Override
    public boolean wasCreatedWithinLastHour(String puuid, Integer queue) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1L);
        List<InfoMatchEntity> infoMatchEntities = infoMatchRepository.findRecentsMatchByPuuidAndQueue(puuid, oneHourAgo, queue);
        return !infoMatchEntities.isEmpty();
    }

    @Override
    public List<MatchDTO> findAllMatchByPuuidAndQueue(String puuid, Integer queue) {
        return infoMatchRepository.findAllMatchByPuuidAndQueue(puuid, queue).stream().map(MatchMapper::infoMatchEntityToMatchDTO).toList();
    }

    @Override
    public List<MatchDTO> findAllMatchByPuuidAndQueueBeforeGivenDate(String puuid, Long gameCreation, Integer queue) {
        return infoMatchRepository.findAllMatchByPuuidAndQueueBeforeGivenDate(puuid, gameCreation, queue).stream().map(MatchMapper::infoMatchEntityToMatchDTO).toList();
    }

    @Override
    public MatchDTO findMatchByGameId(Long gameId) {
        return MatchMapper.infoMatchEntityToMatchDTO(infoMatchRepository.findByGameId(gameId));
    }
}
