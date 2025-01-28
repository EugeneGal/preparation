package org.example.leetcode.palindromenumber;

import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

/**
 * https://leetcode.com/problems/palindrome-number
 *
 * @author Yauheni Halaidzin
 * @since 24.01.2025
 */
@Slf4j
public class Executor {

    public static void main(String[] args) {
        int value = 1230321;

        byte[] bytes = String.valueOf(value).getBytes(StandardCharsets.UTF_8);

        boolean isNumberPolindrome = isNumberPolindrome(bytes);
        log.info("Is number '{}' polindrome: {}", value, isNumberPolindrome);
    }

    private static boolean isNumberPolindrome(byte[] bytes) {
        for (int i = 0, j = bytes.length - 1; i < bytes.length; i++, j--) {
            if (bytes[i] != bytes[j]) {
                return false;
            }
        }
        return true;
    }

}
