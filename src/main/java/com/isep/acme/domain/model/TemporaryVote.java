package com.isep.acme.domain.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.isep.acme.domain.model.enumerate.VoteType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryVote {
    
    @Id
    private UUID temporaryVoteId = UUID.randomUUID();
    private String user;
    private VoteType voteType;

}
