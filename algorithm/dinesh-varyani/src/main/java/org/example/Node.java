package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Node model
 *
 * @author Yauheni Halaidzin
 * @since 12.05.2024
 */
@Data
@AllArgsConstructor
public class Node {

    private int value;

    private Node next;

}
