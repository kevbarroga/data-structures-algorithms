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

package RedBlackDynamicSet;

import DynamicSet.*;

public class RedBlackDynamicSet<T extends Comparable<T>> extends DynamicSet<T> {
	
	private RBTNode<T> root;
	private RBTNode<T> nil;
	
	public RedBlackDynamicSet() {
		
		super();
		nil = new RBTNode<T>(null, null);
		root = nil;
		root.setParent(nil);
		nil.setParent(nil);
		nil.setLeft(nil);
		nil.setRight(nil);
	}
	
	public static void main(String []args) {
		
		RedBlackDynamicSet<String> set = new RedBlackDynamicSet<String>();
		String s1 = "Ja'far";
		String s2 = "Abdullah";
		String s3 = "Maha";
		String s4 = "Amina";
		
		RBTNode<String> n1 = new RBTNode<String>(s1, s1);
		RBTNode<String> n2 = new RBTNode<String>(s2, s2);
		RBTNode<String> n3 = new RBTNode<String>(s3, s3);
		RBTNode<String> n4 = new RBTNode<String>(s4, s4);
		
		/* the following are tests on the algorithms to be implemented in the
		   abstract class, DynamicSet<T>. each case tests the search method */
		  
		//testing insert
		set.insert(n1);
		set.insert(n2);
		set.insert(n3);
		set.insert(n4);
		set.insert(n1);
		
		System.out.println(set.search(n3.getKey()).getKey());
		set.delete(n4);
		set.delete(n3);
		set.delete(n2);
		set.delete(n1);
		set.delete(n1);
		
		set.insert(n4);
		set.insert(n2);
		set.insert(n3);
		set.insert(n1);
		set.insert(n1);

		
		//testing max
		RBTNode<String> testNode = set.maximum();
		System.out.println("Maximum set element: " + testNode.getKey());
		
		//testing min
		testNode = set.minimum();
		System.out.println("Minimum set element: " + testNode.getKey());
		
		//testing successor
		testNode = set.successor(n3);
		if (testNode == null)
			System.out.println("No Successor of " + n3.getKey());
		else
			System.out.println("Successor of " + n3.getKey() + ": " + testNode.getKey());
		
		//testing predecessor
		testNode = set.predecessor(n1);
		if (testNode == null)
			System.out.println("No Predecessor of " + n1.getKey());
		else
			System.out.println("Predecessor of " + n1.getKey() + ": " + testNode.getKey());
		
	}

	/*
	 * Methods taken from algorithms provided from Cormen et al's text
	 * Introduction of Algorithms, MIT Press, 2009.
	 */
	
	//Insert element e in the set. Assumes that e has been properly initialized.
	public void insert(SetElement<T> e) {
		
		if (search(e.getKey()) == nil) {	//check if the element e is already in the tree
			RBTNode<T> toAdd = new RBTNode<T>(e.getKey(), e.getData());
			
			toAdd.setLeft(nil);
			toAdd.setRight(nil);
			toAdd.setParent(nil);
			RBTNode<T> trailing = nil;
			RBTNode<T> current = root;
			
			while (current != nil) {
				trailing = current;
				if (toAdd.getKey().compareTo(current.getKey()) < 0)
					current = current.getLeft();
				else
					current = current.getRight();
			
			}
			//current fell off tree, so the trailing is insert location
			toAdd.setParent(trailing);
			if (trailing == nil)
				root = toAdd;
			else if (toAdd.getKey().compareTo(trailing.getKey()) < 0)
				trailing.setLeft(toAdd);
			else
				trailing.setRight(toAdd);
			toAdd.setLeft(nil);
			toAdd.setRight(nil);
			toAdd.setColor(true);
			RBInsertFixup(toAdd);
			super.size++;
		}
	}
	
