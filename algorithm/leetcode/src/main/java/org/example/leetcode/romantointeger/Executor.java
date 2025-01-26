package org.example.leetcode.romantointeger;

import java.util.Map;

public class Executor {

    public static final Map<Character, Integer> RULES = Map.of(
            'I', 1,
            'V', 5,
            'X', 10,
            'L', 50,
            'C', 100,
            'D', 500,
            'M', 1000);

    public static void main(String[] args) {
        String roman = "DCXXI";

        int sum = romanToInt(roman);

        System.out.println(sum);
    }

    private static int romanToInt(String roman) {
        char[] romanBytes = roman.toCharArray();

        int sum = 0;
        boolean isSkipIteration = false;

        for (int i = 0; i < romanBytes.length; i++) {
            if (isSkipIteration) {
                isSkipIteration = false;
                continue;
            }

            switch (romanBytes[i]) {
                case 'I':
                    if (!hasNext(romanBytes, i + 1)) {
                        sum = sum + RULES.get('I');
                        continue;
                    }
                    if (romanBytes[i + 1] == 'V') {
                        sum = sum + 4;
                        isSkipIteration = true;
                        continue;
                    }
                    if (romanBytes[i + 1] == 'X') {
                        sum = sum + 9;
                        isSkipIteration = true;
                        continue;
                    }
                    sum = sum + RULES.get('I');
                    break;
                case 'X':
                    if (!hasNext(romanBytes, i + 1)) {
                        sum = sum + RULES.get('X');
                        continue;
                    }
                    if (romanBytes[i + 1] == 'L') {
                        sum = sum + 40;
                        isSkipIteration = true;
                        continue;
                    }
                    if (romanBytes[i + 1] == 'C') {
                        sum = sum + 90;
                        isSkipIteration = true;
                        continue;
                    }
                    sum = sum + RULES.get('X');
                    break;
                case 'C':
                    if (!hasNext(romanBytes, i + 1)) {
                        sum = sum + RULES.get('C');
                        continue;
                    }
                    if (romanBytes[i + 1] == 'D') {
                        sum = sum + 400;
                        isSkipIteration = true;
                        continue;
                    }
                    if (romanBytes[i + 1] == 'M') {
                        sum = sum + 900;
                        isSkipIteration = true;
                        continue;
                    }
                    sum = sum + RULES.get('C');
                    break;
                default:
                    sum = sum + RULES.get(romanBytes[i]);
            }
        }
        return sum;
    }

    private static boolean hasNext(char[] romanBytes, int nextIndex) {
        try {
            return romanBytes[nextIndex] != 0;
        } catch (IndexOutOfBoundsException exception) {
            return false;
        }
    }

}
