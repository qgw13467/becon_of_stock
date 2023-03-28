package com.ssafy.beconofstock.contest.repository;

import com.ssafy.beconofstock.contest.entity.Contest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Long> {

    Page<Contest> findAll(Pageable pageable);
}
