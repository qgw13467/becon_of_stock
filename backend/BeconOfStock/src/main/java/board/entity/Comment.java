package board.entity;

<<<<<<< HEAD:backend/BeconOfStock/src/main/java/com/ssafy/beconofstock/board/entity/Comment.java
import com.ssafy.beconofstock.config.BaseEntity;
import com.ssafy.beconofstock.member.entity.Member;
=======
import config.BaseEntity;
import member.entity.Member;
>>>>>>> 61821d291481398436e211f51d7f5eec8887a93d:backend/BeconOfStock/src/main/java/board/entity/Comment.java

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Long boardId;
    private String content;
    private Long likeNum;
    private Long commentNum;
    private int depth;
    private Boolean modified;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CommentRel> children;

    public void increaseCommentNum() { this.commentNum += 1; }

    public void decreaseCommentNum(int num) {
        this.commentNum -= num;
    }


}
