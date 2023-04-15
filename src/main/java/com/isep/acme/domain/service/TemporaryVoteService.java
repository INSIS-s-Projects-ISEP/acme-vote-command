package com.isep.acme.domain.service;

import java.util.UUID;

import com.isep.acme.domain.model.TemporaryVote;
import com.isep.acme.domain.model.Vote;

public interface TemporaryVoteService {

    TemporaryVote save(TemporaryVote temporaryVote);
    
    void deleteById(UUID temporaryVoteId);

    Vote toDefinitiveVote(UUID temporaryVoteId, UUID reviewId);

}
