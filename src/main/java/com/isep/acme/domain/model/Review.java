package com.isep.acme.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.isep.acme.domain.model.enumerate.ApprovalStatus;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;

    @Column(nullable = false)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @OneToMany(mappedBy = "review")
    private Set<Vote> votes = new HashSet<>();

    public void addVote(Vote vote){
        if(!approvalStatus.equals(ApprovalStatus.APPROVED)){
            throw new RuntimeException("Review is not approved");
        }
        votes.add(vote);
    }
}
