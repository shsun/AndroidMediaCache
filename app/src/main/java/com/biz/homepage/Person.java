package com.biz.homepage;

/**
 * Created by shsun on 17/1/18.
 */

public class Person {


    private String mName;
    private int mAge;

    public Person(String name, int age) {
        this.mName = name;
        this.mAge = age;
    }


    public String getName() {
        return this.mName;
    }

    public int getAge() {
        return this.mAge;
    }

}
