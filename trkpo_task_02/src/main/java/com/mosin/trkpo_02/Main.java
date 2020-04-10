/*
Создать класс Person с полями: name, surname, age. Имплементировать интерфейс Comparable,
используя поля surname, name. Добавить несколько объектов Person в List. Вывести:
1)неотсортированный список,
2)список, отсортированный по name,
3)список, отсортированный по surname,
4)список, отсортированный по age
 */

package com.mosin.trkpo_02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Person racer = Person.createPerson("Vettel", "Sebastian", 33);
        Person writer = Person.createPerson("Gibson", "William", 57);
        Person guitarPlayer = Person.createPerson("Darrell", "Dimebag", 33);
        Person writer2 = Person.createPerson("Gibson", "John", 29);

        ArrayList<Person> listPerson = new ArrayList<Person>();
        listPerson.add(racer);
        listPerson.add(guitarPlayer);
        listPerson.add(writer);
        listPerson.add(writer2);

        System.out.println("Несортированный список:");
        Person.printPersonList(listPerson);

        System.out.println("\nОтсортированный по фамилии + имени список:");
        Collections.sort(listPerson);
        Person.printPersonList(listPerson);

        System.out.println("\nОтсортированный по имени список:");
        listPerson.sort(new PersonSortedByName());
        Person.printPersonList(listPerson);

        System.out.println("\nОтсортированный по возрасту список:");
        listPerson.sort(new PersonSortedByAge());
        Person.printPersonList(listPerson);

        System.out.println("\nFinish");
    }
}
