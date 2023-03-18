package com.ssafy.beconofstock.strategy.entity;



import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private Boolean pricePer; //per
    private Boolean pricePbr; //pbr
    private Boolean pricePsr; //psr
    private Boolean pricePor; //por
    private Boolean pricePrr; //prr

    //
    private Boolean qualityRoe; //roe
    private Boolean qualityRoa; //roa

    //
    private Boolean growth3MothTake; //3개월 매출액 성장률
    private Boolean growth12MothTake; //12개월 매출액 성장률
    private Boolean growth3MothOperatingProfit; //3개월 영업이익 성장률
    private Boolean growth12MothOperatingProfit; //12개월 영업이익 성장률

    private Boolean growth3MothNetProfit; //3개월 순이익 성장률
    private Boolean growth12MothNetProfit; //12개월 순이익 성장률
    @Enumerated(EnumType.STRING)
    private AccessType accessType;


}
