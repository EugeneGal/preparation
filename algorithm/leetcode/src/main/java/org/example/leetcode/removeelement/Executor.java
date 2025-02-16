package org.example.leetcode.removeelement;

/**
 * https://leetcode.com/problems/remove-element/
 */
public class Executor {

    public static void main(String[] args) {
//        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        int[] nums = {1, 4, 2, 3, 2};
        int k = removeElement(nums, 2);

        System.out.println(k);
    }

    // https://medium.com/@AlexanderObregon/solving-the-remove-element-problem-on-leetcode-a-java-walkthrough-af09f7794818
    public static int removeElement(int[] nums, int val) {
        int i = 0;

        // {1, 4, 2, 3, 2}
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }

        return i;
    }

    public static int removeElementMySolution(int[] nums, int val) {
        int k = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                for (int j = nums.length - 1 - k; j >= i; j--) {
                    if (nums[j] == val) {
                        k++;
                    } else {
                        int temp = nums[j];
                        nums[j] = nums[i];
                        nums[i] = temp;
                        break;
                    }
                }
            }
        }

        return nums.length - k;
    }

}
