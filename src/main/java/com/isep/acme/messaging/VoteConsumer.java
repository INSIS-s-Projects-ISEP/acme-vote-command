package com.isep.acme.messaging;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Vote;
import com.isep.acme.domain.service.VoteService;
import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class VoteConsumer {

    private final String instanceId;
    private final VoteService voteService;
    private final MessageConverter messageConverter;

    @RabbitListener(queues = "#{voteCreatedQueue.name}", ackMode = "MANUAL")
    public void voteCreated(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        MessageProperties messageProperties = message.getMessageProperties();
        if(messageProperties.getAppId().equals(instanceId)){
            channel.basicAck(tag, false);
            log.info("Received own message.");
            return;
        }
        
        Vote vote = (Vote) messageConverter.fromMessage(message);
        log.info("Vote received: " + vote);
        voteService.create(vote);
        channel.basicAck(tag, false);
        log.info("Vote created: " + vote);
    }

    public void voteUpdated(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        MessageProperties messageProperties = message.getMessageProperties();
        if(messageProperties.getAppId().equals(instanceId)){
            channel.basicAck(tag, false);
            log.info("Received own message.");
            return;
        }
        
        Vote vote = (Vote) messageConverter.fromMessage(message);
        log.info("Vote received: " + vote);
        voteService.updateVoteType(vote, vote.getVoteType());
        channel.basicAck(tag, false);
        log.info("Vote updated: " + vote);
    }

    public void voteDeleted(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        MessageProperties messageProperties = message.getMessageProperties();
        if(messageProperties.getAppId().equals(instanceId)){
            channel.basicAck(tag, false);
            log.info("Received own message.");
            return;
        }
        
        Vote vote = (Vote) messageConverter.fromMessage(message);
        log.info("Vote received: " + vote.getVoteId());
        voteService.deleteById(vote.getVoteId());
        channel.basicAck(tag, false);
        log.info("Vote deleted: " + vote.getVoteId());
    }
}
