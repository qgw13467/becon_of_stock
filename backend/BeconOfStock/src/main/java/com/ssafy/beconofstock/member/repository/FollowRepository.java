package com.ssafy.beconofstock.member.repository;

import com.ssafy.beconofstock.member.entity.Follow;
import com.ssafy.beconofstock.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowed(Member member);

    List<Follow> findByFollowing(Member member);

    Follow findByFollowingAndFollowed(Member member, Member followedMember);
}
