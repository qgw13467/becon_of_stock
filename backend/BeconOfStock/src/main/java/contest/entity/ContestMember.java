package contest.entity;

<<<<<<< HEAD:backend/BeconOfStock/src/main/java/com/ssafy/beconofstock/contest/entity/ContestMember.java
import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import lombok.*;
=======
import config.BaseEntity;
import member.entity.Member;
import strategy.entity.Strategy;
>>>>>>> 61821d291481398436e211f51d7f5eec8887a93d:backend/BeconOfStock/src/main/java/contest/entity/ContestMember.java

import javax.persistence.*;

@Entity
<<<<<<< HEAD:backend/BeconOfStock/src/main/java/com/ssafy/beconofstock/contest/entity/ContestMember.java
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
=======
>>>>>>> 61821d291481398436e211f51d7f5eec8887a93d:backend/BeconOfStock/src/main/java/contest/entity/ContestMember.java
public class ContestMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contest contest;


    @ManyToOne(fetch = FetchType.LAZY)
    private Strategy strategy;

<<<<<<< HEAD:backend/BeconOfStock/src/main/java/com/ssafy/beconofstock/contest/entity/ContestMember.java
    private Long ranking;
=======
    private LocalDateTime participateDateTime;


>>>>>>> 61821d291481398436e211f51d7f5eec8887a93d:backend/BeconOfStock/src/main/java/contest/entity/ContestMember.java
}
