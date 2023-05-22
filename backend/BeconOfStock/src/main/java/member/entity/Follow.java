package member.entity;

<<<<<<< HEAD:backend/BeconOfStock/src/main/java/com/ssafy/beconofstock/member/entity/Follow.java

import com.ssafy.beconofstock.config.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
=======
import config.BaseEntity;
>>>>>>> 61821d291481398436e211f51d7f5eec8887a93d:backend/BeconOfStock/src/main/java/member/entity/Follow.java

import javax.persistence.*;

@Entity
<<<<<<< HEAD:backend/BeconOfStock/src/main/java/com/ssafy/beconofstock/member/entity/Follow.java
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
=======
>>>>>>> 61821d291481398436e211f51d7f5eec8887a93d:backend/BeconOfStock/src/main/java/member/entity/Follow.java
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member following;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member followed;

    public void setFollowing(Member following) {
        this.following = following;
    }

    public void setFollowed(Member followed) {
        this.followed = followed;
    }
}

