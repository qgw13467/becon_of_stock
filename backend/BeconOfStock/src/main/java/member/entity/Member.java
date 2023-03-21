package member.entity;


import board.entity.Board;
import config.BaseEntity;
import contest.entity.Contest;
import lombok.Data;
import strategy.entity.Strategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nickname;

    private Role role;

    @OneToMany(mappedBy = "board",fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "strategy", fetch = FetchType.LAZY)
    private List<Strategy> strategies = new ArrayList<>();

    @OneToMany(mappedBy = "contest",fetch = FetchType.LAZY)
    private List<Contest> contests = new ArrayList<>();

}
