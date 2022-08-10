package com.tn.warmup.linkedlist;

import java.util.Objects;

public class ReversedLinkedList {

    public static void main(String[] args) {
        var head = createLinkedList(4, 3, 9, 1);
        printReversedRecursively(head); // should print "1 <- 9 <- 3 <- 4"
    }

    public static <T> Node<T> createLinkedList(T... elements) {
        Objects.requireNonNull(elements);

        if (elements.length == 0) {
            return null;
        }

        Node<T> head = new Node<>(elements[0]);
        Node<T> current = head;

        for (int i = 1; i < elements.length; i++) {
            current.next = new Node<>(elements[i]);
            current = current.next;
        }

        return head;
    }

    public static <T> void printReversedRecursively(Node<T> head) {
        Objects.requireNonNull(head);

        printCurrent(head.next);
        System.out.println(head.element);
    }

    private static <T> void printCurrent(Node<T> current) {
        if (current != null) {
            printCurrent(current.next);
            System.out.print(current.element + " <- ");
        }
    }

    static class Node<T> {
        T element;
        Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }
}

