package strategy.entity;

import config.BaseEntity;
import lombok.Data;
import member.entity.Member;

import javax.persistence.*;

@Entity
public class Strategy extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String title;
    @Enumerated(EnumType.STRING)
    private AccessType accessType;







}
