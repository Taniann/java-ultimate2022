package com.tn.datastructure;

import java.util.Objects;

/**
 * A simple implementation of the Hash Table that allows storing a generic key-value pair. The table itself is based
 * on the array of {@link Node} objects.
 * <p>
 * An initial array capacity is 16.
 * <p>
 * Every time a number of elements is equal to the array size that tables gets resized
 * (it gets replaced with a new array that it twice bigger than before). E.g. resize operation will replace array
 * of size 16 with a new array of size 32. PLEASE NOTE that all elements should be reinserted to the new table to make
 * sure that they are still accessible  from the outside by the same key.
 *
 * @param <K> key type parameter
 * @param <V> value type parameter
 */
public class HashTable<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 3;
    private int size = 0;

    @SuppressWarnings("unchecked")
    private Node<K, V>[] nodes = new Node[DEFAULT_INITIAL_CAPACITY];

    /**
     * Puts a new element to the table by its key. If there is an existing element by such key then it gets replaced
     * with a new one, and the old value is returned from the method. If there is no such key then it gets added and
     * null value is returned.
     *
     * @param key   element key
     * @param value element value
     * @return old value or null
     */
    public V put(K key, V value) {
        Objects.requireNonNull(key);
        int hash = key.hashCode();
        int index = calculateIndex(hash);
        Node<K, V> newNode = new Node<>(key, value);
        if (nodes[index] == null) {
            nodes[index] = newNode;
        } else {
            Node<K, V> current = nodes[index];
            while (current.next != null) {
                if (current.key.equals(key)) {
                    V existingValue = current.value;
                    current.value = value;
                    return existingValue;
                } else {
                    current = current.next;
                }
            }
            if (current.key.equals(key)) {
                V existingValue = current.value;
                current.value = value;
                return existingValue;
            } else {
                current.next = newNode;
            }
        }
        size++;
        int currentCapacity = nodes.length;
        if (size == currentCapacity) {
            resize(currentCapacity * 2);
        }
        return null;
    }

    /**
     * Prints a content of the underlying table (array) according to the following format:
     * 0: key1:value1 -> key2:value2
     * 1:
     * 2: key3:value3
     * ...
     */
    public void printTable() {
        for (int i = 0; i < nodes.length; i++) {
            System.out.print(i + ": ");
            Node<K, V> current = nodes[i];
            if (current != null) {
                while (current.next != null) {
                    System.out.print(current.key + ":" + current.value + " -> ");
                    current = current.next;
                }
                System.out.println(current.key + ":" + current.value);
            } else {
                System.out.println();
            }
        }
    }

    private int calculateIndex(int hash) {
        return Math.abs(hash) % nodes.length;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newSize) {
        Node<K, V>[] newNodes = new Node[newSize];
        for (Node<K, V> node : nodes) {
            if (node != null) {
                int hash = node.key.hashCode();
                int index = Math.abs(hash) % newSize;
                newNodes[index] = node;
            }
        }
        nodes = newNodes;
    }
}
