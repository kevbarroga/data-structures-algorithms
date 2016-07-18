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
 *     * Neither the name of Davis Corp. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Arthur M. Davis ''AS IS'' AND ANY
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



import DynamicSet.*;
import DLLDynamicSet.*;
import SkipListDynamicSet.*;
import BSTDynamicSet.*;
import RedBlackDynamicSet.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;


public class TestDynamicSet
{
	
	public static void main (String []args)
	{
		System.out.println("/**********Dynamic Set Tester**********/\n\n");
		if (args.length == 1)
		{
		System.out.println("This program will test the Dynamic Set operations: insert, search, predecessor, successor,");
		System.out.println("minimum, and maximum, using four different implementations: Doubly Linked List, Skip List,");
		System.out.println("Binary Search Tree, and Red-Black Tree. At the end of the test, a table with the min, avg,");
		System.out.println("and max run times in nanoseconds of all the dynamic set operations will be printed. See");
		System.out.println("README.txt and the Operations Manual for more details and how to operate this program.\n\n");
	
		String filename;
		Scanner fileScan, scan;
		
		filename = args[0];
		try {
		fileScan = new Scanner(new File(filename));
		
		//create the arraylist to read in all the data
		ArrayList<String> list = new ArrayList<String>();
		
		//create the 4 dynamic sets
		DLLDynamicSet<String> set1 = new DLLDynamicSet<String>();
		SkipListDynamicSet<String> set2 = new SkipListDynamicSet<String>();
		BSTDynamicSet<String> set3 = new BSTDynamicSet<String>();
		RedBlackDynamicSet<String> set4 = new RedBlackDynamicSet<String>();
		
		//read in data into the list
		while (fileScan.hasNextLine()) //while there are more names to read in
			list.add(fileScan.nextLine()); //add in the next name to the list
		//trim all white spaces
		for (int i = 0; i < list.size(); i++)
			list.get(i).trim();
			
		//create arrays holding long values to store recorded times
		long[] test1 = new long[list.size()];
		long[] test2 = new long[list.size()];
		long[] test3 = new long[list.size()];
		long[] test4 = new long[list.size()];
		
		//every three cells will hold min, avg, and max times for each of the 4 dynamic sets
		long[] insert = new long[12];
		long[] search = new long[12];
		long[] predecessor = new long[12];
		long[] successor = new long[12];
		long[] minimum = new long[4];
		long[] maximum = new long[4];
		
		/* prompt user using command line. details on commands in operations manual */
		System.out.println("----------Command Line----------\n");
		
		String choice = "random";
		scan = new Scanner(System.in);
		
		while (choice != "quit")
		{
			Command[] commands = Command.values();
		    
		    System.out.println("OPTIONS:");
		    System.out.println("Execute one of these methods on all sets: "); 
		    for (int i = 0; i < commands.length - 2; i++) {
		      System.out.println("* " + commands[i].name().toLowerCase());
		    }
		    System.out.println("Time all methods (except delete): ");
		    System.out.println("* runtest");
		    System.out.println("To exit/quit program:");
		    System.out.println("* quit");        
		    System.out.println();
		    System.out.println("What do you want to do? ");
		    System.out.print("Type the name of an option: ");
		
			//System.out.print("> ");
			choice = scan.nextLine().trim();
		
		switch (choice)
		{
			case "runtest":
		
		/* the following are tests on each method that implements the methods in the DynamicSet<T> class    */
		/* stats will be called after each call to a dynamic set operator to generate the min, avg, and max */
		/* runtimes for each method dynamic set operator 																	 */
		
		//call insertion on every data set, and record the min, avg, and max times for each insertion
		insertion(set1, set2, set3, set4, list, test1, test2, test3, test4);
		stats(test1, test2, test3, test4, insert, test1.length);
		
		//generate random keys to search for using search. same keys will be used on successor and predecessor
		System.out.println();
		System.out.println("Selecting 10 lines of file to use as test cases: ");
		int[] keys = new int[10];
		for (int i = 0; i < 10; i++)
		{
			keys[i] = (int)(Math.random()*test1.length); 
			System.out.println("* " + list.get(keys[i]));
		}
		System.out.println();

		//call search on every data set, and record the min, avg, and max times for each search
		search(set1, set2, set3, set4, list, test1, test2, test3, test4, keys);
		stats(test1, test2, test3, test4, search, 10);
		
		//call predecessor on every data set, and record the min, avg, and max times for each predecessor
		predecessor(set1, set2, set3, set4, list, test1, test2, test3, test4, keys);
		stats(test1, test2, test3, test4, predecessor, 10);
		
		//call successor on every data set, and record the min, avg, and max times for each successor
		successor(set1, set2, set3, set4, list, test1, test2, test3, test4, keys);
		stats(test1, test2, test3, test4, successor, 10);
		
		//call minimum on every data set, and record the time it takes, since we cannot have multiple 
		//tests for finding the minimum element
		minimum(set1, set2, set3, set4, list, test1, test2, test3, test4, minimum);
				
		//call maximum on every data set, and record the time it takes, since we cannot have multiple
		//tests for finding the maximum element
		maximum(set1, set2, set3, set4, list, test1, test2, test3, test4, maximum);
		
		printTable(insert, search, predecessor, successor, minimum, maximum, list.size(), filename);
		
		break;
		
		case "insert":
			System.out.println("---Insertion---");
			Scanner scan2 = new Scanner(System.in);
			ArrayList<String> insertlist = new ArrayList<String>();
			System.out.println("Please enter a key to be inserted into all of the dynamic sets.");
			System.out.print("> ");
			String in = scan2.nextLine();
			
			if (set1.search(in) == null)
			{
				insertlist.add(in);
				System.out.println("Inserting " + in + "...");
				insertion(set1, set2, set3, set4, insertlist, test1, test2, test3, test4);
				System.out.println(in + " added!\n");
			}
			else
				System.out.println("Error Code: 0100. The key " + in + " is already in the set.\n");
			break;
			
		case "search":
			System.out.println("---Search---");
			Scanner scan3 = new Scanner(System.in);
			System.out.println("Please enter a key to be searched for in all the dynamic sets.");
			System.out.print("> ");
			String se = scan3.nextLine().trim() + " ";
			
			if (set1.search(se) != null)
				System.out.println(se + " is in all the dynamic sets.\n");
			else
				System.out.println("Error Code: 0010. The key " + se + " is not in the set.\n");
			break;
			
		case "delete":
			System.out.println("---Deletion---");
			Scanner scan6 = new Scanner(System.in);
			ArrayList<String> deletelist = new ArrayList<String>();
			System.out.println("Please enter a key to be deleted from all of the dynamic sets.");
			System.out.print("> ");
			String del = scan6.nextLine();
			
			if (set1.search(del) != null)
			{
				deletelist.add(del);
				System.out.println("Deleting " + del + "...");
				delete(set1, set2, set3, set4, deletelist, test1, test2, test3, test4);
				System.out.println(del + " deleted!\n");
			}
			else
				System.out.println("Error Code: 0010. The key " + del + " is not in the set.\n");

			break;
			
		case "predecessor":
			System.out.println("---Predecessor---");
			Scanner scan4 = new Scanner(System.in);
			System.out.println("Please enter a key to find its predecessor.");
			System.out.print("> ");
			String pr = scan4.nextLine();
			
			if (set1.search(pr) != null)
			{
				SetElement<String> pred = set1.predecessor(set1.search(pr));
				if (pred != null && pred.getKey() != null)
					System.out.println("Predecessor of " + pr + ": " + pred.getKey() + "\n");
				else
					System.out.println(pr + " does not have a predecessor.\n");
			}
			else
				System.out.println("Error Code: 0010. The key " + pr + " is not in the set.\n");

				
			break;
			
		case "successor":
			System.out.println("---Successor---");
			Scanner scan5 = new Scanner(System.in);
			System.out.println("Please enter a key to find its successor.");
			System.out.print("> ");
			String su = scan5.nextLine();
			
			if (set1.search(su) != null)
			{
				SetElement<String> suc = set1.successor(set1.search(su));
				if (suc != null && suc.getKey() != null)
					System.out.println("Successor of " + su + ": " + suc.getKey() + "\n");
				else
					System.out.println(su + " does not have a successor.\n");
			}
			else
				System.out.println("Error Code: 0010. The key " + su + " is not in the set.\n");
			break;
			
		case "minimum":
			System.out.println("---Minimum---");
			
			if (set1.minimum() != null)
				System.out.println("Minimum element in the sets: " + set1.minimum().getKey() + "\n");
			else
				System.out.println("Error Code: 0000. The dynamic sets are empty.\n");
			break;
			
		case "maximum":
			System.out.println("---Maximum---");
			
			if (set1.maximum() != null)
				System.out.println("Maximum element in the sets: " + set1.maximum().getKey() + "\n");
			else
				System.out.println("Error Code: 0000. The dynamic sets are empty.\n");
			break;
		
		case "quit":
			System.out.println("Thank you for using the Dyanmic Set Tester.");
			choice = "quit";
			break;
		
		default:
			System.out.println("Error Code: 1100. " + choice + " is not a recognizable command. Refer to the Operations");
			System.out.println("Manual for a list of commands.\n");
			break;
		}//end switch
		
		}//end while loop
		
			} catch (FileNotFoundException fofe) { //dont forget to declare a try-catch block when using file.io*
		  	System.out.println("Error Code: 1111. This program has not been invoked correctly. Please run this program again");
			System.out.println("with a proper command line argument specifying the correct path to a file that contains a list");
			System.out.println("of strings with no other symbols. For more information, please refer to the Operations Manual.");
			}
		
		}//end if
		else
		{
		  	System.out.println("Error Code: 1111. This program has not been invoked correctly. Please run this program again");
			System.out.println("with a proper command line argument specifying the correct path to a file that contains a list");
			System.out.println("of strings with no other symbols. For more information, please refer to the Operations Manual.");
		}
	}
	
