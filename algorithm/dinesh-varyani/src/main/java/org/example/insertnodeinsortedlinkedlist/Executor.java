package org.example.insertnodeinsortedlinkedlist;

import lombok.extern.slf4j.Slf4j;
import org.example.Node;

/**
 * How to insert a node in a sorted Singly Linked List?
 *
 * https://www.youtube.com/watch?v=u9igWutVc4o&list=PL6Zs6LgrJj3tDXv8a_elC6eT_4R5gfX4d&index=61
 *
 * @author Yauheni Halaidzin
 * @since 26.05.2024
 */
@Slf4j
public class Executor {

    public static void main(String[] args) {
        Node fourth = new Node(16, null);
        Node third = new Node(10, fourth);
        Node second = new Node(8, third);
        Node first = new Node(1, second);

        Node newHead = insertNode(first, new Node(11, null));

        log.info("New head: {}", newHead);
    }

    private static Node insertNode(Node head, Node newNode) {
        // 1 -> 8 -> 10 -> 16 -> null

        Node current = head;

        while (current.getNext() != null) {
            if (current.getValue() <= newNode.getValue() && newNode.getValue() <= current.getNext().getValue()) {
                Node next = current.getNext();
                newNode.setNext(next);
                current.setNext(newNode);
                break;
            } else {
                current = current.getNext();
            }
        }

        return head;
    }

}
