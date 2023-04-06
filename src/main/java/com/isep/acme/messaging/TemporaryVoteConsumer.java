package com.isep.acme.messaging;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.TemporaryVote;
import com.isep.acme.domain.service.TemporaryVoteService;
import com.isep.acme.dto.mapper.TemporaryVoteMapper;
import com.isep.acme.dto.message.TemporaryVoteMessage;
import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class TemporaryVoteConsumer {

    private final String instanceId;
    private final MessageConverter messageConverter;
    private final TemporaryVoteMapper temporaryVoteMapper;
    private final TemporaryVoteService temporaryVoteService;
    
    public void temporaryVoteCreated(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        MessageProperties messageProperties = message.getMessageProperties();
        if(messageProperties.getAppId().equals(instanceId)){
            channel.basicAck(tag, false);
            log.info("Received own message and ignore it.");
            return;
        }

        TemporaryVoteMessage temporaryVoteMessage = (TemporaryVoteMessage) messageConverter.fromMessage(message);
        TemporaryVote temporaryVote = temporaryVoteMapper.toEntity(temporaryVoteMessage);

        log.info("Temporary Vote received: " + temporaryVote.getTemporaryVoteId());
        temporaryVoteService.save(temporaryVote);
        log.info("Temporary Vote created: " + temporaryVote.getTemporaryVoteId());
        
    }
}