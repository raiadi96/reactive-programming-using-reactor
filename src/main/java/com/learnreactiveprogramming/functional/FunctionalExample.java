package com.learnreactiveprogramming.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionalExample {
    public static void main(String[] args){
        var names =  List.of("aditya","akshat","ajit","sujit");

        var newNames =  namesGreaterThanSize(names, 5);
        System.out.println(newNames);

    }

    private static List<String> namesGreaterThanSize(List<String> names, int size) {
        return names.stream()
                .filter(name -> name.length() > size)
                .collect(Collectors.toList());
    }
}
