package com.mosin.iterator;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static com.mosin.iterator.Main.compareIterators;

public class MainTest {

    @Test
    // Первый массив содержит больше элементов чем второй. Последний элемент неуникальный
    public void firstArrayIsLargerTest() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 5, 7, 9, 11, 12);
        List<Integer> list2 = Arrays.asList(3, 4, 7, 12, 20);

        List<Integer> expectedData = Arrays.asList(1, 2, 5, 9, 11);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // Первый массив содержит больше элементов. Последний элемент уникальный > последнего из второго массива
    public void firstArrayIsLargerLastElemIsUniqueTest() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 5, 7, 9, 11, 12, 13);
        List<Integer> list2 = Arrays.asList(3, 4, 7, 10, 11, 12);

        List<Integer> expectedData = Arrays.asList(1, 2, 5, 9, 13);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // Первый массив короче. Уникальный элемент находится последним < последнего из второго массива
    public void firstArrayIsShorterLastElemIsUniqueTest() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(1, 2, 4, 10);

        List<Integer> expectedData = Arrays.asList(3);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // Первый массив содержит меньше элементов. . Уникальный элемент находится последним > последнего из второго массива
    public void firstArrayIsShorterTest() {
        List<Integer> list1 = Arrays.asList(-3, 0, 1, 5, 90);
        List<Integer> list2 = Arrays.asList(-100, -2, 5, 12, 22, 38, 56, 57);

        List<Integer> expectedData = Arrays.asList(-3, 0, 1, 90);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // В первом массиве уникальный элемент находится первым
    public void firstElementIsUniqueTest() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(2, 3, 4, 10);

        List<Integer> expectedData = Arrays.asList(1);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // Оба массива состоят из одинаковых элементов
    public void theSameArraysTest() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(1, 2, 3);

        List<Integer> expectedData = Collections.emptyList();
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // В первом массиве нет уникальных элементов
    public void noUniqueElementsTest() {
        List<Integer> list1 = Arrays.asList(6, 7, 8);
        List<Integer> list2 = Arrays.asList(6, 7, 8, 11);

        List<Integer> expectedData = Collections.emptyList();
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // Оба массива состоят из одного и того же элемента
    public void arraysOfTheSameElementsTest() {
        List<Integer> list1 = Arrays.asList(13, 13, 13);
        List<Integer> list2 = Arrays.asList(13, 13, 13);

        List<Integer> expectedData = Collections.emptyList();
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // Неуникальный элемент повторяется в первом массиве
    public void nonUniqueElementIsRepeatedTest() {
        List<Integer> list1 = Arrays.asList(9, 9, 11, 14);
        List<Integer> list2 = Arrays.asList(9, 11, 15);

        List<Integer> expectedData = Arrays.asList(14);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // Второй массив пуст
    public void emptyArrayTest() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list3 = Collections.emptyList();

        try {
            List<Integer> resultData = compareIterators(list1.iterator(), list3.iterator());
            Assert.fail("Expected NoSuchElementException");
        } catch (NoSuchElementException thrown) {
            Assert.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    // Второй массив пуст
    public void emptyBothArraysTest() {
        List<Integer> list1 = Collections.emptyList();
        List<Integer> list2 = Collections.emptyList();

        try {
            List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());
            Assert.fail("Expected NoSuchElementException");
        } catch (NoSuchElementException thrown) {
            Assert.assertNotEquals("", thrown.getMessage());
        }
    }

    @Test
    // В первом массиве уникальный элементы повторяются несколько раз
    public void uniqueElementRepeatsTest() {
        List<Integer> list1 = Arrays.asList(1, 1, 1, 4);
        List<Integer> list2 = Arrays.asList(3, 4, 5, 10);

        List<Integer> expectedData = Arrays.asList(1);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // Все элементы массива - уникальные
    public void allElementsIsUniqueTest() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);

        List<Integer> expectedData = Arrays.asList(1, 2, 3);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // Все элементы массива - уникальные
    public void allElementsIsBiggerTest() {
        List<Integer> list1 = Arrays.asList(13, 23, 45);
        List<Integer> list2 = Arrays.asList(4, 5, 6);

        List<Integer> expectedData = Arrays.asList(13, 23, 45);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // В массиве есть отрицательные числа
    public void negativeNumbersTest() {
        List<Integer> list1 = Arrays.asList(-4, -3, -1);
        List<Integer> list2 = Arrays.asList(-4, -2, 0, 1 ,2 ,3 ,4);

        List<Integer> expectedData = Arrays.asList(-3, -1);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // В первом массиве двузначные числа, во втором массиве однозначные, равные
    // младшему разряду двузначного
    public void lowestDigitTest() {
        List<Integer> list1 = Arrays.asList(11, 22, 33);
        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> expectedData = Arrays.asList(11, 22, 33);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // В массивах многозначные числа
    public void multiDigitNumbersTest() {
        List<Integer> list1 = Arrays.asList(129, 325, 401, 555);
        List<Integer> list2 = Arrays.asList(128, 129, 320, 400, 555, 1289);

        List<Integer> expectedData = Arrays.asList(325, 401);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }


    @Test
    // Ноль - один из уникальных элементов
    public void zeroIsUniqueTest() {
        List<Integer> list1 = Arrays.asList(0, 1, 2, 3);
        List<Integer> list2 = Arrays.asList(1, 2, 4);

        List<Integer> expectedData = Arrays.asList(0, 3);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    @Test
    // Ноль - не один из уникальных элементов
    public void zeroIsntUniqueTest() {
        List<Integer> list1 = Arrays.asList(0, 1, 2, 39);
        List<Integer> list2 = Arrays.asList(0, 2, 4);

        List<Integer> expectedData = Arrays.asList(1, 39);
        List<Integer> resultData = compareIterators(list1.iterator(), list2.iterator());

        Assert.assertEquals(expectedData, resultData);
    }

    // во втором массиве очень много элементов, в первом массиве больше элементов поправить тест negativeNumbersTest
    // последний элемент уникальный: он больше последнего из второго массива или меньше него
    // V 1. (1 1 1 4) (4 5 6)
    // V 2. (1 2 3 4) (1 2 3 5)
    // V 3. (1 2 3) (4 5 6) - (123)
    // V 4. (1 1 1) (1 1 1) -> ()
    // V 5. (1 1 2 3) (1 3 4 5 6) - > (2) (во втором массиве число 1 встречается 1 раз)
    // V 6. () ()
    // V 7. (-3 -2 -4 -7) (-3 -7 4) - (-2, -4)
    // V 8. (11 22 33) (1 2 3) -> (11 22 33)
    // V 9. (123 345 567) (123 567) - (345)
    // V 10. (0 1 2) (1 2 4) -> (0)
    // V 11. (0 1 2) (0 1 7 9) -> (2)
    // V 12. (5 6 7 8) (11 6 7 8), (8 7 6 5) (7 8 6 11)
    // V 13. (11 12 19) (0 1 2)
}