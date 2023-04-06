package com.isep.acme.dto.request;

import com.isep.acme.domain.model.enumerate.VoteType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VoteRequest {

    private String user;
    private Long reviewId;
    private VoteType voteType;
    
}