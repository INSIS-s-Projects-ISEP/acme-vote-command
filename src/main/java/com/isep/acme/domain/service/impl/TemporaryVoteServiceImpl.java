package com.isep.acme.domain.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.TemporaryVote;
import com.isep.acme.domain.model.Vote;
import com.isep.acme.domain.repository.ReviewRepository;
import com.isep.acme.domain.repository.TemporaryVoteRepository;
import com.isep.acme.domain.service.TemporaryVoteService;
import com.isep.acme.domain.service.VoteService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TemporaryVoteServiceImpl implements TemporaryVoteService {

    private final VoteService voteService;
    private final TemporaryVoteRepository temporaryVoteRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public TemporaryVote save(TemporaryVote temporaryVote) {
        return temporaryVoteRepository.save(temporaryVote);
    }

    @Override
    public void deleteById(UUID temporaryVoteId) {
        temporaryVoteRepository.deleteById(temporaryVoteId);
    }

    @Override
    public Vote toDefinitiveVote(UUID temporaryVoteId, UUID reviewId){

        TemporaryVote temporaryVote = temporaryVoteRepository.findById(temporaryVoteId).orElseThrow();
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        Vote vote = new Vote(null,
            review,
            temporaryVote.getVoteType(),
            temporaryVote.getUser()
        );

        return voteService.save(vote);
    }

}
