package com.ssafy.beconofstock.backtest.dto;

import com.ssafy.beconofstock.backtest.entity.Trade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuySellDto {
    private Trade buy;
    private Trade sell;

    public String toString(){
        return "change: "+(sell.getMarcap().doubleValue()/buy.getMarcap().doubleValue())+", start: " + buy.getCorname() + ", " +(long)(buy.getMarcap().doubleValue() / 100D) + ", " + buy.getYear() + " " + buy.getMonth() + ", end : "
                + sell.getCorname() + ", " + (long)(sell.getMarcap().doubleValue() / 100D) + ", " + sell.getYear() + " " + sell.getMonth();
    }
}
