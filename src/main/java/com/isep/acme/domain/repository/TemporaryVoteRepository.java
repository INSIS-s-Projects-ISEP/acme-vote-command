package com.isep.acme.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isep.acme.domain.model.TemporaryVote;

@Repository
public interface TemporaryVoteRepository extends JpaRepository<TemporaryVote, UUID> {
    
}
