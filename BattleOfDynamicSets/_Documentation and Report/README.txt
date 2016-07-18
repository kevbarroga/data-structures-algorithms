										README.txt
										
										
- Overview

This program includes four implementations of dynamics sets.  The implementations include a 
DOUBLY-LINK LIST (DLL), SKIP LIST (SL), BINARY SEARCH TREE (BST), and a RED_BLACK TREE (RBT).  
The main driver class is TestDynamicSets, which reads in a txt file, and treats each newline as a key.  The TestDynamicSets driver class allows the user to time set operations on all four implementations.

- Compile

To compile and run the main driver class, source file TestDynamicSets.java file must be in the same directory as all other packages: BSTDynamicSet, DLLDynamicSet, DynamicSet, RedBlackDynamicSet, and SkipListDynamicSet.  The test files, sorted and unsorted will also be in the same directory.

Move to BattleOfDynamicSets folder and execute:

	javac TestDynamicSet.java
	
- Run

While in the BattleOfDynamicSets folder execute:

	java TestDynamicSet INPUTFILE
	
where INPUTFILE is the name of the file you want to use for tests.  Each line of this file will be used as a key in each DynamicSet implementation.  

ex.

	java TestDynamicSet sorted-1000.txt
	
	
	