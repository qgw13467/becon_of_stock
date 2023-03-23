package com.ssafy.beconofstock.strategy.entity;



import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.dto.StrategyAddDto;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class Strategy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String title;

    private Double cumulativeReturn;
    private Double cagr;
    private Double sharpe;



    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    public Strategy(Member member, StrategyAddDto strategyAddDto){
        this.member = member;
        this.title = strategyAddDto.getStrategyName();
        this.accessType = strategyAddDto.getAccess();
        this.cumulativeReturn= strategyAddDto.getCumulativeReturn();
        this.cagr = strategyAddDto.getCagr();
        this.sharpe = strategyAddDto.getSharpe();
    }


}
