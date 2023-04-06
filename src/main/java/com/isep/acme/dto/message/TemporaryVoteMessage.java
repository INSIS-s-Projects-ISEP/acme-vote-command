package com.isep.acme.dto.message;

import java.util.UUID;

import com.isep.acme.domain.model.enumerate.VoteType;
import com.isep.acme.dto.request.ReviewRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryVoteMessage {

    private UUID temporaryVoteId;
    private String user;
    private VoteType voteType;

    private ReviewRequest reviewRequest;
    
}