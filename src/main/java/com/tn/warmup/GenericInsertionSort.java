package com.tn.warmup;

import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;

public class GenericInsertionSort {

    public static void main(String[] args) {
        List<Integer> integerList = asList(6, 2, 5, 10, 125, 4, 1, 12, -3, 0, 10, 11, 8);
        System.out.println(integerList);
        sort(integerList, Comparator.naturalOrder());
        System.out.println(integerList);
        sort(integerList, Comparator.reverseOrder());
        System.out.println(integerList);
    }

    private static <T> void sort(List<T> list, Comparator<? super T> comparator) {
        for (int i = 1; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                if (comparator.compare(list.get(i), list.get(j)) < 0) {
                    var tmp = list.get(j);
                    list.set(j, list.get(i));
                    list.set(i, tmp);
                }
            }
        }
    }

}
