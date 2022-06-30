package com.ecommerce.util;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CoinChange {
    public CoinChange() {
        changeCoins = new ArrayList<>();
        counter = 0;
    }

    private static int counter = 0;
    private static List<Integer> changeCoins = new ArrayList<>();
    private static final int[] availableCoins = {100, 50, 20, 10, 5};

    public List<Integer> depositChange(double change) {
        log.info("calculate change of deposit coin");
        int ln = availableCoins.length - 1;
        while (counter <= ln) {
            while (change >= availableCoins[counter]) {
                change = change - availableCoins[counter];
                changeCoins.add(availableCoins[counter]);
            }
            counter++;
        }
        return changeCoins;
    }
}
