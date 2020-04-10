package com.mosin.trkpo_task_04;

import org.junit.Assert;
import org.junit.Test;

import static com.mosin.trkpo_task_04.Main.averageForArray;
import static com.mosin.trkpo_task_04.Main.convertToOneDimArray;

public class MainTest {

    // Матрица из элементов Integer
    @Test
    public void intArrayTest() {
        Integer[][] array = { {1, 7, 0},
                              {2, 32, 19},
                              {3, -3, 10} };
        Integer[] expectedData = {1, 7, 0, 2, 32, 19, 3, -3, 10};

        Assert.assertArrayEquals(expectedData, convertToOneDimArray(array));
    }

    // Матрица из элементов Double
    @Test
    public void doubleArrayTest() {
        Double[][] array = { {1.3,  0.7},
                              {3.9, -33d} };
        Double[] expectedData = {1.3, 0.7, 3.9, -33d};

        Assert.assertArrayEquals(expectedData, convertToOneDimArray(array));
    }

    // Двумерный массив из одной строки
    @Test
    public void singleRowArrayTest() {
        Integer[][] array = { {1, 7, 0} };
        Integer[] expectedData = {1, 7, 0};

        Assert.assertArrayEquals(expectedData, convertToOneDimArray(array));
    }

    // Двумерный массив из одного столбца
    @Test
    public void singleColumnArrayTest() {
        Integer[][] array = { {1},
                              {7},
                              {0} };
        Integer[] expectedData = {1, 7, 0};

        Assert.assertArrayEquals(expectedData, convertToOneDimArray(array));
    }

    // Среднее для массива из нулей
    @Test
    public void averageForZeroArrayTest() {
        Integer[][] array = {
                {0, 0},
                {0, 0}
        };
        double expectedData = (double) (
                array[0][0] + array[0][1] +
                array[1][0] + array[1][1]) / (array.length * array.length);

        Assert.assertEquals(expectedData, averageForArray(array), 0);
    }

    // Среднее для массива из целых чисел
    @Test
    public void averageForIntArrayTest() {
        Integer[][] array = {
                {5, 72, 0},
                {21, 3, 19},
                {3, -3, 10}
        };

        double expectedData = (double) (
                array[0][0] + array[0][1] + array[0][2] +
                array[1][0] + array[1][1] + array[1][2] +
                array[2][0] + array[2][1] + array[2][2]) / (array.length * array.length);

        Assert.assertEquals(expectedData, averageForArray(array), 0);
    }

    // Среднее массива вещественных чисел
    @Test
    public void averageForDoubleArrayTest() {
        Double[][] array = {
                {1.13, 0.22, 0.0},
                {7.77, 3.03, 4.22},
                {3.1, -3.3, 10.22}
        };
        double expectedData = (
                array[0][0] + array[0][1] + array[0][2] +
                array[1][0] + array[1][1] + array[1][2] +
                array[2][0] + array[2][1] + array[2][2]) / (array.length * array.length);

        Assert.assertEquals(expectedData, averageForArray(array), 0);
    }
}