	public static enum Command {
	    INSERT, SEARCH, PREDECESSOR, SUCCESSOR, MINIMUM, MAXIMUM, DELETE, RUNTEST, QUIT;  
	  }
	
	/* DECOMMENT PRINT STATEMENTS TO SEE PROGRESS */
	public static void insertion(DynamicSet<String> set1, DynamicSet<String> set2, DynamicSet<String> set3, DynamicSet<String> set4, ArrayList<String> list,
											long[] test1, long[] test2, long[] test3, long[] test4)
	
	{
		long startTime = 0;
		long time = 0;
		
		//DLL set
		for (int i = 0; i < list.size(); i++)
		{
			/* DECOMMENT BELOW TO TRACK PROGRAM */
			//System.out.println("DLL Iteration: " + i);
			DLLNode<String> s1 = new DLLNode<String>(list.get(i), list.get(i));
			startTime = System.nanoTime();
			set1.insert(s1);
			time = System.nanoTime() - startTime;
			test1[i] = time;
		}
		
		//SL set
		for (int i = 0; i < list.size(); i++)
		{
			/* DECOMMENT BELOW TO TRACK PROGRAM */
			//System.out.println("SL Iteration: " + i);
			SLNode<String> s1 = new SLNode<String>(list.get(i), list.get(i));
			startTime = System.nanoTime();
			set2.insert(s1);
			time = System.nanoTime() - startTime;
			test2[i] = time;
		}
		
		//BST set
		for (int i = 0; i < list.size(); i++)
		{
			/* DECOMMENT BELOW TO TRACK PROGRAM */
			//System.out.println("BST Iteration: " + i);
			BSTNode<String> s1 = new BSTNode<String>(list.get(i), list.get(i));
			startTime = System.nanoTime();
			set3.insert(s1);
			time = System.nanoTime() - startTime;
			test3[i] = time;
		}
		
		//RBT set
		for (int i = 0; i < list.size(); i++)
		{
			/* DECOMMENT BELOW TO TRACK PROGRAM */
			//System.out.println("RBT Iteration: " + i);
			RBTNode<String> s1 = new RBTNode<String>(list.get(i), list.get(i));
			startTime = System.nanoTime();
			set4.insert(s1);
			time = System.nanoTime() - startTime;
			test4[i] = time;
		}
	}
	
