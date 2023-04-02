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

import java.util.Collections;
import java.util.Comparator;
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

    @Override
    public Boolean updateRankingByContest(Long contestId) {
        Contest contest = contestRepository.findByContestId(contestId);
        List<ContestMember> contestMembers = contestMemberRepository.findByRank(contestId);
        String des = contest.getDescription();
        for (ContestMember contestMember:contestMembers) {
            if (contestMember == null) {
                return false;
            }
        }

//        Comparator<ContestMember> comparator = null;
//        if (des.equals("strategyMDD")) {
//            comparator = Comparator.comparing(ContestMember::getStrategyMDD);
//        } else if (des.equals("strategyCumulativeReturn")) {
//            comparator = Comparator.comparing(ContestMember::getStrategyCumulativeReturn).reversed();
//        } else if (des.equals("strategyCagr")) {
//            comparator = Comparator.comparing(ContestMember::getStrategyCagr).reversed();
//        } else if (des.equals("strategySharpe")) {
//            comparator = Comparator.comparing(ContestMember::getStrategySharpe).reversed();
//        } else if (des.equals("strategySortino")) {
//            comparator = Comparator.comparing(ContestMember::getStrategySortino).reversed();
//        } else if (des.equals("strategyRevenue")) {
//            comparator = Comparator.comparing(ContestMember::getStrategyRevenue).reversed();
//        }
//        if (comparator == null) {
//            // Invalid description, do nothing
//            return;
//        }
//
//        Collections.sort(contestMembers, comparator);
//
//        int rank = 1;
//        for (ContestMember cm : contestMembers) {
//            cm.setRanking((long) rank);
//            rank++;
//        }
        Collections.sort(contestMembers, (a, b) -> {
            int compare = 0;
            if (a.getStrategy().getStrategyMDD() != null && b.getStrategy().getStrategyMDD() != null && des.equals("strategyMDD")) {
                compare = a.getStrategy().getStrategyMDD().compareTo(b.getStrategy().getStrategyMDD());
            }
            if (compare == 0 && des.equals("strategyCumulativeReturn")) {
                compare = -a.getStrategy().getStrategyCumulativeReturn().compareTo(b.getStrategy().getStrategyCumulativeReturn());
            }
            if (compare == 0 && des.equals("strategyCagr")) {
                compare = -a.getStrategy().getStrategyCagr().compareTo(b.getStrategy().getStrategyCagr());
            }
            if (compare == 0 && des.equals("strategySharpe")) {
                compare = -a.getStrategy().getStrategySharpe().compareTo(b.getStrategy().getStrategySharpe());
            }
            if (compare == 0 && des.equals("strategySortino")) {
                compare = -a.getStrategy().getStrategySortino().compareTo(b.getStrategy().getStrategySortino());
            }
            if (compare == 0 && des.equals("strategyRevenue")) {
                compare = -a.getStrategy().getStrategyRevenue().compareTo(b.getStrategy().getStrategyRevenue());
            }
            return compare;
        });

        int rank = 1;
        for (ContestMember cm : contestMembers) {
            cm.setRanking((long) rank);
            rank++;
            contestMemberRepository.save(cm);
        }
        return true;
    }

}
