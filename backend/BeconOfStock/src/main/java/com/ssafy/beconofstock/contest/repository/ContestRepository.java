package com.ssafy.beconofstock.contest.repository;

import com.ssafy.beconofstock.contest.entity.Contest;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {

    Page<Contest> findAll(Pageable pageable);

    @Query("SELECT c FROM Contest c WHERE c.id = :contestId")
    Contest findByContestId(Long contestId);
}