	public static void delete(DynamicSet<String> set1, DynamicSet<String> set2, DynamicSet<String> set3, DynamicSet<String> set4, ArrayList<String> list,
											long[] test1, long[] test2, long[] test3, long[] test4)
	
	{
		//DLL set
		for (int i = 0; i < list.size(); i++)
		{
			SetElement<String> s1 = set1.search(list.get(i));
			set1.delete(s1);
		}
		
		//SL set
		for (int i = 0; i < list.size(); i++)
		{
			SetElement<String> s2 = set2.search(list.get(i));
			set2.delete(s2);
		}
		
		//BST set
		for (int i = 0; i < list.size(); i++)
		{
			SetElement<String> s3 = set3.search(list.get(i));
			set3.delete(s3);
		}
		
		//RBT set
		for (int i = 0; i < list.size(); i++)
		{
			SetElement<String> s4 = set4.search(list.get(i));
			set4.delete(s4);
		}
	
	}
	
	public static void search(DynamicSet<String> set1, DynamicSet<String> set2, DynamicSet<String> set3, DynamicSet<String> set4, ArrayList<String> list,
											long[] test1, long[] test2, long[] test3, long[] test4, int[] keys)
	
	{
		long startTime = 0;
		long time = 0;
		
		SetElement<String> elem;
		//DLL set
		for (int i = 0; i < 10; i++)
		{
			startTime = System.nanoTime();
			elem = set1.search(list.get(keys[i]));
			time = System.nanoTime() - startTime;
			test1[i] = time;
		}
		
		//SL set
		for (int i = 0; i < 10; i++)
		{
			startTime = System.nanoTime();
			elem = set2.search(list.get(keys[i]));
			time = System.nanoTime() - startTime;
			test2[i] = time;
		}
		
		//BST set
		for (int i = 0; i < 10; i++)
		{
			startTime = System.nanoTime();
			elem = set3.search(list.get(keys[i]));
			time = System.nanoTime() - startTime;
			test3[i] = time;
		}
		
		//RBT set
		for (int i = 0; i < 10; i++)
		{
			startTime = System.nanoTime();
			elem = set4.search(list.get(keys[i]));
			time = System.nanoTime() - startTime;
			test4[i] = time;
		}
	}

