package com.isep.acme.domain.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.Vote;
import com.isep.acme.domain.model.enumerate.ApprovalStatus;
import com.isep.acme.domain.repository.ReviewRepository;
import com.isep.acme.domain.service.ReviewService;
import com.isep.acme.domain.service.VoteService;
import com.isep.acme.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final VoteService voteService;
    private final ReviewRepository reviewRepository;

    @Override
    public Review save(Review review){
        return reviewRepository.save(review);
    }

    @Override
    public Review moderateReview(Long reviewID, ApprovalStatus approvalStatus){
        Review review = reviewRepository.findById(reviewID).orElseThrow(() -> {
            throw new ResourceNotFoundException("Review not found");
        });

        review.setApprovalStatus(approvalStatus);
        return reviewRepository.save(review);
    }

    @Override
    public void addVoteToReview(Long reviewId, Vote vote) {
        Optional<Review> optReview = reviewRepository.findById(reviewId);
        if(optReview.isEmpty()){
            throw new ResourceNotFoundException(Review.class, reviewId);
        }

        Review review = optReview.get();
        review.addVote(vote);
        voteService.save(vote);
    }

    @Override
    public void deleteReview(Long reviewId){
        reviewRepository.deleteById(reviewId);
    }

}