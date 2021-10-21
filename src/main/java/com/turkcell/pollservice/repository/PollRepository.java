package com.turkcell.pollservice.repository;

import com.turkcell.pollservice.domain.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

    Optional<Poll> findByIsActiveTrueAndId(Long pollId);
    Page<Poll> findByIsActiveTrue(Pageable pageable);
    Page<Poll> findByIsActiveFalse(Pageable pageable);
    Page<Poll> findAll(Pageable pageable);
    Page<Poll> findByIsActiveTrueAndIsApprovedTrue(Pageable pageable);
    Page<Poll> findByIsApprovedFalse(Pageable pageable);
    Page<Poll> findByIsApprovedTrue(Pageable pageable);
    Page<Poll> findByIsApprovedTrueAndIsActiveTrue(Pageable pageable);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Poll st WHERE st.id = ?1")
    int deleteByIdentifier(Long id);

}
