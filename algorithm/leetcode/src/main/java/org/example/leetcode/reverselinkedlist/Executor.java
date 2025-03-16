package org.example.leetcode.reverselinkedlist;

import lombok.extern.slf4j.Slf4j;
import org.example.leetcode.mergetwosortedlists.ListNode;

/**
 * https://leetcode.com/problems/reverse-linked-list/description/
 */
@Slf4j
public class Executor {

    public static void main(String[] args) {
        ListNode node4 = new ListNode(4);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);

        ListNode reversedList = reverseList(node1);
        log.info(reversedList.toString());
    }

    private static ListNode reverseList(ListNode head) {
        ListNode prev = null;

        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }

        return prev;
    }

}