	public static void predecessor(DynamicSet<String> set1, DynamicSet<String> set2, DynamicSet<String> set3, DynamicSet<String> set4, ArrayList<String> list,
											long[] test1, long[] test2, long[] test3, long[] test4, int[] keys)
	
	{
		long startTime = 0;
		long time = 0;
		
		SetElement<String> dummy;
		//DLL set
		for (int i = 0; i < 10; i++)
		{
			dummy = set1.search(list.get(keys[i]));
			startTime = System.nanoTime();
			dummy = set1.predecessor(dummy);
			time = System.nanoTime() - startTime;
			test1[i] = time;
		}
		
		//SL set
		for (int i = 0; i < 10; i++)
		{
			dummy = set2.search(list.get(keys[i]));
			startTime = System.nanoTime();
			dummy = set2.predecessor(dummy);
			time = System.nanoTime() - startTime;
			test2[i] = time;
		}

		//BST set
		for (int i = 0; i < 10; i++)
		{
			dummy = set3.search(list.get(keys[i]));
			startTime = System.nanoTime();
			dummy = set3.predecessor(dummy);
			time = System.nanoTime() - startTime;
			test3[i] = time;
		}
		
		//RBT set
		for (int i = 0; i < 10; i++)
		{
			dummy = set4.search(list.get(keys[i]));
			startTime = System.nanoTime();
			dummy = set4.predecessor(dummy);
			time = System.nanoTime() - startTime;
			test4[i] = time;
		}
	}
	
	public static void successor(DynamicSet<String> set1, DynamicSet<String> set2, DynamicSet<String> set3, DynamicSet<String> set4, ArrayList<String> list,
											long[] test1, long[] test2, long[] test3, long[] test4, int[] keys)
	
	{
		long startTime = 0;
		long time = 0;
		
		SetElement<String> dummy;
		//DLL set
		for (int i = 0; i < 10; i++)
		{
			dummy = set1.search(list.get(keys[i]));
			startTime = System.nanoTime();
			dummy = set1.successor(dummy);
			time = System.nanoTime() - startTime;
			test1[i] = time;
		}
		
		//SL set
		for (int i = 0; i < 10; i++)
		{
			dummy = set2.search(list.get(keys[i]));
			startTime = System.nanoTime();
			dummy = set2.successor(dummy);
			time = System.nanoTime() - startTime;
			test2[i] = time;
		}

		//BST set
		for (int i = 0; i < 10; i++)
		{
			dummy = set3.search(list.get(keys[i]));
			startTime = System.nanoTime();
			dummy = set3.successor(dummy);
			time = System.nanoTime() - startTime;
			test3[i] = time;
		}
		
		//RBT set
		for (int i = 0; i < 10; i++)
		{
			dummy = set4.search(list.get(keys[i]));
			startTime = System.nanoTime();
			dummy = set4.successor(dummy);
			time = System.nanoTime() - startTime;
			test4[i] = time;
		}
	}
	
	public static void minimum(DynamicSet<String> set1, DynamicSet<String> set2, DynamicSet<String> set3, DynamicSet<String> set4, ArrayList<String> list,
											long[] test1, long[] test2, long[] test3, long[] test4, long[] result)
	
