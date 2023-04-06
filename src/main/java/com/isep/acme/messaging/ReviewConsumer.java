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
public class ReviewConsumer {

    private final ReviewService reviewService;

    @RabbitListener(queues = "#{reviewCreatedQueue.name}")
    public void reviewCreated(Review review){
        log.info("Review received: " + review);
        reviewService.create(review);
        log.info("Review created: " + review);
    }

    @RabbitListener(queues = "#{reviewUpdatedQueue.name}")
    public void reviewUpdated(Review review){
        log.info("Review received: " + review.getReviewId());
        reviewService.moderateReview(review.getReviewId(), review.getApprovalStatus());
        log.info("Review updated: " + review.getReviewId());
    }

    @RabbitListener(queues = "#{reviewDeletedQueue.name}")
    public void reviewDeleted(Long reviewId){
        log.info("Review received: " + reviewId);
        reviewService.deleteReview(reviewId);
        log.info("Review deleted: " + reviewId);
    }


}
