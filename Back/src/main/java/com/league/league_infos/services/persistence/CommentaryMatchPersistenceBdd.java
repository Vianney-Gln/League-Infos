package com.league.league_infos.services.persistence;

import com.league.league_infos.dao.entity.CommentaryMatchEntity;
import com.league.league_infos.dao.entity.ParticipantMatchEntity;
import com.league.league_infos.dao.repository.CommentaryMatchRepository;
import com.league.league_infos.dao.repository.ParticipantMatchRepository;
import com.league.league_infos.dto.ia.CommentaryMatchDTO;
import com.league.league_infos.mapper.CommentaryMatchMapper;
import com.league.league_infos.services.spi.CommentaryMatchPersistence;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentaryMatchPersistenceBdd implements CommentaryMatchPersistence {

    private final CommentaryMatchRepository commentaryMatchRepository;
    private final ParticipantMatchRepository participantMatchRepository;

    public CommentaryMatchPersistenceBdd(CommentaryMatchRepository commentaryMatchRepository, ParticipantMatchRepository participantMatchRepository) {
        this.commentaryMatchRepository = commentaryMatchRepository;
        this.participantMatchRepository = participantMatchRepository;
    }

    @Override
    public Optional<CommentaryMatchDTO> getCommentaryByGameIdAndPuuid(Long gameId, String puuid) {
        Optional<CommentaryMatchEntity> commentaryMatchEntity = commentaryMatchRepository.getByGameIdAndPuuid(gameId, puuid);
        return commentaryMatchEntity.map(CommentaryMatchMapper::commentaryMatchEntityToCommentaryMatchDTO);
    }

    @Override
    public CommentaryMatchDTO persist(CommentaryMatchDTO commentaryMatchDTO, Long gameId, String puuid) {
        CommentaryMatchEntity commentaryMatchEntity = CommentaryMatchMapper.commentaryMatchDTOToCommentaryMatchEntity(commentaryMatchDTO);
        ParticipantMatchEntity participantMatchEntity = participantMatchRepository.getParticipantByGameIdAndPuuid(gameId, puuid);
        commentaryMatchEntity.setParticipantMatchEntity(participantMatchEntity);
        return CommentaryMatchMapper.commentaryMatchEntityToCommentaryMatchDTO(commentaryMatchRepository.save(commentaryMatchEntity));
    }
}
