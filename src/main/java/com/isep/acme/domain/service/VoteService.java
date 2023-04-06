package com.isep.acme.domain.service;

import com.isep.acme.domain.model.Vote;
import com.isep.acme.domain.model.enumerate.VoteType;

public interface VoteService {

    Vote save(Vote vote);

    Vote updateVoteType(Vote vote, VoteType voteType);
    
    void deleteById(Long voteId);
}
