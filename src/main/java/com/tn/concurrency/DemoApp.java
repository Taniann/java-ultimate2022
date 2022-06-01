package com.tn.concurrency;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class DemoApp {

    public static void main(String[] args) {
        var array = generateArray(10);
        System.out.println(Arrays.toString(array));
        MergeSortTask mergeSortTask = new MergeSortTask(array);
        ForkJoinPool.commonPool()
                    .invoke(mergeSortTask);
        System.out.println(Arrays.toString(array));
    }

    private static int[] generateArray(int size) {
        return IntStream.generate(() -> ThreadLocalRandom.current()
                                                         .nextInt(size))
                        .limit(size)
                        .toArray();
    }
}
