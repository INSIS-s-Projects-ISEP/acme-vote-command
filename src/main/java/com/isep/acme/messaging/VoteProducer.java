package com.isep.acme.messaging;

import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Vote;
import com.isep.acme.dto.mapper.VoteMapper;
import com.isep.acme.dto.message.VoteMessage;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class VoteProducer {

    private final RabbitmqService rabbitmqService;
    private final VoteMapper voteMapper;

    public void voteCreated(Vote vote){
        VoteMessage voteMessage = voteMapper.toMessage(vote);
        rabbitmqService.sendMessage("vote.vote-created", "", voteMessage);
    }

}