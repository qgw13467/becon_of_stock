package com.ssafy.beconofstock.strategy.dto;

import com.ssafy.beconofstock.strategy.entity.Indicator;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IndicatorsDto {
    Map<String, List<Indicator>> indicators = new HashMap<>();
    List<Map<String,String>> fators = new ArrayList<>();

}
