package com.mosin.trkpo_02;

import java.util.ArrayList;
import java.util.List;

public class Person implements Comparable<Person> {

    String name;

    String surname;

    int age;

    static public Person createPerson(String surname, String name, int age) {
        Person person = new Person();

        person.surname = surname;
        person.name = name;
        person.age = age;

        return person;
    }

    @Override
    public String toString() {
        String sb = "{Surname: " + this.surname +
                ", name: " + this.name +
                ", age: " + this.age +
                "}";
        return sb;
    }

    @Override
    public int compareTo(Person person) {
        if (this.surname.toLowerCase().compareTo(person.surname.toLowerCase()) == 0) {
            return this.name.toLowerCase().compareTo(person.name.toLowerCase());
        } else {
            return this.surname.toLowerCase().compareTo(person.surname.toLowerCase());
        }
    }

    static public void printPersonList(List<Person> list) {
        for (Person person : list) {
            System.out.println(person);
        }
    }
}
