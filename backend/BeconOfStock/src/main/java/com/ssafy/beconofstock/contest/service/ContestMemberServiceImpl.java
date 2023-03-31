package com.ssafy.beconofstock.contest.service;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.contest.dto.ContestMemberDto;
import com.ssafy.beconofstock.contest.dto.ContestMemberJoinReqDto;
import com.ssafy.beconofstock.contest.dto.ContestMemberJoinResDto;
import com.ssafy.beconofstock.contest.entity.Contest;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.contest.repository.ContestMemberRepository;
import com.ssafy.beconofstock.contest.repository.ContestRepository;
import com.ssafy.beconofstock.exception.NotFoundException;
import com.ssafy.beconofstock.strategy.repository.StrategyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContestMemberServiceImpl implements ContestMemberService{

    private final ContestMemberRepository contestMemberRepository;
    private final ContestRepository contestRepository;
    private final StrategyRepository strategyRepository;


    @Override
    public ContestMemberJoinResDto joinContestMember(OAuth2UserImpl user, ContestMemberJoinReqDto contestMemberJoinReqDto) {
        ContestMember contestMember = ContestMember.builder()
                .member(user.getMember())
                .contest(contestRepository.findById(contestMemberJoinReqDto.getContestId()).orElseThrow(() -> new NotFoundException()))
                .strategy(strategyRepository.findById(contestMemberJoinReqDto.getStrategyId()).orElseThrow(() -> new NotFoundException()))
                .ranking(999999999999999999L)
                .build();
        return new ContestMemberJoinResDto(contestMemberRepository.save(contestMember));
    }

    @Override
    public Boolean deleteContestMember(OAuth2UserImpl user, Long contestMemberId) {
        ContestMember contestMember = contestMemberRepository.findById(contestMemberId).orElse(null);
        if (contestMember == null || !contestMember.getMember().getId().equals(user.getMember().getId())) {
            return false;
        }
        contestMemberRepository.delete(contestMember);
        return true;
    }

    @Override
    public Page<ContestMemberDto> getContestStatus(OAuth2UserImpl user, Long contestId, Pageable pageable) {
        Page<ContestMember> contestMember = contestMemberRepository.findByContestId(user.getMember(), contestId, pageable);

        PageImpl<ContestMemberDto> result = new PageImpl<>(
                contestMember.stream().map(ContestMemberDto::new).collect(Collectors.toList()),
                pageable,
                contestMember.getTotalPages());
        return result;
    }

//    @Override
//    public void findContestMembersByRanking(Long contestId) {
//        List<ContestMember> contestMembers = contestMemberRepository.findContestMemberByRanking(contestId);
//        for (int i = 1; i < contestMembers.size(); i++) {
//            ContestMember cm = contestMembers.get(i);
//            cm.setRanking((long) (i));
//        }
//    }


}
