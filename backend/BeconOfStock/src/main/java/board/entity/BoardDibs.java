package board.entity;

import config.BaseEntity;
import lombok.Data;
import member.entity.Member;

import javax.persistence.*;

@Entity
@Data
public class BoardDibs extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;


}
