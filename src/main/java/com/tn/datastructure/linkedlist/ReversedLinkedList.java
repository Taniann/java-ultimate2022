package com.tn.datastructure.linkedlist;

import java.util.Stack;

public class ReversedLinkedList {
    public static void main(String[] args) {
        Node<Integer> head = createLinkedList(4, 3, 9, 1);
        printReversedRecursively(head);
        printReversedUsingStack(head);
    }

    /**
     * Creates a list of linked {@link Node} objects based on the given array of elements and returns a head of the list.
     *
     * @param elements an array of elements that should be added to the list
     * @param <T>      elements type
     * @return head of the list
     */
    public static <T> Node<T> createLinkedList(T... elements) {
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

    /**
     * Prints a list in a reserved order using a recursion technique. Please note that it should not change the list,
     * just print its elements.
     * <p>
     * Imagine you have a list of elements 4,3,9,1 and the current head is 4. Then the outcome should be the following:
     * 1 -> 9 -> 3 -> 4
     *
     * @param head the first node of the list
     * @param <T>  elements type
     */
    public static <T> void printReversedRecursively(Node<T> head) {
        if (head == null) {
            return;
        }
        printRecursively(head.next);
        System.out.println(head.element);
    }

    private static <T> void printRecursively(Node<T> node) {
        if (node != null) {
            printRecursively(node.next);
            System.out.print(node.element + " -> ");
        }
    }

    /**
     * Prints a list in a reserved order using a {@link java.util.Stack} instance. Please note that it should not change
     * the list, just print its elements.
     * <p>
     * Imagine you have a list of elements 4,3,9,1 and the current head is 4. Then the outcome should be the following:
     * 1 -> 9 -> 3 -> 4
     *
     * @param head the first node of the list
     * @param <T>  elements type
     */
    public static <T> void printReversedUsingStack(Node<T> head) {
        Node<T> current = head;
        Stack<T> stack = new Stack<>();
        while (current != null) {
            stack.push(current.element);
            current = current.next;
        }

        while (!stack.isEmpty()) {
            T element = stack.pop();
            if (stack.isEmpty()) {
                System.out.println(element);
            } else {
                System.out.print(element + " -> ");
            }
        }
    }

}
