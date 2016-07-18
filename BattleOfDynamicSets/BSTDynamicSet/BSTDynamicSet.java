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

package BSTDynamicSet;

import DynamicSet.*;

public class BSTDynamicSet<T extends Comparable<T>> extends DynamicSet<T> {
	
	private BSTNode<T> root;
	
	public BSTDynamicSet() {
		
		super();
		root = null;
	}
	
	public static void main(String []args) {
		
		BSTDynamicSet<String> set = new BSTDynamicSet<String>();
		String s1 = "Ja'far";
		String s2 = "Abdullah";
		String s3 = "Maha";
		String s4 = "Amina";
		
		BSTNode<String> n1 = new BSTNode<String>(s1, s1);
		BSTNode<String> n2 = new BSTNode<String>(s2, s2);
		BSTNode<String> n3 = new BSTNode<String>(s3, s3);
		BSTNode<String> n4 = new BSTNode<String>(s4, s4);
		
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
		BSTNode<String> testNode;
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
		if(testNode != null)
			System.out.println("Predecessor of " + n2.getKey() + ": " + testNode.getKey());
		else
			System.out.println(n2.getKey() + " has no predecessor.");
				
		testNode = set.predecessor(n1);
		if(testNode != null)
			System.out.println("Predecessor of " + n1.getKey() + ": " + testNode.getKey());
		else
			System.out.println(n1.getKey() + " has no predecessor.");
				
		//testing successor
		testNode = set.successor(n2);
		if(testNode != null)
			System.out.println("Successor of " + n2.getKey() + ": " + testNode.getKey());
		else
			System.out.println(n2.getKey() + " has no successor.");
			
		testNode = set.successor(n3);
		if(testNode != null)
			System.out.println("Successor of " + n3.getKey() + ": " + testNode.getKey());
		else
			System.out.println(n3.getKey() + " has no successor.");
		
	}
	
	/*
	 * Methods taken from algorithms provided from Cormen et al's text
	 * Introduction of Algorithms, MIT Press, 2009.
	 */
	
	//Insert element e in set. Assumes e has been properly initialized
	public void insert(SetElement<T> e) {
		
		if(search(e.getKey()) == null) {	//check to see if e is already in tree
		
			BSTNode<T> toAdd = new BSTNode<T>(e.getKey(), e.getData());
			BSTNode<T> trailing = null;
			BSTNode<T> current = root;
			
			while(current != null) {
				trailing = current;
				if(toAdd.getKey().compareTo(current.getKey()) < 0)
					current = current.getLeft();
				else
					current = current.getRight();
			}
			//fell off end of tree, so insert here
			toAdd.setParent(trailing);
			if(trailing == null)	//tree empty
				root = toAdd;
			else if(toAdd.getKey().compareTo(trailing.getKey()) < 0)
				trailing.setLeft(toAdd);
			else
				trailing.setRight(toAdd);
		
			super.size++;
		}
	}

	//Giver pointer to element e, remove element e
	public void delete(SetElement<T> e) {
		
		
		BSTNode<T> node = search(e.getKey());
		
		if(node != null) {	//list must not be empty before delete
			BSTNode<T> successor;
			if(node.getLeft() == null)	//has only right child, which could be null
				transplant(node, node.getRight());	//transplant is used to pull up subtree to replace deleted node
			else if(node.getRight() == null)
				transplant(node, node.getLeft());
			else {	
				//node has two children. Find successor
				successor = treeMinimum(node.getRight());
				if(successor.getParent() != node) {
					
					transplant(successor, successor.getRight());
					successor.setRight(node.getRight());
					successor.getRight().setParent(successor);
				}
				transplant(node, successor);
				successor.setLeft(node.getLeft());
				successor.getLeft().setParent(successor);
			}
			super.size--;
		}	
	}
	
	/**
	 * Find the minimum node of subtree
	 */
	private BSTNode<T> treeMinimum(BSTNode<T> subtree) {
		
		BSTNode<T> current = subtree;
		
		while(current.getLeft() != null)
			current = current.getLeft();
		
		return current;
	}
	
	/**
	 * Replaces tree rooted at the given node with the given replacement subtree.
	 */
	private void transplant(BSTNode<T> node, BSTNode<T> replacement) {
		
		//if the root is the node to be deleted, then replace becomes new root
		if(node.getParent() == null)
			root = replacement;
		//if the root to be deleted is its parents left child, change out to replace
		else if(node == node.getParent().getLeft())
			node.getParent().setLeft(replacement);
		else
			node.getParent().setRight(replacement);
		if(replacement != null)
			replacement.setParent(node.getParent());
	}

	//Finds Set Element that has the smallest key, and returns a pointer to it., or null if the set is empty
	public BSTNode<T> search(T k) {
		
		BSTNode<T> current = root;
		
		while(current != null && k.compareTo(current.getKey()) != 0) {
			if(k.compareTo(current.getKey()) < 0)
				current = current.getLeft();
			else
				current = current.getRight();
		}
		return current;
	}

	//Find a SetElement that has the smallest key, return pointer to it, or null if empty set
	public BSTNode<T> minimum() {
		
		BSTNode<T> current = root;
		
		while(current.getLeft() != null)
			current = current.getLeft();
		
		return current;
	}

	//Finds and returns element with largest key in set
	public BSTNode<T> maximum() {
		
		BSTNode<T> current = root;
		
		while(current.getRight() != null)
			current = current.getRight();
		
		return current;
	}

	/*
	 * Given a pointer to element e in the set, finds a SetElement in the set
	 * that has the next larger key, and returns a pointer to it, or null if
	 * e is the maximum element.
	 */
	public BSTNode<T> successor(SetElement<T> e) {
		
		BSTNode<T> succ = search(e.getKey());
		
		if(succ != null)
			return succ.getRight();
		else
			return null;
	}

	/*
	 * Given a pointer to element e in the set, finds a SetElement in the set 
	 * that has the next smaller key, and returns a pointer to it, or null if
	 * e is the minimum element.
	 */
	public BSTNode<T> predecessor(SetElement<T> e) {
		
		BSTNode<T> pred = search(e.getKey());
		
		if(pred != null)
			return pred.getRight();
		else
			return null;
	}
	
	/**
	 * Performs inorder traversal on tree
	 * returns a String
	 */
	private String toStringInorder(BSTNode<T> node) {
		
		if(node == null) {
			return "";
		}
		//get left tree
		StringBuilder result = new StringBuilder();
		result.append(toStringInorder(node.getLeft()));
		
		//add this node
		if(node.getLeft() != null) {
			result.append(", ");
		}
		result.append(node.getKey());
		result.append("=");
		result.append(node.getData());
		if(node.getRight() != null) {
			result.append(", ");
		}
		
		//get right tree
		result.append(toStringInorder(node.getRight()));
		return result.toString();
	}
	
	public String toString() {
		return "[" + toStringInorder(this.root) + "]";
	}
	
}
