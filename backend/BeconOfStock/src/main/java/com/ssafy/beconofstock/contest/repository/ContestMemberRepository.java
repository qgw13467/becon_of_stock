package com.ssafy.beconofstock.contest.repository;

import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ContestMemberRepository extends JpaRepository<ContestMember, Long> {

    @Query("SELECT cm FROM ContestMember cm LEFT JOIN FETCH cm.contest LEFT JOIN FETCH cm.strategy WHERE cm.member = :member")
   List<ContestMember> findByMemberFetch(Member member);
    @Query("SELECT cm FROM ContestMember cm LEFT JOIN FETCH cm.contest LEFT JOIN FETCH cm.strategy WHERE cm.member.providerId = :providerId")
    List<ContestMember> findByMemberFetch(String providerId);

//    @Query("SELECT cm FROM ContestMember cm LEFT JOIN FETCH cm.contest WHERE cm.contest.id=:contestId")
//    Page<ContestMember> findByContestId(Long contestId, Pageable pageable);

}
