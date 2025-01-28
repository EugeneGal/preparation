package org.example.leetcode.validparentheses;

import java.util.LinkedList;
import java.util.List;

/**
 * Class description
 *
 * @author Yauheni Halaidzin
 * @since 28.01.2025
 */
public class Executor {

    private static final List<Character> OPENING_BRACKETS = List.of('(', '[', '{');

    private static final List<Character> CLOSING_BRACKETS = List.of(')', ']', '}');

    public static void main(String[] args) {
        String str = "abc";

        boolean isValid = isValid(str);
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
                Character polledElement = stack.poll();

            }


        }


        return false;
    }

}
