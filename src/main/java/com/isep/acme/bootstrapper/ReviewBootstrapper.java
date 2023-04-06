package com.isep.acme.bootstrapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.service.ReviewService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ReviewBootstrapper implements CommandLineRunner {

    private final ReviewService reviewService;

    @Override
    public void run(String... args) throws Exception {

        Review review = new Review();
        review.setApprovalStatus("approved");
        reviewService.save(review);

        log.info("Review created: " + review.getReviewId());

    }
    
}
