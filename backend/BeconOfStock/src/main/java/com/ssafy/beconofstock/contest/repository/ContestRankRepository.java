package com.ssafy.beconofstock.contest.repository;

import com.ssafy.beconofstock.contest.entity.ContestRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContestRankRepository extends JpaRepository<ContestRank, Long> {

        @Query("SELECT cr FROM ContestRank cr WHERE cr.contest.id=:contestId")
        List<ContestRank> findContestRankByContestId(@Param("contestId") Long contestId);
}
