package org.example.sort.bubblesort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Executor {

    public static void main(String[] args) {
        int[] array = {6, 5, 4, 3, 2, 1};

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
        log.info("Array sorted in ascending order: {}", array);
    }

}
