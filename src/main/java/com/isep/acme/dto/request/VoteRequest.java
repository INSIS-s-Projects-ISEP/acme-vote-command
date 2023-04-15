package com.isep.acme.dto.request;

import java.util.UUID;

import com.isep.acme.domain.model.enumerate.VoteType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VoteRequest {

    private String user;
    private UUID reviewId;
    private VoteType voteType;
    
}