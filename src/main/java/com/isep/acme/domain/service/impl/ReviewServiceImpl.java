package com.isep.acme.domain.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.Vote;
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
    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review moderateReview(Long reviewID, String approved){

        Optional<Review> r = reviewRepository.findById(reviewID);
        if(r.isEmpty()){
            throw new ResourceNotFoundException("Review not found");
        }

        Boolean ap = r.get().setApprovalStatus(approved);
        if(!ap) {
            throw new IllegalArgumentException("Invalid status value");
        }

        return reviewRepository.save(r.get());
    }

    @Override
    public void addVoteToReview(Long reviewId, Vote vote) {

        Optional<Review> optReview = reviewRepository.findById(reviewId);
        if(optReview.isEmpty()){
            throw new ResourceNotFoundException(Review.class, reviewId);
        }

        Review review = optReview.get();
        review.addVote(vote);

        voteService.create(vote);
    }

    @Override
    public void deleteReview(Long reviewId)  {
        reviewRepository.deleteById(reviewId);
    }

}