	{
		long startTime = 0;
		long time = 0;
		
		SetElement<String> dummy;
		//DLL set
		startTime = System.nanoTime();
		dummy = set1.minimum();
		time = System.nanoTime() - startTime;
		result[0] = time;
		
		//SL set
		startTime = System.nanoTime();
		dummy = set2.minimum();
		time = System.nanoTime() - startTime;
		result[1] = time;

		//BST set
		startTime = System.nanoTime();
		dummy = set3.minimum();
		time = System.nanoTime() - startTime;
		result[2] = time;
		
		//RBT set
		startTime = System.nanoTime();
		dummy = set4.minimum();
		time = System.nanoTime() - startTime;
		result[3] = time;
	}
	
	public static void maximum(DynamicSet<String> set1, DynamicSet<String> set2, DynamicSet<String> set3, DynamicSet<String> set4, ArrayList<String> list,
											long[] test1, long[] test2, long[] test3, long[] test4, long[] result)
	
	{
		long startTime = 0;
		long time = 0;
		
		SetElement<String> dummy;
		//DLL set
		startTime = System.nanoTime();
		dummy = set1.maximum();
		time = System.nanoTime() - startTime;
		result[0] = time;
		
		//SL set
		startTime = System.nanoTime();
		dummy = set2.maximum();
		time = System.nanoTime() - startTime;
		result[1] = time;

		//BST set
		startTime = System.nanoTime();
		dummy = set3.maximum();
		time = System.nanoTime() - startTime;
		result[2] = time;
		
		//RBT set
		startTime = System.nanoTime();
		dummy = set4.maximum();
		time = System.nanoTime() - startTime;
		result[3] = time;
	}

		
	
	public static void stats(long[] test1, long[] test2, long[] test3, long[] test4, long[] results, int trials)
	{
		//calculate min avg and max values for DLL
		long avg = 0;
		long min = test1[0];
		long max = test1[trials - 1];
		for (int i = 0; i < trials - 1; i++)
		{
			if (test1[i] < min)
				min = test1[i];
			else if (test1[i] > max)
				max = test1[i];
			avg+= test1[i];
		}
		avg = avg/trials;
		results[0] = min;
		results[1] = avg;
		results[2] = max;
		
		//calculate min avg and max values for SL
		avg = 0;
		min = test2[0];
		max = test2[trials - 1];
		for (int i = 0; i < trials - 1; i++)
		{
			if (test2[i] < min)
				min = test2[i];
			else if (test2[i] > max)
				max = test2[i];
			avg+= test2[i];
		}
		avg = avg/trials;
		results[3] = min;
		results[4] = avg;
		results[5] = max;
		
		//calculate min avg and max values for BST
		avg = 0;
		min = test3[0];
		max = test3[trials - 1];
		for (int i = 0; i < trials - 1; i++)
		{
			if (test3[i] < min)
				min = test3[i];
			else if (test3[i] > max)
				max = test3[i];
			avg+= test3[i];
		}
		avg = avg/trials;
		results[6] = min;
		results[7] = avg;
		results[8] = max;
		
		//calculate min avg and max values for RBT
		avg = 0;
		min = test4[0];
		max = test4[trials - 1];
		for (int i = 0; i < trials - 1; i++)
		{
			if (test4[i] < min)
				min = test4[i];
			else if (test4[i] > max)
				max = test4[i];
			avg+= test4[i];
		}
		avg = avg/trials;
		results[9] = min;
		results[10] = avg;
		results[11] = max;

	}
	
