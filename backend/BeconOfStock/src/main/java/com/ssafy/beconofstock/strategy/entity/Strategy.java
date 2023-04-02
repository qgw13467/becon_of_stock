package com.ssafy.beconofstock.strategy.entity;


import com.ssafy.beconofstock.backtest.entity.CummulateReturn;
import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.dto.StrategyAddDto;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@DynamicInsert
public class Strategy extends BaseEntity {

    String title;
    @OneToMany
    List<CummulateReturn> cummulateReturnList;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Double strategyCumulativeReturn;    // H(DESC)
    private Double strategyCagr;        // H
    private Double strategySharpe;      // H
    private Double strategySortino;     // H
    private Double strategyMDD;         // L(ASC)
    //

    private Integer strategyRevenue;    // H
    private Integer totalMonth;


//    @Enumerated(EnumType.STRING)
//    private AccessType accessType

    public Strategy(Member member, StrategyAddDto dto) {
        this.member = member;
        this.title = dto.getTitle();
        this.strategyCumulativeReturn = dto.getStrategyCumulativeReturn();
        this.strategyCagr = dto.getStrategyCagr();
        this.strategySharpe = dto.getStrategySharpe();
        this.strategySortino = dto.getStrategySortino();
        this.strategyMDD = dto.getStrategyMDD();
        this.strategyRevenue = dto.getStrategyRevenue();
        this.totalMonth = dto.getTotalMonth();
    }

    public void setByStrategyAddDto(StrategyAddDto dto) {
        this.title = (dto.getTitle() != null) ? dto.getTitle() : this.title;
        this.strategyCumulativeReturn =
                (dto.getStrategyCumulativeReturn() != null)
                        ? dto.getStrategyCumulativeReturn() :
                        this.strategyCumulativeReturn;

        this.strategyCagr = (dto.getStrategyCagr() != null) ? dto.getStrategyCagr() : this.strategyCagr;
        this.strategySharpe = (dto.getStrategySharpe() != null) ? dto.getStrategySharpe() : this.strategySharpe;
        this.strategySortino = (dto.getStrategySortino() != null) ? dto.getStrategySortino() : this.strategySortino;
        this.strategyMDD = (dto.getStrategyMDD() != null) ? dto.getStrategyMDD() : this.strategyMDD;
        this.strategyRevenue = (dto.getStrategyRevenue() != null) ? dto.getStrategyRevenue() : this.strategyRevenue;
        this.totalMonth = (dto.getTotalMonth() != null) ? dto.getTotalMonth() : this.totalMonth;
    }

    public Strategy(List<CummulateReturn> cummulateReturnList) {
        this.cummulateReturnList = cummulateReturnList;
    }

    public Strategy(Member member, String title, List<CummulateReturn> cummulateReturnList) {
        this.member = member;
        this.title = title;
        this.cummulateReturnList = cummulateReturnList;
    }


}
