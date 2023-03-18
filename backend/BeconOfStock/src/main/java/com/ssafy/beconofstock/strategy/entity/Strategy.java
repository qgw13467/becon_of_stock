package com.ssafy.beconofstock.strategy.entity;



import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Strategy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String title;

    @ColumnDefault("0")
    private Boolean pricePer; //per
    @ColumnDefault("0")
    private Boolean pricePbr; //pbr
    @ColumnDefault("0")
    private Boolean pricePsr; //psr
    @ColumnDefault("0")
    private Boolean pricePor; //por
    @ColumnDefault("0")
    private Boolean pricePrr; //prr

    //
    @ColumnDefault("0")
    private Boolean qualityRoe; //roe
    @ColumnDefault("0")
    private Boolean qualityRoa; //roa

    //
    @ColumnDefault("0")
    private Boolean growth3MothTake; //3개월 매출액 성장률
    @ColumnDefault("0")
    private Boolean growth12MothTake; //12개월 매출액 성장률
    @ColumnDefault("0")
    private Boolean growth3MothOperatingProfit; //3개월 영업이익 성장률
    @ColumnDefault("0")
    private Boolean growth12MothOperatingProfit; //12개월 영업이익 성장률

    @ColumnDefault("0")
    private Boolean growth3MothNetProfit; //3개월 순이익 성장률
    @ColumnDefault("0")
    private Boolean growth12MothNetProfit; //12개월 순이익 성장률
    @Enumerated(EnumType.STRING)
    private AccessType accessType;


}
