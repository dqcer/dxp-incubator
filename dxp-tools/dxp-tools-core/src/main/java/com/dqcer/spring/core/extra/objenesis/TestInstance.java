package com.dqcer.spring.core.extra.objenesis;

import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

public class TestInstance {

    public String name;

    private int x;
    private Integer y;

    public TestInstance(String name, int x, Integer y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void print() {
        System.out.println("name = " + name);
        System.out.println("x = " + x);
        System.out.println("y = " + y);
    }

    public static void main(String[] args) {
        Objenesis objenesis = new ObjenesisStd(true);
        TestInstance test = objenesis.newInstance(TestInstance.class);
        test.print();
        System.out.println("===========");
        TestInstance testInstance = new TestInstance("new", 1, null);
        testInstance.print();

    }
}
