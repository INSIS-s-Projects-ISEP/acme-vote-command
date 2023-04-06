package com.isep.acme.domain.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.isep.acme.domain.model.enumerate.ApprovalStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    private Long reviewId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @OneToMany(mappedBy = "review")
    private Set<Vote> votes = new HashSet<>();

    public Review(Long reviewId, ApprovalStatus approvalStatus) {
        this.reviewId = reviewId;
        this.approvalStatus = approvalStatus;
    }

    public void addVote(Vote vote){
        if(!approvalStatus.equals(ApprovalStatus.APPROVED)){
            throw new RuntimeException("Review is not approved");
        }
        votes.add(vote);
    }
}
