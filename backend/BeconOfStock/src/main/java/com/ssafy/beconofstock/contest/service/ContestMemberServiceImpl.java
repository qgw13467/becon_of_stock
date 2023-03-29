package com.ssafy.beconofstock.contest.service;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.contest.repository.ContestMemberRepository;
import com.ssafy.beconofstock.contest.repository.ContestRepository;
import com.ssafy.beconofstock.strategy.repository.StrategyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContestMemberServiceImpl implements ContestMemberService{

    private final ContestMemberRepository contestMemberRepository;
    private final ContestRepository contestRepository;
    private final StrategyRepository strategyRepository;


    @Override
    public ContestMember joinContestMember(OAuth2UserImpl user, Long contestId, Long strategyId) {
        ContestMember contestMember = ContestMember.builder()
                .member(user.getMember())
                .contest(contestRepository.findByContestId(contestId))
                .strategy(strategyRepository.findByStrategyId(strategyId))
                .ranking(0L)
                .build();
        return contestMemberRepository.save(contestMember);
    }

    @Override
    public Boolean deleteContestMember(OAuth2UserImpl user, Long contestMemberId) {
        ContestMember contestMember = contestMemberRepository.findById(contestMemberId).orElse(null);
        if (!contestMember.getMember().getId().equals(user.getMember().getId())) {
            return false;
        }
        contestMemberRepository.delete(contestMember);
        return true;
    }

//    @Override
//    public Page<ContestMember> getContestStatus(Long contestId, Pageable pageable) {
//        Page<ContestMember> contestMember = contestMemberRepository.findByContestId(contestId, pageable);
//
//        PageImpl<ContestMember> result = new PageImpl<>(
//                contestMember.toList(),
//                pageable,
//                contestMember.getTotalPages());
//        return result;
//    }


}
