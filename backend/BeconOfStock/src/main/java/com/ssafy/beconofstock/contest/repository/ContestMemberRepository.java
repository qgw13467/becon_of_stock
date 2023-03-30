package com.ssafy.beconofstock.contest.repository;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface ContestMemberRepository extends JpaRepository<ContestMember, Long> {

    @Query("SELECT cm FROM ContestMember cm LEFT JOIN FETCH cm.contest LEFT JOIN FETCH cm.strategy WHERE cm.member = :member")
   List<ContestMember> findByMemberFetch(Member member);
    @Query("SELECT cm FROM ContestMember cm LEFT JOIN FETCH cm.contest LEFT JOIN FETCH cm.strategy WHERE cm.member.providerId = :providerId")
    List<ContestMember> findByMemberFetch(String providerId);

    @Query("SELECT cm FROM ContestMember cm WHERE cm.member=:member AND cm.contest.id=:contestId")
    Page<ContestMember> findByContestId(Member member, Long contestId, Pageable pageable);

    @Query("SELECT cm FROM ContestMember cm WHERE cm.contest.id=:contestId ORDER BY cm.strategy.cumulativeReturn DESC")
    List<ContestMember> findContestMemberByRanking(@Param("contestId") Long contestId);

}
