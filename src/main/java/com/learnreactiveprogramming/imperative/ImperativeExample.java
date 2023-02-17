package com.learnreactiveprogramming.imperative;


import java.util.ArrayList;
import java.util.List;

public class ImperativeExample {
    public static void main(String[] args){
        var names =  List.of("aditya","akshat","ajit","sujit");

        var newNames =  namesGreaterThanSize(names, 5);
        System.out.println(newNames);

    }

    private static List<String> namesGreaterThanSize(List<String> names, int size) {
        var newNameList = new ArrayList<String>();
        for (String name : names) {
            if (name.length() > size) {
                newNameList.add(name);
            }
        }
        return newNameList;
    }
}
