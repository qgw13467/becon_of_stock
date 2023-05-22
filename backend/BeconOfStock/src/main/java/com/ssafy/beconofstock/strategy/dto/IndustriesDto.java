package com.ssafy.beconofstock.strategy.dto;

import com.ssafy.beconofstock.backtest.entity.Industry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class IndustriesDto {

    List<Industry> industries;

}
