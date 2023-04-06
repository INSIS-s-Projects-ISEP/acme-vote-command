package com.isep.acme.domain.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.isep.acme.domain.model.TemporaryVote;
import com.isep.acme.domain.repository.TemporaryVoteRepository;
import com.isep.acme.domain.service.TemporaryVoteService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TemporaryVoteServiceImpl implements TemporaryVoteService {

    private final TemporaryVoteRepository temporaryVoteRepository;

    @Override
    public TemporaryVote save(TemporaryVote temporaryVote) {
        return temporaryVoteRepository.save(temporaryVote);
    }
    
    @Override
    public void deleteById(UUID temporaryVoteId) {
        temporaryVoteRepository.deleteById(temporaryVoteId);
    }

}