	/* Taken from the algorithm provided by the text; Cormen */
	private void RBInsertFixup(RBTNode<T> node) {
		
		RBTNode<T> current;
		//true for red
		//false for black
		
		while (node.getParent().getColor()) {	//if elm's parent's color is red
			
			if (node.getParent() == node.getParent().getParent().getLeft()) {
				current = node.getParent().getParent().getRight();
				
				if (current.getColor()){	//if trailing node is red
					node.getParent().setColor(false);
					current.setColor(false);
					node.getParent().getParent().setColor(true);
					node = node.getParent().getParent();
				}
				else if (node == node.getParent().getRight()) {
					node = node.getParent();
					LeftRotate(node);
				}
				else {
					node.getParent().setColor(false); //false for black
					node.getParent().getParent().setColor(true); //true for red
					RightRotate(node.getParent().getParent());
				}
			}
			else {
				current = node.getParent().getParent().getLeft();
				if (current.getColor()) {	//true for red
					node.getParent().setColor(false); //false for black
					current.setColor(false); //false for black
					node.getParent().getParent().setColor(true); //true for red
					node = node.getParent().getParent();
				}
				else if (node == node.getParent().getLeft()) {
					node = node.getParent();
					RightRotate(node);
				}
				else {
					node.getParent().setColor(false); //false for black
					node.getParent().getParent().setColor(true); //true for red
					LeftRotate(node.getParent().getParent());
				}
			}
		}
		root.setColor(false); //false for black
	}
	
	/* Taken from the algorithm provided by the text; Cormen */
	private void LeftRotate(RBTNode<T> node) {
		
		RBTNode<T> rightchild = node.getRight();
		node.setRight(rightchild.getLeft());

		if (rightchild.getLeft() != nil)
			rightchild.getLeft().setParent(node);

		rightchild.setParent(node.getParent());
		
		if (node.getParent() == nil)
			root = rightchild;
		else if (node.getKey().compareTo(node.getParent().getLeft().getKey()) == 0)
			node.getParent().setLeft(rightchild);
		else
			node.getParent().setRight(rightchild);
		
		rightchild.setLeft(node);
		node.setParent(rightchild);
	}
	
	/* Taken from the algorithm provided by the text; Cormen */
	private void RightRotate(RBTNode<T> node) {
		
		RBTNode<T> leftchild = node.getLeft();
		node.setLeft(leftchild.getRight());
		
		if (leftchild.getRight() != nil)
			leftchild.getRight().setParent(node);
		
		leftchild.setParent(node.getParent());
		
		if (node.getParent() == nil)
			root = leftchild;
		else if (node.getKey().compareTo(node.getParent().getRight().getKey()) == 0)
			node.getParent().setRight(leftchild);
		else
			node.getParent().setLeft(leftchild);
		
		leftchild.setRight(node);
		node.setParent(leftchild);
	}
	
	/* Taken from the algorithm provided by the text; Cormen */
	//Given a pointer to element e in the set, removes e from the set.
	public void delete(SetElement<T> e) {
		
		RBTNode<T> node = search(e.getKey());
		
		if (node != nil)
		{
			RBTNode<T> trailing = node;
			RBTNode<T> leading;
			boolean original = trailing.getColor(); //store trailing node's original color
			if (node.getLeft() == nil) {
				leading = node.getRight();
				RedBlackTransplant(node, node.getRight());
			}
			else if (node.getRight() == nil) {
				leading = node.getLeft();
				RedBlackTransplant(node, node.getLeft());
			}
			else {
				trailing = RBTreeMinimum(node.getRight());
				original = trailing.getColor();
				leading = trailing.getRight();
				if (trailing.getParent() == node)
					leading.setParent(trailing);
				else {
					RedBlackTransplant(trailing, trailing.getRight());
					trailing.setRight(node.getRight());
					trailing.getRight().setParent(trailing);
				}
				RedBlackTransplant(node, trailing);
				trailing.setLeft(node.getLeft());
				trailing.getLeft().setParent(trailing);
				trailing.setColor(node.getColor());
			}
			if (original == false) //false for black
				RedBlackDeleteFixup(leading);
			super.size--;
		}
		
	}
	
