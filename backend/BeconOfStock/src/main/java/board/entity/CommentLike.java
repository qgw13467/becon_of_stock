package board.entity;

<<<<<<< HEAD:backend/BeconOfStock/src/main/java/com/ssafy/beconofstock/board/entity/CommentLike.java
import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.strategy.entity.Strategy;
=======
import config.BaseEntity;
import strategy.entity.Strategy;
>>>>>>> 61821d291481398436e211f51d7f5eec8887a93d:backend/BeconOfStock/src/main/java/board/entity/CommentLike.java

import javax.persistence.*;

@Entity
public class CommentLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}
