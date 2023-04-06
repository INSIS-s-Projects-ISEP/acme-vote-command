package com.isep.acme.dto.mapper;

import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.TemporaryVote;
import com.isep.acme.dto.request.TemporaryVoteRequest;

@Component
public class TemporaryVoteMapper {
    
    public TemporaryVote toEntity(TemporaryVoteRequest temporaryVoteRequest){
        return new TemporaryVote(null,
            temporaryVoteRequest.getUser(),
            temporaryVoteRequest.getVoteType()
        );
    }
}
