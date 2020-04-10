package com.mosin.trkpo_02;

import java.util.Comparator;

public class PersonSortedBySurname implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        return person1.surname.compareTo(person2.surname);
    }
}
