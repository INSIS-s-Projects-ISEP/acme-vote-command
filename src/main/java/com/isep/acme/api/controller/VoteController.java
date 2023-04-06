package com.isep.acme.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isep.acme.domain.model.Vote;
import com.isep.acme.domain.service.ReviewService;
import com.isep.acme.dto.mapper.VoteMapper;
import com.isep.acme.dto.request.VoteRequest;
import com.isep.acme.messaging.VoteProducer;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@RestController
@AllArgsConstructor
@Log
@RequestMapping("votes")
public class VoteController {

    private final ReviewService reviewService;
    private final VoteProducer voteProducer;
    private final VoteMapper voteMapper;
    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody VoteRequest voteRequest){
        Vote vote = voteMapper.toEntity(voteRequest);
        reviewService.addVoteToReview(voteRequest.getReviewId(), vote);
        voteProducer.voteCreated(vote);
        log.info("Vote created: " + vote.getVoteId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
