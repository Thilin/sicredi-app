package com.example.sicrediapp.model.repositories;

import com.example.sicrediapp.model.entity.Votation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotationRepository extends JpaRepository<Votation, Long> {
    Votation findBySessionIdAndAssociateId(Long id, Long associateId);

    Long countBySessionIdAndVoteTrue(Long sessionId);

    Long countBySessionIdAndVoteFalse(Long sessionId);
}
