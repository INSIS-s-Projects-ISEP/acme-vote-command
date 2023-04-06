package com.isep.acme.dto.message;

import com.isep.acme.domain.model.enumerate.VoteType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class VoteMessage {
    private Long voteId;
    private Long reviewId;
    private VoteType voteType;
    private String user;
}
