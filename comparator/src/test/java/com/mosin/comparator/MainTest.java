package com.mosin.comparator;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class MainTest {

    @Test
    // Обычный позитивный сценарий
    public void positiveSortingByFamilyTest() {
        Person racer = Person.createPerson("Vettel", "Sebastian", 33);
        Person writer = Person.createPerson("Gibson", "William", 57);
        Person guitarPlayer = Person.createPerson("Darrell", "Dimebag", 33);
        Person writer2 = Person.createPerson("Gibson", "John", 29);

        ArrayList<Person> actualData = new ArrayList<>();
        actualData.add(racer);
        actualData.add(guitarPlayer);
        actualData.add(writer);
        actualData.add(writer2);
        Collections.sort(actualData);

        ArrayList<Person> expectedData = new ArrayList<>();
        expectedData.add(guitarPlayer);
        expectedData.add(writer2);
        expectedData.add(writer);
        expectedData.add(racer);

        Assert.assertEquals(expectedData, actualData);
    }

    @Test
    // Регистр на сортировку не влияет
    public void upperCaseAndLowerCaseSortingTest() {
        Person racer = Person.createPerson("vettel", "Sebastian", 33);
        Person racer2 = Person.createPerson("VETTEL", "Abastian", 33);
        Person writer = Person.createPerson("GIBSON", "William", 57);
        Person writer2 = Person.createPerson("gibson", "John", 29);

        ArrayList<Person> actualData = new ArrayList<>();
        actualData.add(racer);
        actualData.add(racer2);
        actualData.add(writer);
        actualData.add(writer2);
        Collections.sort(actualData);

        ArrayList<Person> expectedData = new ArrayList<>();
        expectedData.add(writer2);
        expectedData.add(writer);
        expectedData.add(racer2);
        expectedData.add(racer);

        Assert.assertEquals(expectedData, actualData);
    }

    @Test
    // Регистр на сортировку не влияет
    public void latinAndCyrillicSortingTest() {
        Person racer = Person.createPerson("Яшин", "Лев", 63);
        Person racer2 = Person.createPerson("Abigail", "Abastian", 33);
        Person writer = Person.createPerson("Бобров", "Иван", 57);
        Person writer2 = Person.createPerson("Aъtest", "John", 29);

        ArrayList<Person> actualData = new ArrayList<>();
        actualData.add(racer);
        actualData.add(racer2);
        actualData.add(writer);
        actualData.add(writer2);
        Collections.sort(actualData);

        ArrayList<Person> expectedData = new ArrayList<>();
        expectedData.add(racer2);
        expectedData.add(writer2);
        expectedData.add(writer);
        expectedData.add(racer);

        Assert.assertEquals(expectedData, actualData);
    }

    @Test
    // Имя, фамилия с пробелами
    public void nameWithSpacesSortingTest() {
        Person racer = Person.createPerson("Borisov", "Sebastian", 33);
        Person writer = Person.createPerson("Gibson", "William", 57);
        Person guitarPlayer = Person.createPerson("  Darrell", "Dimebag", 33);
        Person writer2 = Person.createPerson("Bori sov", "John", 29);

        ArrayList<Person> actualData = new ArrayList<>();
        actualData.add(racer);
        actualData.add(guitarPlayer);
        actualData.add(writer);
        actualData.add(writer2);
        Collections.sort(actualData);

        ArrayList<Person> expectedData = new ArrayList<>();
        expectedData.add(guitarPlayer);
        expectedData.add(writer2);
        expectedData.add(racer);
        expectedData.add(writer);

        Assert.assertEquals(expectedData, actualData);
    }

    @Test
    // Без фамилии
    public void withoutSurnameSortingTest() {
        Person racer = Person.createPerson("", "Sebastian", 33);
        Person writer = Person.createPerson("", "Alex", 57);
        Person guitarPlayer = Person.createPerson("  Darrell", "Dimebag", 33);
        Person writer2 = Person.createPerson("Afflek", "Ben", 29);

        ArrayList<Person> actualData = new ArrayList<>();
        actualData.add(racer);
        actualData.add(guitarPlayer);
        actualData.add(writer);
        actualData.add(writer2);
        Collections.sort(actualData);

        ArrayList<Person> expectedData = new ArrayList<>();
        expectedData.add(writer);
        expectedData.add(racer);
        expectedData.add(guitarPlayer);
        expectedData.add(writer2);

        Assert.assertEquals(expectedData, actualData);
    }
}