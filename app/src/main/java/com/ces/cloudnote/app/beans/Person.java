package com.ces.cloudnote.app.beans;

/**
 * Created by AutoNavi on 2014/8/22.
 */
public class Person {

    public int _id;
    public String name;
    public int age;
    public String info;

    public Person() {
    }

    public Person(String name, int age, String info) {
        this.name = name;
        this.age = age;
        this.info = info;
    }
}