	/* Taken from the algorithm provided by the text; Cormen */
	private void RedBlackDeleteFixup(RBTNode<T> leading) {
		RBTNode<T> w;
		while (leading != root && !leading.getColor()) {	//while leading isnt root, and its color is black
			if (leading == leading.getParent().getLeft()) {
				
				w = leading.getParent().getRight();
				
				if (w.getColor()) {	//if w's color is red (i.e. true)
					w.setColor(false);	//false for black					//case 1
					leading.getParent().setColor(true); //true for red		//case 1
					LeftRotate(leading.getParent());								//case 1
					w = leading.getParent().getRight();							//case 1
				}
				if (!w.getLeft().getColor() && !w.getRight().getColor()) {	//if w's left and right child are both black
					w.setColor(true);												//case 2
					leading = leading.getParent();										//case 2
				}
				else if (!w.getRight().getColor()) {	//else if w's right child is black
					w.getLeft().setColor(false); //false for black
					w.setColor(true); //true for red
					RightRotate(w);
					w = leading.getParent().getRight();
				}
				else {
					w.setColor(leading.getParent().getColor());
					leading.getParent().setColor(false); //false for black
					w.getRight().setColor(false); //false for black
					LeftRotate(leading.getParent());
					leading = root;
				}
			}
			else { //same as "then" clause with left and right exchanged
				w = leading.getParent().getLeft();
				if (w.getColor()) {	//if w's color is red (i.e. true)
					w.setColor(false); //false for black					//case 1
					leading.getParent().setColor(true); //true for red		//case 1
					RightRotate(leading.getParent());								//case 1
					w = leading.getParent().getLeft();							//case 1
				}
				if (!w.getRight().getColor() && !w.getLeft().getColor()) { //if w's left and right child are both black
					w.setColor(true);												//case 2
					leading = leading.getParent();										//case 2
				}
				else if (!w.getLeft().getColor()) { //else if w's right child is black
					w.getRight().setColor(false); //false for black
					w.setColor(true); //true for red
					LeftRotate(w);
					w = leading.getParent().getLeft();
				}
				else {
					w.setColor(leading.getParent().getColor());
					leading.getParent().setColor(false); //false for black
					w.getLeft().setColor(false); //false for black
					RightRotate(leading.getParent());
					leading = root;
				}
			}//end if statement
		}//end while
		leading.setColor(false); //false for black		
	}

	/* Taken from the algorithm provided by the text; Cormen */
	private void RedBlackTransplant(RBTNode<T> u, RBTNode<T> v) {
		
		if (u.getParent() == nil)
			root = v;
		else if (u == u.getParent().getLeft())
			u.getParent().setLeft(v);
		else
			u.getParent().setRight(v);
		v.setParent(u.getParent());
	}
	
	/* Taken from the algorithm provided by the text; Cormen */
	//Finds a SetElement with key k and returns a pointer to it,
    //or null if not found.
	public RBTNode<T> search (T k) {
		RBTNode<T> currentnode = root;
		while (currentnode != nil && k.compareTo(currentnode.getKey()) != 0) {
			if (k.compareTo(currentnode.getKey()) < 0)
				currentnode = currentnode.getLeft();
			else
				currentnode = currentnode.getRight();
		}
		return currentnode;
	}
	
	//Finds a SetElement that has the smallest key, and returns a pointer to it,
    //or null if the set is empty.
	public RBTNode<T> minimum() {
		RBTNode<T> temp = root;
		while (temp.getLeft() != nil)
			temp = temp.getLeft();
		if (temp == nil)
			return null;
		else
			return temp;
	}
	
	private RBTNode<T> RBTreeMinimum(RBTNode<T> subtree) {
		RBTNode<T> temp = subtree;
		while (temp.getLeft() != null)
			temp = temp.getLeft();
			
		if (temp == nil)
			return null;
		else
			return temp;
	}
	
	//Finds a SetElement that has the largest key, and returns a pointer to it,
    //or null if the set is empty.
	public RBTNode<T> maximum() {
		RBTNode<T> temp = root;
		while (temp.getRight() != nil)
			temp = temp.getRight();
			
		if (temp == nil)
			return null;
		else
			return temp;
	}
	
	//Given a pointer to element e in the set, finds a SetElement in the set
    //that has the next larger key, and returns a pointer to it, or null if
    //e is the maximum element.
	public RBTNode<T> successor(SetElement<T> e) {
		RBTNode<T> suc = search(e.getKey());
		if (suc != null) {
			if (suc.getRight() != nil)
				return suc.getRight();
			else 
				return null;
		}
		else
			return null;
	}
	
	//Given a pointer to element e in the set, finds a SetElement in the set
    //that has the next smaller key, and returns a pointer to it, or null if
    //e is the minimum element.
	public RBTNode<T> predecessor(SetElement<T> e) {
		RBTNode<T> suc = search(e.getKey());
		if (suc != null) {
			if (suc.getLeft() != nil)
				return suc.getLeft();
			else 
				return null;
		}
		else
			return null;
	}
}
