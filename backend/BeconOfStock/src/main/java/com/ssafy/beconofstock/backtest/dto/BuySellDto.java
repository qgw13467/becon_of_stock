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

    public String toString() {
        return "getCorclose: " + (sell.getCorclose().doubleValue() / buy.getCorclose().doubleValue()) +
                ", getMarcap: " + (sell.getMarcap().doubleValue() / buy.getMarcap().doubleValue()) +
                ", start: " + buy.getCorname() + ", " + buy.getCorclose() + ", " + buy.getYear() + " " + buy.getMonth() + ", end : "
                + sell.getCorname() + ", " + sell.getCorclose() + ", " + sell.getYear() + " " + sell.getMonth();
    }
}
