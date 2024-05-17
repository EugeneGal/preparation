package org.example.reverselinkedlist;

import lombok.extern.slf4j.Slf4j;
import org.example.Node;

/**
 * Reverse a Singly Linked List.
 *
 * https://www.youtube.com/watch?v=jY-EUKXYT20&list=PL6Zs6LgrJj3tDXv8a_elC6eT_4R5gfX4d&index=57
 *
 * @author Yauheni Halaidzin
 * @since 12.05.2024
 */
@Slf4j
public class Executor {

    public static void main(String[] args) {
        Node fourth = new Node(11, null);
        Node third = new Node(8, fourth);
        Node second = new Node(1, third);
        Node first = new Node(10, second);

        Node newHead = reverse(first);

        log.info("New head: {}", newHead);
    }

    private static Node reverse(Node head) {
        // 10 -> 1 -> 8 -> 11 -> null
        // 11 -> 8 -> 1 -> 10 -> null

        Node current = head;
        Node previous = null;
        Node next = null;

        while (current != null) {
            next = current.getNext();
            current.setNext(previous);
            previous = current;
            current = next;
        }

        return previous;
    }

}
