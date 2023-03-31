package com.ssafy.beconofstock.strategy.entity;


import com.ssafy.beconofstock.backtest.entity.CummulateReturn;
import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;
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

//    @Enumerated(EnumType.STRING)
//    private AccessType accessType;


    public Strategy(Member member, String title) {
        this.member = member;
        this.title = title;
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
