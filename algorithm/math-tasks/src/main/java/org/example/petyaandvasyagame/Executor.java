package org.example.petyaandvasyagame;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Petya and Vasya game.
 *
 * https://habr.com/ru/companies/yandex/articles/340784/ - «Игра».
 *
 * @author Yauheni Halaidzin
 * @since 12.05.2024
 */
@Slf4j
public class Executor {

    private static final Set<Integer> PETYA_NUMBERS = new HashSet<>();

    private static final Set<Integer> VASYA_NUMBERS = new HashSet<>();

    public static void main(String[] args) {
        Queue<Integer> queue = Arrays.stream(args[0].split(","))
            .map(Integer::parseInt)
            .collect(Collectors.toCollection(LinkedList::new));

        while (queue.peek() != null) {
            int petyaNumber = addNumber(PETYA_NUMBERS, queue);
            int vasyaNumber = addNumber(VASYA_NUMBERS, queue);

            if (petyaNumber > vasyaNumber) {
                addNumber(VASYA_NUMBERS, queue);
            } else {
                addNumber(PETYA_NUMBERS, queue);
            }
        }

        int petyaSum = PETYA_NUMBERS.stream()
            .mapToInt(i -> i)
            .sum();

        int vasyaSum = VASYA_NUMBERS.stream()
            .mapToInt(i -> i)
            .sum();

        if (petyaSum > vasyaSum) {
            log.info("Petya won: petyaNumbersSum={}, vasyaNumbersSum={}", petyaSum, vasyaSum);
        } else if (petyaSum < vasyaSum) {
            log.info("Vasya won: petyaNumbersSum={}, vasyaNumbersSum={}", petyaSum, vasyaSum);
        } else {
            log.info("Friendship won: petyaNumbersSum={}, vasyaNumbersSum={}", petyaSum, vasyaSum);
        }
    }

    private static int addNumber(Set<Integer> numbers, Queue<Integer> queue) {
        Integer number = queue.poll();
        if (number == null) {
            return 0;
        }

        numbers.add(number);

        return number;
    }

}