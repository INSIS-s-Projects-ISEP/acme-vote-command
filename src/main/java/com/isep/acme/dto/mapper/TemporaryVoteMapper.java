package com.isep.acme.dto.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isep.acme.domain.model.TemporaryVote;
import com.isep.acme.dto.message.TemporaryVoteMessage;
import com.isep.acme.dto.request.ReviewRequest;
import com.isep.acme.dto.request.TemporaryVoteRequest;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TemporaryVoteMapper {

    private final ObjectMapper objectMapper;
    
    public TemporaryVote toEntity(TemporaryVoteRequest temporaryVoteRequest){
        TemporaryVote temporaryVote = new TemporaryVote();
        temporaryVote.setUser(temporaryVoteRequest.getUser());
        temporaryVote.setVoteType(temporaryVoteRequest.getVoteType());
        return temporaryVote;
    }

    public TemporaryVote toEntity(TemporaryVoteMessage temporaryVoteMessage){
        return new TemporaryVote(
            temporaryVoteMessage.getTemporaryVoteId(),
            temporaryVoteMessage.getUser(),
            temporaryVoteMessage.getVoteType()
        );
    }

    public TemporaryVoteMessage toMessage(TemporaryVote temporaryVote, ReviewRequest reviewRequest){
        return new TemporaryVoteMessage(
            temporaryVote.getTemporaryVoteId(),
            temporaryVote.getUser(),
            temporaryVote.getVoteType(),
            reviewRequest
        );
    }

    public List<TemporaryVoteMessage> toMessageList(String messages) throws Exception {
        TypeReference<Map<String, List<TemporaryVoteMessage>>> mapType = new TypeReference<>() {};
        Map<String, List<TemporaryVoteMessage>> response = objectMapper.readValue(messages, mapType);
        return response.get("response");
    }

    public List<TemporaryVote> toEntityList(List<TemporaryVoteMessage> messages){
        return (messages.stream()
            .map(this::toEntity)
            .collect(Collectors.toList())
        );
    }
}
