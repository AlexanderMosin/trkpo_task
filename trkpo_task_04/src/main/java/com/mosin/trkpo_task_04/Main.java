package com.mosin.trkpo_task_04;
/*  Запишите в двумерный массив NxN случайные числа от 10 до 99. Размерность N получить
 * из аргументов. Напишите обобщенную функцию, возвращающую одномерный массив из
 * переданного двумерного. Напишите обобщенную функцию, находящую среднее значение
 * элементов переданного массива.
 * Протестируйте обе функции.
 */

import static java.lang.Math.random;

public class Main {
    public static void main(String[] args) {

        final int n = 3;

        Integer[][] arr = generateArrayOfInt(n);
        printArray(arr);
        System.out.println("Преобразовали в одномерный: ");
        printArray(convertToOneDimArray(arr));

        System.out.println();


        Double[][] arrayOfDouble = generateArrayOfDouble(n);
        printArray(arrayOfDouble);
        System.out.println("Преобразовали в одномерный: ");
        printArray(convertToOneDimArray(arrayOfDouble));
    }

    private static Integer[][] generateArrayOfInt (int n) {
        Integer[][] myarray = new Integer[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                myarray[i][j] = (int) (random() * 90);
            }
        }
        return myarray;
    }

    private static Double[][] generateArrayOfDouble (int n) {
        Double[][] myarray = new Double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                myarray[i][j] = (random() * 90);
            }
        }
        return myarray;
    }

    private static <T extends Number> void printArray (T[][] array) {
        for (T[] ts : array) {
            for (T t : ts) {
                // можно ли как-то осуществить универсальный вывод?
                if (t instanceof Double) {
                    System.out.printf("%5.2f   ", t);
                } else {
                    System.out.printf("%4d  ", t);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static <T extends Number> void printArray (T[] array) {
        for (T elem : array) {
            if (elem instanceof Double) {
                System.out.printf("%5.2f   ", elem);
            } else {
                System.out.printf("%4d  ", elem);
            }
        }
        System.out.println();
    }

    private static <T extends Number> T[] convertToOneDimArray(T[][] array) {
        T[] newArray = (T[]) new Number[array.length*array[0].length];

        for (int i = 0; i < array.length; i++) {
            System.arraycopy(array[i], 0, newArray, i * array[i].length, array[i].length);
        }

        return newArray;
    }

    private static <T extends Number> double averageForArray(T[][] array) {
        double sum = 0;
        int length = array.length * array.length;
        for (T[] row : array) {
            for (T elem : row) {
                sum += elem.doubleValue();
            }
        }

        return sum/length;
    }

}
