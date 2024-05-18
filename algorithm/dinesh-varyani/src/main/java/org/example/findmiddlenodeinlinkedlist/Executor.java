package org.example.findmiddlenodeinlinkedlist;

import lombok.extern.slf4j.Slf4j;
import org.example.Node;

/**
 * Find middle node in a Singly Linked List.
 *
 * https://www.youtube.com/watch?v=DYpEpZzNmiA&list=PL6Zs6LgrJj3tDXv8a_elC6eT_4R5gfX4d&index=58
 *
 * @author Yauheni Halaidzin
 * @since 12.05.2024
 */
@Slf4j
public class Executor {

    public static void main(String[] args) {
        Node fifth = new Node(15, null);
        Node fourth = new Node(11, fifth);
        Node third = new Node(1, fourth);
        Node second = new Node(8, third);
        Node first = new Node(10, second);

        Node middleNode = findMiddleNode(first);

        log.info("Middle node: {}", middleNode);
    }

    private static Node findMiddleNode(Node head) {
        // 10 -> 8 -> 1 -> 11 -> 15 -> null

        Node slow = head;
        Node fast = head;

        while (fast != null && fast.getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }

        return slow;
    }

}
