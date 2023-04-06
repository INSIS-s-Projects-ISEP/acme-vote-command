package com.isep.acme.dto.mapper;

import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.TemporaryVote;
import com.isep.acme.dto.message.TemporaryVoteMessage;
import com.isep.acme.dto.request.ReviewRequest;
import com.isep.acme.dto.request.TemporaryVoteRequest;

@Component
public class TemporaryVoteMapper {
    
    public TemporaryVote toEntity(TemporaryVoteRequest temporaryVoteRequest){
        return new TemporaryVote(null,
            temporaryVoteRequest.getUser(),
            temporaryVoteRequest.getVoteType()
        );
    }

    public TemporaryVoteMessage toMessage(TemporaryVote temporaryVote, ReviewRequest reviewRequest){
        return new TemporaryVoteMessage(
            temporaryVote.getTemporaryVoteId(),
            temporaryVote.getUser(),
            temporaryVote.getVoteType(),
            reviewRequest
        );
    }
}
