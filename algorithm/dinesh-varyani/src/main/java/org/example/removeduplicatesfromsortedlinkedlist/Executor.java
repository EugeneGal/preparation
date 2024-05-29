package org.example.removeduplicatesfromsortedlinkedlist;

import lombok.extern.slf4j.Slf4j;
import org.example.Node;

/**
 * How to remove duplicate from sorted Singy Linked List?
 *
 * https://www.youtube.com/watch?v=0Hn18rUkZKY&list=PL6Zs6LgrJj3tDXv8a_elC6eT_4R5gfX4d&index=60
 *
 * @author Yauheni Halaidzin
 * @since 26.05.2024
 */
@Slf4j
public class Executor {

    public static void main(String[] args) {
        Node fifth = new Node(3, null);
        Node fourth = new Node(3, fifth);
        Node third = new Node(2, fourth);
        Node second = new Node(1, third);
        Node first = new Node(1, second);

        Node newHead = removeDuplicateNodesAuthorVersion(first);
        // Node newHead = removeDuplicateNodesMyVersion(first);

        log.info("New head: {}", newHead);
    }

    private static Node removeDuplicateNodesAuthorVersion(Node head) {
        // 1 -> 1 -> 2 -> 3 -> 3 -> null

        Node current = head;

        while (current != null && current.getNext() != null) {
            if (current.getValue() == current.getNext().getValue()) {
                current.setNext(current.getNext().getNext());
            } else {
                current = current.getNext();
            }
        }
        return head;
    }

    private static Node removeDuplicateNodesMyVersion(Node head) {
        // 1 -> 1 -> 2 -> 3 -> 3 -> null

        Node current = head;

        while (current != null) {
            while (current.getNext() != null && current.getValue() == current.getNext().getValue()) {
                Node next = current.getNext();
                current.setNext(next.getNext());
            }
            current = current.getNext();
        }
        return head;
    }

}
