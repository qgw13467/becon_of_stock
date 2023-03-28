package com.ssafy.beconofstock.contest.entity;


import com.ssafy.beconofstock.config.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String content;
    /**
     * 0이면 진행 중 1이면 완료된 대회
     */
    private Long type;

}
