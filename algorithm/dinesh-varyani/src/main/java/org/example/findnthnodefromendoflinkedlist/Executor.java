package org.example.findnthnodefromendoflinkedlist;

import lombok.extern.slf4j.Slf4j;
import org.example.Node;

/**
 * Find nth node from the end of a Singly Linked List.
 *
 * https://www.youtube.com/watch?v=adS-s2Stg_A&list=PL6Zs6LgrJj3tDXv8a_elC6eT_4R5gfX4d&index=60
 *
 * @author Yauheni Halaidzin
 * @since 19.05.2024
 */
@Slf4j
public class Executor {

    public static void main(String[] args) {
        Node fifth = new Node(15, null);
        Node fourth = new Node(11, fifth);
        Node third = new Node(1, fourth);
        Node second = new Node(8, third);
        Node first = new Node(10, second);

        Node middleNode = findNthNodeFromEnd(first, 2);

        log.info("Middle node: {}", middleNode);
    }

    private static Node findNthNodeFromEnd(Node head, int n) {
        // 10 -> 8 -> 1 -> 11 -> 15 -> null

        Node rightPointer = head;
        Node mainPointer = head;
        int count = 0;

        while (count < n) {
            rightPointer = rightPointer.getNext();
            count++;
        }

        while (rightPointer != null) {
            rightPointer = rightPointer.getNext();
            mainPointer = mainPointer.getNext();
        }

        return mainPointer;
    }

}
