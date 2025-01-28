package org.example.leetcode.twosum;

/**
 * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * You can return the answer in any order.
 *
 * https://leetcode.com/problems/two-sum/description/
 *
 * @author Yauheni Halaidzin
 * @since 23.12.2024
 */
public class Executor {

    public static void main(String[] args) {
        int[] nums = {3, 3};
        int[] ints = twoSum(nums, 6);
        System.out.println();
    }

    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i == j) {
                    continue;
                }
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }

}
