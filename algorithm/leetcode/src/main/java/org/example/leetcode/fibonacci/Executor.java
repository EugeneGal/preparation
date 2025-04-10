package org.example.leetcode.fibonacci;

public class Executor {

    public static void main(String[] args) {
        int fibonacci = fibonacci(7);

        System.out.println(fibonacci);
    }

    private static int fibonacci(int k) {
        if (k <= 1) {
            return k;
        }

        return fibonacci(k - 2) + fibonacci(k - 1);
    }

}
