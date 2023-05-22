package board.entity;

<<<<<<< HEAD:backend/BeconOfStock/src/main/java/com/ssafy/beconofstock/board/entity/BoardDibs.java
import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
=======
import config.BaseEntity;
>>>>>>> 61821d291481398436e211f51d7f5eec8887a93d:backend/BeconOfStock/src/main/java/board/entity/BoardDibs.java
import lombok.Data;
import member.entity.Member;

import javax.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDibs extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;


}
