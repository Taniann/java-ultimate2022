package com.tn;

import com.tn.datastructure.HashTable;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        HashTable<String, String> hashTable = new HashTable<>();
       /* hashTable.put("null", null);
        hashTable.put("null", "null1");
        hashTable.put("3", "A3");
        hashTable.put("4", "A4");
        hashTable.put("5", "A5");
        hashTable.put("6", "A6");
        hashTable.put("7", "A7");
        hashTable.put("8", "A8");
        hashTable.put("9", "A9");
        hashTable.put("10", "A10");
        hashTable.put("11", "A11");
        hashTable.put("12", "A12");
        hashTable.put("13", "A13");
        hashTable.put("14", "A14");
        hashTable.put("15", "A15");
        hashTable.printTable();*/
        hashTable.put("1", "A1");
        hashTable.put("2", "A2");
        hashTable.put("3", "A3");
        hashTable.put("3", "A");
        hashTable.printTable();
    }
}
