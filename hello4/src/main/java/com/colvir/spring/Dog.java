package com.colvir.spring;

public class Dog implements Animal {

    @Override
    public void say() {
        System.out.println("Гав");
    }
}
