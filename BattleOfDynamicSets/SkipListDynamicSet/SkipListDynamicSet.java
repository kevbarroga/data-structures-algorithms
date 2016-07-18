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

package SkipListDynamicSet;

import java.util.ArrayList;
import DynamicSet.*;

public class SkipListDynamicSet<T extends Comparable<T>> extends DynamicSet<T> {
	
	private ArrayList<SLNode<T>> dic;
	private int top;	//size of arraylist, number of levels in the skiplist
	private int current;	//current level of skiplist
	private SLNode<T> head;
	
	public SkipListDynamicSet() {
		
		super();
		dic = new ArrayList<SLNode<T>>();	//arraylist of doubly linked list with sentinels
		//initialize the skip list
		SLNode<T> one = new SLNode<T>(null, null);	//create two sublist with sentinels
		SLNode<T> two = new SLNode<T>(null, null);
		dic.add(one);	//add them into dictionary containing skip list
		dic.add(two);
		top = dic.size() - 1;	//refers to number of levels in the skiplist
		current = top;	//current level while traversing skiplist
		
		//initialize horizontal pointer
		dic.get(top).setNext(dic.get(top));
		dic.get(top).setPrev(dic.get(top));
		dic.get(top - 1).setNext(dic.get(top - 1));
		dic.get(top - 1).setPrev(dic.get(top - 1));
		
		//initialize vertical pointers
		dic.get(top).setBelow(dic.get(top - 1));	//for smallest
		dic.get(top - 1).setAbove(dic.get(top));
		
		dic.get(top).setBelow(dic.get(top - 1));	//for greatest
		dic.get(top - 1).setAbove(dic.get(top));
		
		head = dic.get(top);	//assign top list smallest element to head
	}
	
	public static void main(String []args) {
		
		SkipListDynamicSet<String> set = new SkipListDynamicSet<String>();
		String s1 = "Ja'far";
		String s2 = "Abdullah";
		String s3 = "Maha";
		String s4 = "Amina";
		
		SLNode<String> n1 = new SLNode<String>(s1, s1);
		SLNode<String> n2 = new SLNode<String>(s2, s2);
		SLNode<String> n3 = new SLNode<String>(s3, s3);
		SLNode<String> n4 = new SLNode<String>(s4, s4);
		
		//test insert
		set.insert(n2);
		set.insert(n3);
		set.insert(n1);
		set.insert(n4);
		set.insert(n1);
		
		System.out.println(set.toString());
		
		set.delete(n2);
		set.insert(n2);
		
		//testing max
		SLNode<String> testNode = set.maximum();
		System.out.println("Maximun set element: " + testNode.getKey());
		
		//testing min
		testNode = set.minimum();
		System.out.println("Minimum set element: " + testNode.getKey());
		
		//testing successor
		testNode = set.successor(n3);
		if(testNode == null)
			System.out.println("No Successor of " + n3.getKey());
		else
			System.out.println("Successor of " + n3.getKey() + ": " + testNode.getKey());
		
		//testing predecessor
		testNode = set.predecessor(n1);
		if(testNode == null)
			System.out.println("No Predecessor of " + n1.getKey());
		else
			System.out.println("Predecessor of " + n1.getKey() + ": " + testNode.getKey());
	}
		

	public void insert(SetElement<T> e) {
		
		if(search(e.getKey()) == null) {
			
			//SLNode<T> temp = new SLNode<T>(e.getKey(), e.getData());
			SLNode<T> p = searchpos(e.getKey());	//search for position to insert
			SLNode<T> q = insertAfterAbove(p, null, e);	//perform insertion in bottom horizontal list
														//return the position of new element
			current = 0;
			//determines of loop executes
			while(Math.random() < (double)1/2) {
				
				//if current is at the top, then we need to add 2 sublists to the top of the skiplist
				//to ensure there is always an empty list at the top
				if(current == top) {
					
					for(int j = 0; j < 2; j++) {
						
						SLNode<T> newList = new SLNode<T>(null, null);
						dic.add(newList);
						top = dic.size() - 1;	//one more list added so we reset top variable
						
						//initialize horizontal pointers
						dic.get(top).setNext(dic.get(top));
						dic.get(top).setPrev(dic.get(top));
						
						//initialize vertical pointers
						dic.get(top).setBelow(dic.get(top - 1));	//for smallest
						dic.get(top - 1).setAbove(dic.get(top));
						
						dic.get(top).setBelow(dic.get(top - 1));	//for greatest
						dic.get(top - 1).setAbove(dic.get(top));
						
						head = dic.get(top);	//assign top list smallest element to head
					}
				}
				
				while(p.getAbove() == null) {
					
					p = p.getPrev();	//scan backward
				}
				p = p.getAbove();	//jump to next level
				current++;
				q = insertAfterAbove(p, q, e);	//insert new item
			}
			super.size++;
		}
		
	}
	
