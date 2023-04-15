package com.isep.acme.domain.service;

import java.util.UUID;

import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.Vote;
import com.isep.acme.domain.model.enumerate.ApprovalStatus;

public interface ReviewService {

    Review save(Review review);
    
    Review moderateReview(UUID reviewID, ApprovalStatus approvalStatus);
    
    void addVoteToReview(UUID reviewId, Vote vote);

    void deleteReview(UUID reviewId);

}
