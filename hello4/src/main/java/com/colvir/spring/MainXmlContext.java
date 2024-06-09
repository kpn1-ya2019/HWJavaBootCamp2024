package com.colvir.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainXmlContext {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationXmlContext.xml");
        Animal animal = context.getBean("animalBean", Animal.class);

        animal.say();
    }
}