	public static void printTable(long[] insert, long[] search, long[] predecessor, long[] successor, 
											long[] minimum, long[] maximum, int size, String filename)
	{
		String[] values1 = new String[12]; //insert
		String[] values2 = new String[12]; //search
		String[] values3 = new String[12]; //predecessor
		String[] values4 = new String[12]; //successor
		String[] values5 = new String[4];  //minimum
		String[] values6 = new String[4]; //maximum
		
		//formatting insert data
		for (int i = 0; i < 12; i++)
		{
			if (i%3 == 0)
				values1[i] = "Min: " + insert[i];
			else if (i%3 == 1)
				values1[i] = "Avg: " + insert[i];
			else
				values1[i] = "Max: " + insert[i];
			
			if (values1[i].length() > 15)
				values1[i].trim();
				
			while (values1[i].length() < 15)
				values1[i] = values1[i] + " ";
		}
		
		//formatting search data
		for (int i = 0; i < 12; i++)
		{
			if (i%3 == 0)
				values2[i] = "Min: " + search[i];
			else if (i%3 == 1)
				values2[i] = "Avg: " + search[i];
			else
				values2[i] = "Max: " + search[i];
			
			if (values2[i].length() > 15)
				values2[i].trim();
				
			while (values2[i].length() < 15)
				values2[i] = values2[i] + " ";
		}
		
		//formatting predecessor data
		for (int i = 0; i < 12; i++)
		{
			if (i%3 == 0)
				values3[i] = "Min: " + predecessor[i];
			else if (i%3 == 1)
				values3[i] = "Avg: " + predecessor[i];
			else
				values3[i] = "Max: " + predecessor[i];
			
			if (values3[i].length() > 15)
				values3[i].trim();
				
			while (values3[i].length() < 15)
				values3[i] = values3[i] + " ";
		}
		
		//formatting successor data
		for (int i = 0; i < 12; i++)
		{
			if (i%3 == 0)
				values4[i] = "Min: " + successor[i];
			else if (i%3 == 1)
				values4[i] = "Avg: " + successor[i];
			else
				values4[i] = "Max: " + successor[i];
			
			if (values4[i].length() > 15)
				values4[i].trim();
				
			while (values4[i].length() < 15)
				values4[i] = values4[i] + " ";
		}
		
		//formatting minimum data
		for (int i = 0; i < 4; i++)
		{
			values5[i] = "Time: " + minimum[i];
			
			if (values5[i].length() > 15)
				values5[i].trim();
			
			while (values5[i].length() < 15)
				values5[i] = values5[i] + " ";
		}
		
		//formatting maximum data
		for (int i = 0; i < 4; i++)
		{
			values6[i] = "Time: " + maximum[i];
			
			if (values6[i].length() > 15)
				values6[i].trim();
			
			while (values6[i].length() < 15)
				values6[i] = values6[i] + " ";
		}
		
		System.out.println("/**********Dynamic Set Test Results************/\n");
		System.out.println("File Name: " + filename);
		System.out.println("Size: " + size + " (times reported in nanoseconds)");
		System.out.println("------------------------------------------------------------------------------");
		System.out.println("             | DLL           | SL            | BST           | RBT           |");
		System.out.println("-----------------------------------------------------------------------------|");
		System.out.println("insert       |" + values1[0] + "|" + values1[3] + "|" + values1[6] + "|" + values1[9] + "|"); 
		System.out.println("             |" + values1[1] + "|" + values1[4] + "|" + values1[7] + "|" + values1[10] + "|");
		System.out.println("             |" + values1[2] + "|" + values1[5] + "|" + values1[8] + "|" + values1[11] + "|");
		System.out.println("-----------------------------------------------------------------------------|");
		System.out.println("search       |" + values2[0] + "|" + values2[3] + "|" + values2[6] + "|" + values2[9] + "|"); 
		System.out.println("             |" + values2[1] + "|" + values2[4] + "|" + values2[7] + "|" + values2[10] + "|");
		System.out.println("             |" + values2[2] + "|" + values2[5] + "|" + values2[8] + "|" + values2[11] + "|");
		System.out.println("-----------------------------------------------------------------------------|");
		System.out.println("predecessor  |" + values3[0] + "|" + values3[3] + "|" + values3[6] + "|" + values3[9] + "|"); 
		System.out.println("             |" + values3[1] + "|" + values3[4] + "|" + values3[7] + "|" + values3[10] + "|");
		System.out.println("             |" + values3[2] + "|" + values3[5] + "|" + values3[8] + "|" + values3[11] + "|");
		System.out.println("-----------------------------------------------------------------------------|");
		System.out.println("successor    |" + values4[0] + "|" + values4[3] + "|" + values4[6] + "|" + values4[9] + "|"); 
		System.out.println("             |" + values4[1] + "|" + values4[4] + "|" + values4[7] + "|" + values4[10] + "|");
		System.out.println("             |" + values4[2] + "|" + values4[5] + "|" + values4[8] + "|" + values4[11] + "|");
		System.out.println("-----------------------------------------------------------------------------|");
		System.out.println("minimum      |" + values5[0] + "|" + values5[1] + "|" + values5[2] + "|" + values5[3] + "|"); 
		System.out.println("-----------------------------------------------------------------------------|");
		System.out.println("maximum      |" + values6[0] + "|" + values6[1] + "|" + values6[2] + "|" + values6[3] + "|"); 
		System.out.println("------------------------------------------------------------------------------\n");



	}
	
}
		
		