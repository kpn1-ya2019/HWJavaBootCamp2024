package com.colvir.spring;

import java.time.LocalTime;

public class HelloWorld {
    public static void main(String[] args) {
        LocalTime currentTime = LocalTime.now();
        System.out.println("The current local time is: " + currentTime);

        Greeter greeter = new Greeter();
        System.out.println(greeter.sayHello());

        User firstUser = new User();
        firstUser.login = "ivanov";
        firstUser.name = "ivan";

        User secondUser = new User();
        secondUser.login = "ivanov";
        secondUser.name = "ivan";

// Ctrl+ Slash - // comment
/*
* Ctrl+ Shift+ Slash -  comment
*
*/

//        if (firstUser.equals(secondUser)) {
//            System.out.println("пользователи равны");
//        } else {
//            System.out.println("пользователи разные");
//        }
        System.out.println(firstUser);

        /*day2 dogs and cats*/
        Cat cat = new Cat();
        Dog dog = new Dog();

        cat.say();
        dog.say();

    }
}
