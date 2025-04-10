package org.example.coding.hashcode;

import java.util.HashSet;
import java.util.Set;

public class Executor {

    public static void main(String[] args) {
        MyObject myObject = new MyObject(8);

        Set<MyObject> hashSet = new HashSet<>();
        hashSet.add(myObject);

        myObject.setI(800);

        boolean a = hashSet.contains(myObject);
        boolean b = hashSet.contains(new MyObject(800));
        boolean c = hashSet.contains(new MyObject(8));
    }

    static class MyObject {

        private int i;

        public MyObject(int i) {
            this.i = i;
        }

        public void setI(int i) {
            this.i = i;
        }

        @Override
        public boolean equals(Object o) {
            if (this==o) return true;
            if (o==null || getClass()!=o.getClass()) return false;
            MyObject myObject = (MyObject) o;
            return i==myObject.i;
        }

        @Override
        public int hashCode() {
            return i;
        }

    }

}
