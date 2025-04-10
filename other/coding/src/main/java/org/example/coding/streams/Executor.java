package org.example.coding.streams;

import java.util.Comparator;
import java.util.List;

public class Executor {

    public static void main(String[] args) {
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        integers.stream()
                .filter(i -> {
                    System.out.println("filter: " + i);

                    return i % 2 == 0;
                })
                .sorted(Comparator.reverseOrder())
                .forEach(i -> System.out.println("foreach: " + i));
    }

}
