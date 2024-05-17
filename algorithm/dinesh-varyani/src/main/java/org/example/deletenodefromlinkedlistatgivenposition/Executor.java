package org.example.deletenodefromlinkedlistatgivenposition;

import lombok.extern.slf4j.Slf4j;
import org.example.Node;

/**
 * Delete a node from a Singly Linked List at a given position.
 * My implementation differs from that on youtube.
 *
 * https://www.youtube.com/watch?v=3wWkyemipYs&list=PL6Zs6LgrJj3tDXv8a_elC6eT_4R5gfX4d&index=55
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

        Node newHead = removeNode(first, 3);

        log.info("New head: {}", newHead);
    }

    private static Node removeNode(Node head, int position) {
        // 10 -> 1 -> 8 -> 11 -> null

        if (position == 1) {
            return head.getNext();
        }

        int i = 1;
        Node current = head;
        Node previous = head;

        while (current != null) {
            if (i == position) {
                previous.setNext(current.getNext());
                break;
            }

            previous = current;
            current = current.getNext();
            i++;
        }

        return head;
    }

}
