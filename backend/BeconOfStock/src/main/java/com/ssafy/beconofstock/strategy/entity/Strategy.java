package com.ssafy.beconofstock.strategy.entity;



import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.dto.StrategyAddDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Strategy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String title;

    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    public Strategy(Member member, StrategyAddDto strategyAddDto){
        this.member = member;
        this.title = strategyAddDto.getStrategyName();

        this.accessType = strategyAddDto.getAccessType();
    }


}
