package com.isep.acme.dto.request;

import com.isep.acme.domain.model.enumerate.VoteType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TemporaryVoteRequest {

    private String user;
    private VoteType voteType;
    private ReviewRequest review;

}
