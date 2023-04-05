package com.ssafy.beconofstock.contest.repository;

import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestMemberRepository extends JpaRepository<ContestMember, Long> {

    @Query("SELECT cm FROM ContestMember cm LEFT JOIN FETCH cm.contest LEFT JOIN FETCH cm.strategy WHERE cm.member = :member")
   List<ContestMember> findByMemberFetch(Member member);
    @Query("SELECT cm FROM ContestMember cm LEFT JOIN FETCH cm.contest LEFT JOIN FETCH cm.strategy WHERE cm.member.providerId = :providerId")
    List<ContestMember> findByMemberFetch(String providerId);

    @Query("SELECT cm FROM ContestMember cm WHERE cm.contest.id=:contestId")
    Page<ContestMember> findByContestId(@Param("contestId") Long contestId, Pageable pageable);

    @Query("SELECT cm.strategy FROM ContestMember cm WHERE cm.contest.id=:contestId")
    List<Strategy> findStrategyByContestId(@Param("contestId") Long contestId);

    @Query("select cm from ContestMember cm where cm.contest.id=:contestId")
    List<ContestMember> findByRank(@Param("contestId") Long contestId);

    @Query("select cm.strategy.id from ContestMember cm Where cm.member=:member and cm.contest.id=:contestId")
    List<Long> contestJoinMember(@Param("member") Member member, @Param("contestId") Long contestId);
}
