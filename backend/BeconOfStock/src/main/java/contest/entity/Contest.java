package contest.entity;

<<<<<<< HEAD:backend/BeconOfStock/src/main/java/com/ssafy/beconofstock/contest/entity/Contest.java

import com.ssafy.beconofstock.config.BaseEntity;
import lombok.*;
=======
import config.BaseEntity;
>>>>>>> 61821d291481398436e211f51d7f5eec8887a93d:backend/BeconOfStock/src/main/java/contest/entity/Contest.java

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
<<<<<<< HEAD:backend/BeconOfStock/src/main/java/com/ssafy/beconofstock/contest/entity/Contest.java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
=======

>>>>>>> 61821d291481398436e211f51d7f5eec8887a93d:backend/BeconOfStock/src/main/java/contest/entity/Contest.java
public class Contest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    /**
     * 랭킹을 매길 때 어떤걸 볼지
     */
    private String description;
    private String content;

    /**
     * 0이면 진행 중 1이면 완료된 대회
     */
    private Long type;

    private LocalDateTime start_date_time;

    private LocalDateTime end_date_time;

}
