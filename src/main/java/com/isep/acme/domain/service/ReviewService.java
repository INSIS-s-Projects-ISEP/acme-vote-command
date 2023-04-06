package com.isep.acme.domain.service;

import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.Vote;
import com.isep.acme.domain.model.enumerate.ApprovalStatus;

public interface ReviewService {

    Review save(Review review);
    
    Review moderateReview(Long reviewID, ApprovalStatus approvalStatus);
    
    void addVoteToReview(Long reviewId, Vote vote);

    void deleteReview(Long reviewId);

}
