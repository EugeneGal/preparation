package org.example.coding.generics;

import org.example.coding.generics.model.*;

import java.util.ArrayList;
import java.util.List;

public class Executor {

    public static void main(String[] args) {
        // read - '? extends A' - upper bound
        read(new ArrayList<A>());
        read(new ArrayList<B>());
        read(new ArrayList<C>());

        // write - '? super A' - lower bound
        write(new ArrayList<A>());
//        write(new ArrayList<B>()); - not correct
//        write(new ArrayList<C>());  - not correct
    }

    private static void read(List<? extends A> list) {
        for (A element : list) {
            System.out.println(element);
        }
    }

    private static void write(List<? super A> list) {
        list.add(new ImplA());
        list.add(new ImplB());
        list.add(new ImplC());
    }

}
