package org.example.leetcode.twosum;

import lombok.extern.slf4j.Slf4j;

/**
 * https://leetcode.com/problems/two-sum/description/
 */
@Slf4j
public class Executor {

    public static void main(String[] args) {
        int[] nums = {3, 3};
        int[] ints = twoSum(nums, 6);
        log.info("{}", ints);
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
