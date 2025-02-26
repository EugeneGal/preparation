package org.example.leetcode.longestcommonprefix;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/longest-common-prefix
 */
@Slf4j
public class Executor {

    public static void main(String[] args) {
        String[] array = {"flower", "flow", "flight"};
//        String[] array = {"abc","ab","a"};

        String longestCommonPrefix = longestCommonPrefixBest(array);

        log.info("longestCommonPrefix = {}", longestCommonPrefix);
    }

    private static String longestCommonPrefixBest(String[] array) {
        String first = array[0];

        for (int i = 0; i < first.length(); i++) {
            for (int j = 1; j < array.length; j++) {
                if (i > array[j].length() - 1 || array[j].charAt(i) != first.charAt(i)) {
                    return first.substring(0, i);
                }
            }
        }

        return first;
    }

    private static String longestCommonPrefix(String[] array) {
        Arrays.sort(array);

        String first = array[0];

        for (int i = 0; i < first.length(); i++) {
            for (String str : array) {
                if (str.charAt(i) != first.charAt(i)) {
                    return first.substring(0, i);
                }
            }
        }

        return first;
    }

}
