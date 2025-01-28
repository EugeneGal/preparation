package org.example.leetcode.romantointeger;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * https://leetcode.com/problems/roman-to-integer/description
 */
@Slf4j
public class Executor {

    private static final Map<Character, Integer> RULES = Map.of(
        'I', 1,
        'V', 5,
        'X', 10,
        'L', 50,
        'C', 100,
        'D', 500,
        'M', 1000);

    public static void main(String[] args) {
        String roman = "MCMXCIV";

        int sum = romanToInt(roman);

        log.info("Roman = {}, int = {}", roman, sum);
    }

    private static int romanToInt(String roman) {
        int sum = 0;
        int prevNumber = 0;

        for (int i = 0; i < roman.length(); i++) {
            sum = sum + RULES.get(roman.charAt(i));

            if (RULES.get(roman.charAt(i)) > prevNumber) {
                sum = sum - prevNumber * 2;
            }
            prevNumber = RULES.get(roman.charAt(i));
        }

        return sum;
    }

}
