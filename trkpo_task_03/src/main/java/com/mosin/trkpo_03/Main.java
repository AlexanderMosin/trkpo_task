/*
Написать метод, на вход - 2 итератора по числам. известно, что коллекции
под итераторами упорядочены и бесконечны. Необходимо вывести все элементы
первой коллекции, которых нет во второй. Напишите junit-тесты.
 */
package com.mosin.trkpo_03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(1, 2, 4, 10);
        System.out.println(list1 + "\n" + list2);

        Iterator iter1 = list1.iterator();
        Iterator iter2 = list2.iterator();

        System.out.println("\nЭлементы первой коллекции, которых нету во второй: " + compareIterators(iter1, iter2));
    }

    static List<Integer> compareIterators(Iterator iter1, Iterator iter2) {
        if (!iter1.hasNext() || !iter2.hasNext()) {
            System.out.println("Некорректные входные данные");
        }
        Integer iter1Element = (Integer) iter1.next();
        Integer iter2Element = (Integer) iter2.next();

        List<Integer> uniqueElements = new ArrayList<>();
        List<Integer> repeatedElements = new ArrayList<>();

        do {
            if (iter1Element < iter2Element) {
                if (!repeatedElements.contains(iter1Element))
                    uniqueElements.add(iter1Element);
                if (iter1.hasNext()) {
                    iter1Element = (Integer) iter1.next();
                } else {
                    return uniqueElements.stream().distinct().collect(Collectors.toList());
                }
            } else if (iter1Element.equals(iter2Element)) {
                repeatedElements.add(iter1Element);
                if (iter1.hasNext()) {
                    iter1Element = (Integer) iter1.next();
                }
                if (iter2.hasNext()) {
                    iter2Element = (Integer) iter2.next();
                } else {
                    uniqueElements.add(iter1Element);
                }
            } else {
                if (iter2.hasNext()) {
                    iter2Element = (Integer) iter2.next();
                } else {
                    if (!repeatedElements.contains(iter1Element)) {
                        uniqueElements.add(iter1Element);
                        iter1Element = (Integer) iter1.next();
                    }
                }
            }
        } while (iter1.hasNext() || iter2.hasNext());

        if (!iter1Element.equals(iter2Element) &&
                !repeatedElements.contains(iter1Element) &&
                !uniqueElements.contains(iter1Element)) {
            uniqueElements.add(iter1Element);
        }

        return uniqueElements.stream().distinct().collect(Collectors.toList());
    }
}
