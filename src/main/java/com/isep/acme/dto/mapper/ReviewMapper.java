package com.isep.acme.dto.mapper;

import org.springframework.stereotype.Component;

import com.isep.acme.domain.model.Review;
import com.isep.acme.dto.message.ReviewMessage;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReviewMapper {

    public Review toEntity(ReviewMessage reviewMessage){
        return new Review(
            reviewMessage.getIdReview(),
            reviewMessage.getApprovalStatus()
        );
    }

}
