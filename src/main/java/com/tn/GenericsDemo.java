package com.tn;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

public class GenericsDemo {
    public static void main(String[] args) {
        Stream.of("Andrii", "Maria", "Yevhen", "Taras")
              .max(thenComparing(comparing(String::length), s -> s.charAt(0)))
              .ifPresent(System.out::println);
    }

    static <T, R extends Comparable<? super R>> Comparator<T> comparing(Function<? super T, ? extends R> function) {
        return (e1, e2) -> function.apply(e1)
                                   .compareTo(function.apply(e2));
    }

    static <T, R extends Comparable<? super R>> Comparator<T> thenComparing(Comparator<? super T> initialComparator,
                                                                            Function<? super T, ? extends R> extractionFunction) {
        return (e1, e2) -> {
            var initialComparatorResult = initialComparator.compare(e1, e2);
            if (initialComparatorResult == 0) {
                return extractionFunction.apply(e1)
                                         .compareTo(extractionFunction.apply(e2));
            } else {
                return initialComparatorResult;
            }
        };
    }
}
