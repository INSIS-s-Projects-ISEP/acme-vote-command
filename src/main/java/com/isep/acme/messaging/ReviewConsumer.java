package com.isep.acme.messaging;

import java.io.IOException;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.Vote;
import com.isep.acme.domain.service.ReviewService;
import com.isep.acme.domain.service.TemporaryVoteService;
import com.isep.acme.dto.mapper.ReviewMapper;
import com.isep.acme.dto.message.ReviewForTemporaryVoteMessage;
import com.isep.acme.dto.message.ReviewMessage;
import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ReviewConsumer {

    private final TemporaryVoteService temporaryVoteService;
    private final ReviewService reviewService;
    
    private final ReviewMapper reviewMapper;
    private final TemporaryVoteProducer temporaryVoteProducer;

    @RabbitListener(queues = "#{reviewCreatedQueue.name}", ackMode = "MANUAL")
    public void reviewCreated(ReviewMessage reviewMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        Review review = reviewMapper.toEntity(reviewMessage);
        log.info("Review received: " + review.getReviewId());

        reviewService.save(review);
        channel.basicAck(tag, false);

        log.info("Review created: " + review.getReviewId());
    }

    @RabbitListener(queues = "#{reviewUpdatedQueue.name}", ackMode = "MANUAL")
    public void reviewUpdated(ReviewMessage reviewMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        Review review = reviewMapper.toEntity(reviewMessage);
        log.info("Review received: " + review.getReviewId());

        reviewService.moderateReview(review.getReviewId(), review.getApprovalStatus());
        channel.basicAck(tag, false);

        log.info("Review updated: " + review.getReviewId());
    }

    @RabbitListener(queues = "#{reviewDeletedQueue.name}", ackMode = "MANUAL")
    public void reviewDeleted(Long reviewId, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        log.info("Review received: " + reviewId);
        reviewService.deleteReview(reviewId);
        channel.basicAck(tag, false);

        log.info("Review deleted: " + reviewId);
    }

    @RabbitListener(queues = "#{reviewCreatedForTemporaryVoteQueue.name}", ackMode = "MANUAL")
    public void reviewCreatedForTemporaryVote(ReviewForTemporaryVoteMessage reviewForTemporaryVoteMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException{

        Long reviewId = reviewForTemporaryVoteMessage.getReviewId();
        UUID temporaryVoteId = reviewForTemporaryVoteMessage.getTemporaryVoteId();

        temporaryVoteService.toDefinitiveVote(temporaryVoteId, reviewId);
        temporaryVoteProducer.definitiveVoteCreated(temporaryVoteId);

        channel.basicAck(tag, false);
        log.info("Definitive Vote created from Temporary Vote '" + temporaryVoteId + "'");

    }

}
