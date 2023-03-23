package com.ssafy.beconofstock.contest.service;


import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.contest.dto.ContestRequestDto;
import com.ssafy.beconofstock.contest.dto.ContestResponseDto;
import com.ssafy.beconofstock.contest.entity.Contest;
import com.ssafy.beconofstock.contest.repository.ContestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepo;

    @Override
    public ContestResponseDto createContest(ContestRequestDto contestReq) {
        Contest contest = Contest.builder()
                .id(contestReq.getContestId())
                .description(contestReq.getDescription())
                .content(contestReq.getContent())
                .build();
        return new ContestResponseDto(contestRepo.save(contest));
    }

    @Override
    public List<ContestResponseDto> getContestList() {
        List<Contest> contestList = contestRepo.findAll();
        return contestList.stream().map(ContestResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public Contest getContestDetail(Long contestId) {
        return contestRepo.findById(contestId).orElse(null);
    }

    @Override
    public void deleteContest(Long contestId) {
        contestRepo.deleteById(contestId);
    }

//    @Override
//    public ContestResponseDto updateContest(Long contestId) {
//        return null;
//    }
}
