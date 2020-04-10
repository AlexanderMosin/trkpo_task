package com.mosin.comparator;

import java.util.Comparator;

public class PersonSortedByAge implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        return Integer.compare(person1.age, person2.age);
    }
}
