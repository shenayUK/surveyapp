package com.turkcell.pollservice.repository;


import com.turkcell.pollservice.domain.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Choice st WHERE st.poll.id = ?1")
    int deleteByPollId(Long id);

}
