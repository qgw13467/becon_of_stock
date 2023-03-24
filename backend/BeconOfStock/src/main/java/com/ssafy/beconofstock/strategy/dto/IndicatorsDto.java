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
    List<Map<String,Object>> factors = new ArrayList<>();

}
