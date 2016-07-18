/*
 * Copyright (c) 2013, Kevin Barroga
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Kevin Barroga ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <copyright holder> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class BattleOfSorts {

    public static void main(String[] args) {
        
        // name of file to run sorting tests
        String fileName = args[0];     
        
        // will store the words read from the file
        List<String> nameList = new ArrayList<String>();
        
        BufferedReader br = null;
        try {
            // attempt to open the fileName file
            br = new BufferedReader(new FileReader(fileName));
            
            String list; 
            
            // loop and read a line from the file as long as we dont get null
            while((list = br.readLine()) != null )
                
            // add the read word to the wordList
            nameList.add(list);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // attempt the close the file
                br.close();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
              
        // initialize a new string array equal to the size of the nameList
        String[] names1 = new String[nameList.size()];
        String[] names2 = new String[nameList.size()];
        String[] names3 = new String[nameList.size()];
        String[] names4 = new String[nameList.size()];
        
        // call the nameList's toArray method to and transfer items from
        // nameList to our string array names
        nameList.toArray(names1);
        nameList.toArray(names2);
        nameList.toArray(names3);
        nameList.toArray(names4);
        
        // decomment for loop to display unsorted list
        // for loop to display list of unsorted  strings from the names array
        /*System.out.println("Before sorting: " + names1.length + " keys:\n");
        for( int i = 0; i < names1.length; i++ )
            System.out.println( names1[i] );
        */
        
        // 
        long startTime = 0;
        long estimatedTime = 0;
        // array to store the estimated run times of each sort
        // time in nanoseconds
        long[] result = new long[4];
        
        // insertion sort test on names1 array
        startTime = System.nanoTime();
        insertionSort(names1);
        estimatedTime = System.nanoTime() - startTime;
        result[0] = estimatedTime;
        
        // merge sort test on names2 array
        startTime = System.nanoTime();
        mergeSort(names2);
        estimatedTime = System.nanoTime() - startTime;
        result[1] = estimatedTime;
        
        // heap sort test on names3 array
        startTime = System.nanoTime();
        heapSort(names3);
        estimatedTime = System.nanoTime() - startTime;
        result[2] = estimatedTime;
        
        //quick sort test on names4 array
        startTime = System.nanoTime();
        quickSort(names4);
        estimatedTime = System.nanoTime() - startTime;
        result[3] = estimatedTime; 
        
        // decomment for loop to display sorted list 
        // for loop to display list of sorted strings from the names array
        /*System.out.println("After sorting: " + names1.length + " keys:\n");
        for( int i = 0; i < names1.length; i++ )
            System.out.println( names1[i] );
        */      
        
        System.out.println("***************************** Sorting Test Results *********************************");
        System.out.println("************************************************************************************");
        System.out.println("File name: " + fileName);
        System.out.println("Sort Test: " + nameList.size() + " keys");
        System.out.println(" Insertion Sort: " + result[0] + " ns; First Key: " + names1[0] + "; Last Key: " + names1[names1.length - 1]);
        System.out.println(" Merge Sort:     " + result[1] + " ns; First Key: " + names2[0] + "; Last Key: " + names2[names2.length - 1]);
        System.out.println(" Heap Sort:      " + result[2] + " ns; First Key: " + names3[0] + "; Last Key: " + names3[names3.length - 1]);
        System.out.println(" Quick Sort:     " + result[3] + " ns; First Key: " + names4[0] + "; Last Key: " + names4[names4.length - 1]);
        System.out.println("************************************************************************************");
        
    }
                
        
    
    
    ///////////////////////////////////////////////// INSERTION SORT ///////////////////////////////////////////
    /* 
     * insertion sort algorithm that takes an array of Comparable items
     */
    public static void insertionSort(Comparable [] a ) {
        for(int i = 1; i < a.length; i++) {
            Comparable temp = a[i];
            int j = i;
            for(; j > 0 && temp.compareTo(a[j - 1]) < 0; j--)
                a[j] = a[j - 1];
            a[j] = temp;
        }
    }
    
    ///////////////////////////////////////////////// MERGE SORT ///////////////////////////////////////////
    /*
     * merge sort algorithm that takes an array of Comparable items
     */
    public static void mergeSort(Comparable [] a ) {
        Comparable [] tempArray = new Comparable [a.length];
        mergeSort (a, tempArray, 0, a.length - 1);
    }
    
    /*
     * internal recursive method that takes four parameters;
     * an array of Comparable items
     * a temporary array tempArray to place the merge results
     * an integer left, the left-most index of subarray
     * an integer right, the right-most index of subarray
     */
    private static void mergeSort(Comparable [] a, Comparable [] tempArray, 
            int left, int right) {
        if(left < right) {
            int center = (left + right) / 2;
            mergeSort(a, tempArray, left, center);
            mergeSort(a, tempArray, center + 1, right);
            merge(a, tempArray, left, center + 1, right);
        }
    }
    
    /*
     * internal method that merges the two sorted halves of  a subarray that takes five parameters;
     * an array of Comparable items
     * a temporary array tempArray to place the merge results
     * integer leftPos the left-most index of the subarray
     * integer rightPos the index of the beginning of the second half
     * integer rightEnd the right-most index of subarray
     */
    private static void merge(Comparable [] a, Comparable [] tempArray,
            int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tempPos = leftPos;
        int numElements = rightEnd - leftPos + 1;
        
        //Main loop
        while(leftPos <= leftEnd && rightPos <= rightEnd)
            if (a[leftPos].compareTo(a[rightPos]) <= 0)
                tempArray[tempPos++] = a[leftPos++];
            else
                tempArray[tempPos++] = a[rightPos++];
        
        while(leftPos <= leftEnd)   //copy rest of first half
            tempArray[tempPos++] = a[leftPos++];
        while(rightPos <= rightEnd) //copy rest of right half
            tempArray[tempPos++] = a[rightPos++];
        
        //copy tempArray
        for(int i = 0; i < numElements; i++, rightEnd--)
            a[rightEnd] = tempArray[rightEnd];
    }
    
    ///////////////////////////////////////////////// HEAP SORT ///////////////////////////////////////////
    /*
     * Heap sort algorithm that takes an array of Comparable items
     */
    public static void heapSort(Comparable [] a) {
        for(int i = a.length / 2; i >= 0; i--)  //build heap
            percDown(a, i, a.length);
        for(int i = a.length - 1; i > 0; i--) {
            swap(a, 0, i);
            percDown(a, 0, i);
        }
        
    }
    
    /*
     * internal method for heap sort that takes one parameter;
     * integer i the index of an item in the heap
     * returns the index of the left child
     */
    private static int leftChild(int i) {
        return 2 * i + 1;
    }
    
    /*
     * internal method for heap sort that is used to deleteMax and buildHeap
     * takes three parameters;
     * an array of Comparable items
     * integer i, index from which to drop down to
     * integer n, size of binar heap
     */
    private static void percDown(Comparable [] a, int i, int n) {
        int child;
        Comparable temp;
        
        for(temp = a[i]; leftChild(i) < n; i = child) {       
            child = leftChild( i );
            if( child != n - 1 && a[ child ].compareTo( a[ child + 1 ] ) < 0 )
                child++;
            if( temp.compareTo( a[ child ] ) < 0 )
                a[ i ] = a[ child ];
            else
                break;
        }
        a[ i ] = temp;
    
    } 
    
    /*
     * method used to swap elements in an array. three parameters;
     * an array of objects
     * integer index1 the index of the first object
     * integer index2 the index of the second object
     */
    public static final void swap(Object [] a, int index1, int index2) {
        Object tmp = a [index1];
        a[index1] = a[index2];
        a[index2] = tmp;
    }
    
    ///////////////////////////////////////////////// QUICK SORT ///////////////////////////////////////////
    /*
     * quick sort algorithm that takes an array of Comparable items
     */
    public static void quickSort(Comparable [] a) {
        quickSort(a, 0, a.length - 1);
    }
    
    private static final int CUTOFF = 10;
    
    /*
     * internal quick sort recursive algorithm takes three parameters;
     * an array of Comparable items
     * integer low the left-most index of the subarray
     * integer high the right-most index of the subarray
     */
    private static void quickSort(Comparable [] a, int low, int high) {
        if(low + CUTOFF > high)
            insertionSort(a, low, high);
        else {
            // sort low, middle, high
            int middle = (low + high) / 2;
            if(a[middle].compareTo(a[low]) < 0)
                swap(a, low, middle);
            if(a[high].compareTo(a[low]) < 0)
                swap(a, low, high);
            if(a[high].compareTo(a[middle]) < 0)
                swap(a, middle, high);
            
            // place pivot at position high-1
            swap(a, middle, high - 1);
            Comparable pivot = a[high - 1];
            
            // begin partitioning
            int i, j;
            for(i = low, j = high - 1; ;) {
                while(a[++i].compareTo(pivot) < 0)
                    ;
                while(pivot.compareTo(a[--j]) < 0)
                    ;
                if(i >= j)
                    break;
                swap(a, i, j);
            }
            
            // restore pivot
            swap(a, i, high - 1);
            
            quickSort(a, low, i - 1);   //sort small elements
            quickSort(a, i + 1, high);  //sort large elements
            
        }
    }
    
    /*
     * internal insertion sort to sort subarrays that is used by quick sort
     * takes three parameters;
     * an array of Comparable items
     * integer low the left-most index of the subarray
     * integer high the right-most index of subarray
     */
    private static void insertionSort(Comparable [] a, int low, int high) {
        for(int i = low + 1; i <= high; i++) {
            Comparable temp = a[i];
            int j;
            for(j = i; j > low && temp.compareTo(a[j - 1]) < 0; j--)
                a[j] = a[j - 1];
            a[j] = temp;
        }
    }
}
