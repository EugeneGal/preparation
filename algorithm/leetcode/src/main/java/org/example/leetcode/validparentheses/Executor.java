package org.example.leetcode.validparentheses;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class description
 *
 * @author Yauheni Halaidzin
 * @since 28.01.2025
 */
@Slf4j
public class Executor {

    private static final List<Character> OPENING_BRACKETS = List.of('(', '[', '{');

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

    private static boolean isValid(String str) {
        if (str.length() % 2 != 0) {
            return false;
        }

        LinkedList<Character> stack = new LinkedList<>();

        for (int i = 0; i < str.length(); i++) {
            char element = str.charAt(i);

            if (OPENING_BRACKETS.contains(element)) {
                stack.push(element);
            } else {
                Character polledElement = MAPPING.get(stack.poll());
                if (element != polledElement) {
                    return false;
                }
            }
        }

        return true;
    }

}
