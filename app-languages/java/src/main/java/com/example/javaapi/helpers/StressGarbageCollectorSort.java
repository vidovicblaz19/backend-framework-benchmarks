package com.example.javaapi.helpers;

import java.util.ArrayList;
import java.util.List;

public class StressGarbageCollectorSort {
    public static List<Integer> stressGarbageCollectorSort(List<Integer> arr) {
        if (arr.size() <= 1) {
            return arr;
        }

        int pivotIndex = arr.size() / 2;
        int pivot = arr.get(pivotIndex);
        List<Integer> less = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();

        for (Integer element : arr) {
            if (element < pivot) {
                less.add(element);
            } else if (element > pivot) {
                greater.add(element);
            } else {
                equal.add(element);
            }
        }

        List<Integer> sortedList = new ArrayList<>();
        sortedList.addAll(stressGarbageCollectorSort(less));
        sortedList.addAll(equal);
        sortedList.addAll(stressGarbageCollectorSort(greater));

        return sortedList;
    }
}