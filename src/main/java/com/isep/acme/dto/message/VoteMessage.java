package com.isep.acme.dto.message;

import java.util.UUID;

import com.isep.acme.domain.model.enumerate.VoteType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteMessage {
    private UUID voteId;
    private UUID reviewId;
    private VoteType voteType;
    private String user;
}