	private SLNode<T> searchpos(T k) {
		
		if(k != null) {
			
			SLNode<T> p = head;
			current = top;
			while(p != null && p.getBelow() != null) {
				
				p = p.getBelow();
				current--;	//decrement to next level
				while(p.getNext() != dic.get(current) && p.getNext().getKey().compareTo(k) <= 0) {
					
					p = p.getNext();
				}
			}
			if(p == dic.get(current)) {		//list is empty so return pointer to sentinel
				return dic.get(current);
			}
			else
				return p;
		}
		else 
			return null;
	}

	private SLNode<T> insertAfterAbove(SLNode<T> p, SLNode<T> q, SetElement<T> e) {
		
		SLNode<T> newnode = new SLNode<T>(e.getKey(), e.getData());
		
		//splice newnode into bottom horizontal list
		p.getNext().setPrev(newnode);
		newnode.setPrev(p);
		newnode.setNext(p.getNext());
		p.setNext(newnode);
		
		//splice newnode into vertical list, however only below since splicing is determined by Math.random()
		newnode.setBelow(q);
		if(q != null) {
			
			q.setAbove(newnode);
		}
		return newnode;
	}
	
	
	
	public void delete(SetElement<T> e) {
		
		if(e != null) {
			
			SLNode<T> p = search(e.getKey());
			
			if(p != null) {
				
				while(p != null) {
					
					if(p.getPrev() == dic.get(current) && p.getNext() == dic.get(current)) {
						
						//splice out p a the current level
						dic.get(current).setNext(dic.get(current));
						dic.get(current).setPrev(dic.get(current));
						
						//remove top list
						dic.remove(top);
						top = dic.size() - 1;
					}
					else {
						
						p.getPrev().setNext(p.getNext());
						p.getNext().setPrev(p.getPrev());
					}
					p = p.getBelow();
					current--;
				}
			}
		}
		super.size--;
	}

	
	public SLNode<T> search(T k) {
		
		if(k != null) {
			
			SLNode<T> p = head;
			current = top;
			boolean combobreaker = true;
			
			while(p != null && p.getBelow() != null && combobreaker) {
				
				p = p.getBelow();
				current--;
				while(p.getNext() != dic.get(current) && p.getNext().getKey().compareTo(k) <= 0) {
					
					p = p.getNext();
					if(p.getNext() != dic.get(current) && p.getNext().getKey().compareTo(k) == 0)
						combobreaker = false;
				}
			}
			if(p == dic.get(current))	//list is empty
				return null;
			else if(p.getKey().compareTo(k) == 0)
				return p;
			else
				return null;	//k isn't in the list 
		}
		else
			return null;
		
	}

	
	public SLNode<T> minimum() {
		
		if(dic.get(0).getNext() == dic.get(0))
			return null;
		else
			return dic.get(0).getNext();
	}

	
	public SLNode<T> maximum() {
		
		if(dic.get(0).getPrev() == dic.get(0))
			return null;
		else
			return dic.get(0).getPrev();
	}

	
	public SLNode<T> successor(SetElement<T> e) {
		
		SLNode<T> p = search(e.getKey());
		if(p != null) {
			
			while(p.getBelow() != null) {
				
				p = p.getBelow();
				current--;
			}
			if(p.getNext() == dic.get(0)) {
				
				p = null;
			}
			else
				p = p.getNext();
		}
		return p;
	}

	public SLNode<T> predecessor(SetElement<T> e) {
		
		SLNode<T> p = search(e.getKey());
		if(p != null) {
			
			while(p.getBelow() != null) {
				
				p = p.getBelow();
				current--;
			}
			if(p.getPrev() == dic.get(0)) {
				
				p = null;
			}
			else p = p.getPrev();
		}
		return p;
	}
	
	public String toString() {
		
		StringBuilder result = new StringBuilder();
		result.append("[");
		
		SLNode<T> p = head;
		current = top;
		while(p != null && p.getBelow() != null) {
			
			p = p.getBelow();
			current--;
		}
		while(p.getNext() != dic.get(current) && p.getNext() != null) {
			result.append(p.getNext().getKey());
			p = p.getNext();
			if(p.getNext() != dic.get(current) && p.getNext() != null) {
				result.append(", ");
			}
		}
		result.append("]");
		return result.toString();
	}
}
