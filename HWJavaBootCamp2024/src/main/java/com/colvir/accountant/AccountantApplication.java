package com.colvir.accountant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class AccountantApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountantApplication.class, args);
    }
}
/*
Написать веб приложение для управления сущностями.
Пример: бухгалтерское приложение для управления кадрами и расчета заработной платы.
Сущности: сотрудник (имя, фамилия, зарплата, отдел), платежные поручения перевода заработной платы (кому, сумма, дата перечисления)
Критерии оценки:
Можно управлять минимум двумя взаимодействующими сущностями через контроллер - доступен CRUD интерфейс + простая агрегация данных
Сервисный слой покрыт тестами
Стек: Java 17, Spring boot 3.*.*, maven, Junit, Mockito
 */
