package org.example.leetcode.mergetwosortedlists;

import lombok.extern.slf4j.Slf4j;

/**
 * https://leetcode.com/problems/merge-two-sorted-lists/
 */
@Slf4j
public class Executor {

    public static void main(String[] args) {
        ListNode node13 = new ListNode(4);
        ListNode node12 = new ListNode(2, node13);
        ListNode node11 = new ListNode(1, node12);

        ListNode node23 = new ListNode(4);
        ListNode node22 = new ListNode(3, node23);
        ListNode node21 = new ListNode(1, node22);

        ListNode result = mergeTwoLists1(node11, node21);

        log.info(result.toString());
    }

    // Recursive implementation for merging two already sorted linked lists into a single sorted linked list.
    // The time complexity is (O(n + m)), where (n) is the number of nodes in list1 and (m) is the number of nodes in list2.
    public static ListNode mergeTwoLists1(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        ListNode result = new ListNode();
        if (list1.val > list2.val) {
            result.val = list2.val;
            result.next = mergeTwoLists1(list1, list2.next);
        } else {
            result.val = list1.val;
            result.next = mergeTwoLists1(list1.next, list2);
        }

        return result;
    }

    // Bubble sort of linked list, time complexity is O(n^2)
    public static ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        if (list1 == null && list2 == null) {
            return null;
        }
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        ListNode lastElementOfFirstList = list1;

        while (lastElementOfFirstList.next != null) {
            lastElementOfFirstList = lastElementOfFirstList.next;
        }

        lastElementOfFirstList.next = list2;

        return bubbleSortOfLinkedList(list1);
    }

    private static ListNode bubbleSortOfLinkedList(ListNode head) {
        ListNode current = head;

        while (current != null) {
            ListNode index = current.next;

            while (index != null) {
                if (current.val > index.val) {
                    int temp = index.val;
                    index.val = current.val;
                    current.val = temp;
                }
                index = index.next;
            }
            current = current.next;
        }

        return head;
    }

}
