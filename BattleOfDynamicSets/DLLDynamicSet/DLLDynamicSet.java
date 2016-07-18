/*
 * Copyright (c) 2013, Kevin Barroga
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the names of the copyright holders nor the
 *       names of any contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */

package DLLDynamicSet;

import DynamicSet.*;


public class DLLDynamicSet<T extends Comparable<T>> extends DynamicSet<T> {
	
	private DLLNode<T> head; //DLL sentinel
	
	//Constructor that initialized a new list with a sentinel that references the head and tail of the list.
	public DLLDynamicSet() {
		
		super();
		head = new DLLNode<T>(null, null);
		head.setNext(head);
		head.setPrev(head);
	}
	
	/////////////// Main method test ///////////////
	public static void main(String []args) {
		
		DLLDynamicSet<String> set = new DLLDynamicSet<String>();
		String s1 = "Ja'far";
		String s2 = "Abdullah";
		String s3 = "Maha";
		String s4 = "Amina";
		
		DLLNode<String> n1 = new DLLNode<String>(s1, s1);
		DLLNode<String> n2 = new DLLNode<String>(s2, s2);
		DLLNode<String> n3 = new DLLNode<String>(s3, s3);
		DLLNode<String> n4 = new DLLNode<String>(s4, s4);
		
		//test insert
		set.insert(n2);
		set.insert(n3);
		set.insert(n1);
		set.insert(n4);
		set.insert(n1);
		
		System.out.println(set.toString());
		
		//test delete
		set.delete(n2);
		set.delete(n3);
		set.delete(n1);
		set.delete(n4);
		set.delete(n1);
		
		//reinitialize list
		set.insert(n1);
		set.insert(n2);
		set.insert(n3);
		set.insert(n4);		
		
		//testing maximum
		DLLNode<String> testNode;
		long startTime = System.nanoTime();
		testNode = set.maximum();
		long time = System.nanoTime() - startTime;
		System.out.println(time);
		
		System.out.println("Maximum element:" + testNode.getKey());
		
		//test minimum
		testNode = set.minimum();
		System.out.println("Minimum element:" + testNode.getKey());
		
		//testing predecessor
		testNode = set.predecessor(n2);
		System.out.println("Predecessor of " + n2.getKey() + ": " + testNode.getKey());
				
		testNode = set.predecessor(n1);
		System.out.println("Predecessor of " + n1.getKey() + ": " + testNode.getKey());
				
		//testing successor
		testNode = set.successor(n2);
		System.out.println("Successor of " + n2.getKey() + ": " + testNode.getKey());
				
		testNode = set.successor(n3);
		System.out.println("Successor of " + n3.getKey() + ": " + testNode.getKey());
		
	}
	
	//insert element into list
	public void insert(SetElement<T> e) {
		
		if(search(e.getKey()) == null) {
			
			DLLNode<T> toAdd = new DLLNode<T>(e.getKey(), e.getData());
			
			//search for appropriate position to insert into list
			DLLNode<T> target = searchPosition(e.getKey());
			
			// if target = null then the element to be inserted will be the max element
			if(target == null)
				target = head;
			//perform insertion
			target.getPrev().setNext(toAdd);
			toAdd.setPrev(target.getPrev());
			toAdd.setNext(target);
			target.setPrev(toAdd);
			
			//update size
			super.size++;
		}
		
	}
	
	private DLLNode<T> searchPosition (T k) {
		
		if (k != null && head.getNext().getKey() != null) {
			
			DLLNode<T> current = head.getNext();	//assign head to current
			
			//loop through list until the current possition's key is >= k
			while(current != head && current.getKey().compareTo(k) < 0) {
				current = current.getNext();
			}
			if(current == head) {
				return null;
			}
			else {
				return current;
			}	
		}
		else
			return null;
	}

	//Given pointer to element e in set, remove e from set.
	public void delete(SetElement<T> e) {
		
		if(e != null) {
			
			DLLNode<T> node = search(e.getKey());
			
			if(node != null) {
				node.getPrev().setNext(node.getNext());
				node.getNext().setPrev(node.getPrev());
				super.size--;	//update size
				System.gc();	//run garbage collector
			}
		}
	}

	//Find a a SetElement with key 'k' and returns a pointer to it,
	//or returns a pointer to the node with the greatest key < k,
	//or null if the key is always < k.
	public DLLNode<T> search(T k) {
		
		if (k != null && head.getNext().getKey() != null) {
			
			DLLNode<T> current = head.getNext();
			
			//loop through the list until the key is >= to k
			while(current != head && current.getKey().compareTo(k) < 0) {
				current = current.getNext();
			}
			if(current == head)
				return null;
			else if(current.getKey().compareTo(k) == 0)
				return current;
			else
				return null;
		}
		else
			return null;
	}
	
	//Find a SetElement that has the smallest key, and returns a pointer to it, or null if empty
	public DLLNode<T> minimum() {
		
		if(head.getNext() != head) {
			return head.getNext();
		}
		else
			return null;
	}

	//Find a SetElement that has the largest key, and returns a pointer to it, of null if empty
	public DLLNode<T> maximum() {
		
		if(head.getPrev() != head) {
			return head.getPrev();
		}
		else
			return null;
	}

	//Given a pointer to element e in the set, finds a SetElement in the set that has the next larger key, and returns
	//a pointer to it, or null if e is the maximum element
	public DLLNode<T> successor(SetElement<T> e) {
		
		if(e != null) {
			
			DLLNode<T> succ = search(e.getKey());
			
			if(succ != null)
				return succ.getNext();
			else
				return null;
		}
		else
			return null;
	}
	
	//Given a pointer to element e in the set, finds a SetElement in the set that has the next smaller key,
	// and returns a pointer to it, or null if e is the minimum element.
	public DLLNode<T> predecessor(SetElement<T> e) {
		
		if(e != null) {
			
			DLLNode<T> pred = search(e.getKey());
			
			if(pred != null)
				return pred.getPrev();
			else
				return pred;
		}
		else
			return null;
	}
	
	public String toString() {
		
		StringBuilder result = new StringBuilder("[");
		DLLNode<T> current = this.head.getNext();
		
		while(current != this.head) {
			result.append(current.getKey());
			current = current.getNext();
			if(current != this.head) {
				result.append(", ");
			}
		}
		result.append("]");
		return result.toString();
	}
	
}
