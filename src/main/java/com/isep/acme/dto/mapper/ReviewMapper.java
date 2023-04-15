package com.isep.acme.dto.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isep.acme.domain.model.Review;
import com.isep.acme.dto.message.ReviewMessage;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReviewMapper {

    private final ObjectMapper objectMapper;

    public Review toEntity(ReviewMessage reviewMessage){
        return new Review(
            reviewMessage.getReviewId(),
            reviewMessage.getApprovalStatus()
        );
    }

    public List<ReviewMessage> toMessageList(String messages) throws Exception {
        TypeReference<Map<String, List<ReviewMessage>>> mapType = new TypeReference<>() {};
        Map<String, List<ReviewMessage>> response = objectMapper.readValue(messages, mapType);
        return response.get("response");
    }

    public List<Review> toEntityList(List<ReviewMessage> messages) {
        return (messages.stream()
            .map(this::toEntity)
            .collect(Collectors.toList())
        );
    }

}
