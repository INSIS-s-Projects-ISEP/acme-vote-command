package com.isep.acme.messaging;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.TemporaryVote;
import com.isep.acme.dto.mapper.TemporaryVoteMapper;
import com.isep.acme.dto.message.TemporaryVoteMessage;
import com.isep.acme.dto.request.ReviewRequest;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TemporaryVoteProducer {

    private final RabbitmqService rabbitmqService;
    private final TemporaryVoteMapper temporaryVoteMapper;

    public void temporaryVoteCreated(TemporaryVote temporaryVote, ReviewRequest reviewRequest){
        TemporaryVoteMessage temporaryVoteMessage = temporaryVoteMapper.toMessage(temporaryVote, reviewRequest);
        rabbitmqService.sendMessage("temporary-vote.temporary-vote-created", "", temporaryVoteMessage);
    }

    public void definitiveVoteCreated(UUID temporaryId){
        rabbitmqService.sendMessage("vote.definitive-vote-created", "", temporaryId);
    }

}
