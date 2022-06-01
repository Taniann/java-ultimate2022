package com.tn.concurrency;

import java.util.Objects;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class MergeSortTask extends RecursiveAction {
    private final int[] array;

    public MergeSortTask(int[] array) {
        Objects.requireNonNull(array);
        Objects.checkIndex(0, array.length);
        this.array = array;
    }

    @Override
    protected void compute() {
        int length = array.length;
        if (length == 1) {
            return;
        }

        int middleIndex = length / 2;

        int[] leftArray = new int[middleIndex];
        int[] rightArray = new int[length - leftArray.length];
        System.arraycopy(array, 0, leftArray, 0, middleIndex);
        System.arraycopy(array, middleIndex, rightArray, 0, length - leftArray.length);

        MergeSortTask leftTask = new MergeSortTask(leftArray);
        MergeSortTask rightTask = new MergeSortTask(rightArray);
        ForkJoinTask<Void> leftForkJoinTask = leftTask.fork();
        rightTask.compute();
        leftForkJoinTask.join();
        merge(array, leftArray, rightArray);
    }

    private void merge(int[] result, int[] left, int[] right) {
        int i = 0;
        int j = 0;
        int index = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                result[index++] = left[i++];
            } else {
                result[index++] = right[j++];
            }
        }
        while (i < left.length) {
            result[index++] = left[i++];
        }
        while (j < right.length) {
            result[index++] = right[j++];
        }
    }

}
