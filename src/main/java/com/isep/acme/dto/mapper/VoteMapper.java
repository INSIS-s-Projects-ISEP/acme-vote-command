package com.isep.acme.dto.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isep.acme.domain.model.Review;
import com.isep.acme.domain.model.Vote;
import com.isep.acme.domain.repository.ReviewRepository;
import com.isep.acme.dto.message.VoteMessage;
import com.isep.acme.dto.request.VoteRequest;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class VoteMapper {

    private final ObjectMapper objectMapper;
    private final ReviewRepository reviewRepository;

    public Vote toEntity(VoteRequest voteRequest){
        UUID reviewId = voteRequest.getReviewId();
        Optional<Review> optReview = reviewRepository.findById(reviewId);
        
        return new Vote(null,
            optReview.orElse(null),
            voteRequest.getVoteType(),
            voteRequest.getUser()
        );
    }

    public Vote toEntity(VoteMessage voteMessage){
        UUID reviewId = voteMessage.getReviewId();
        Optional<Review> optReview = reviewRepository.findById(reviewId);

        return new Vote(
            voteMessage.getVoteId(),
            optReview.orElseThrow(),
            voteMessage.getVoteType(),
            voteMessage.getUser()
        );
    }

    public VoteMessage toMessage(Vote vote){
        return new VoteMessage(
            vote.getVoteId(),
            vote.getReview().getReviewId(),
            vote.getVoteType(),
            vote.getUser()
        );
    }

    public List<VoteMessage> toMessageList(String messages) throws Exception {
        TypeReference<Map<String, List<VoteMessage>>> mapType = new TypeReference<>() {};
        Map<String, List<VoteMessage>> response = objectMapper.readValue(messages, mapType);
        return response.get("response");
    }

    public List<Vote> toEntityList(List<VoteMessage> messages){
        return (messages.stream()
            .map(this::toEntity)
            .collect(Collectors.toList())
        );
    }

}
