										README.txt
										
										
- Overview

This program includes four implementations of comparison sorts.  The implementations include insertion sort, merge sort,
heap sort, and quick sort algorithms.  
The main driver class is BattleOfSorts.java, which reads in a txt file as an argument.

- Compile

To compile and run the main driver class, source file BattleOfSorts.java file must be in the same directory as the test files, sorted and unsorted.

Move to BattleOfSorts folder and execute:

	javac BattleOfSorts.java
	
- Run

While in the BattleOfSorts folder execute:

	java BattleOfSorts INPUTFILE
	
where INPUTFILE is the name of the file you want to use for tests.

ex.

	java BattleOfSorts sorted-1000.txt
	
	
EXAMPLE OUTPUT:

***************************** Sorting Test Results *********************************
************************************************************************************
File name: sorted-100.txt
Sort Test: 100 keys
 Insertion Sort: 39384 ns; First Key: Abraham Keller ; Last Key: Winston Davis 
 Merge Sort:     188848 ns; First Key: Abraham Keller ; Last Key: Winston Davis 
 Heap Sort:      473160 ns; First Key: Abraham Keller ; Last Key: Winston Davis 
 Quick Sort:     160504 ns; First Key: Abraham Keller ; Last Key: Winston Davis 
************************************************************************************
	
	