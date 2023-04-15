package com.isep.acme.dto.message;

import java.util.UUID;

import com.isep.acme.domain.model.enumerate.ApprovalStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMessage {

    private UUID reviewId;
    private ApprovalStatus approvalStatus;

}
