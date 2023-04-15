package com.isep.acme.domain.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.isep.acme.domain.model.Vote;
import com.isep.acme.domain.model.enumerate.VoteType;
import com.isep.acme.domain.repository.VoteRepository;
import com.isep.acme.domain.service.VoteService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {
    
    private final VoteRepository voteRepository;

    @Override
    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public Vote updateVoteType(Vote vote, VoteType voteType){
        vote.setVoteType(voteType);
        return voteRepository.save(vote);
    }

    public void deleteById(UUID voteId){
        voteRepository.deleteById(voteId);
    }
    
}
