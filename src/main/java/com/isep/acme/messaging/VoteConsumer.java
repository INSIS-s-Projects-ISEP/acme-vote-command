package com.isep.acme.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.service.ReviewService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class VoteConsumer {

    private final VoteService voteService;

    @RabbitListener(queues = "#{voteCreatedQueue.name}")
    public void voteCreated(Vote vote){
        log.info("Vote received: " + vote);
        voteService.create(Vote);
        log.info("Vote created: " + vote);
    }
}
