package org.example.leetcode.validparentheses;

import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

/**
 * https://leetcode.com/problems/valid-parentheses/description/
 */
@Slf4j
public class Executor {

    private static final Map<Character, Character> MAPPING = Map.of(
            ')', '(',
            ']', '[',
            '}', '{'
    );

    public static void main(String[] args) {
        String str = "()";

        boolean isValid = isValid(str);

        log.info("Is valid? {}", isValid);
    }

    public static boolean isValid(String str) {
        if (str.length() % 2 != 0) {
            return false;
        }

        Deque<Character> stack = new LinkedList<>();

        for (int i = 0; i < str.length(); i++) {
            char element = str.charAt(i);

            if (MAPPING.containsValue(element)) {
                stack.push(element);
            } else {
                Character polledElement = stack.poll();
                if (MAPPING.get(element) != polledElement) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

}
