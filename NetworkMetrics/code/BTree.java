package code;
/*
 * 
 */

/*
 * Copyright (c) 2013, Erika Nana
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Project 1 nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Erika Nana ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Erika Nana BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * Operations for manipulating a binary search tree.
 * Based on the psuedocode found in Introduction to Algorithms Third Edition
 * by Cormen et al.
 *
 * @param <Type> the generic type
 * @author Original Author Erika Nana
 *
 */
import java.util.ArrayList;

public class BTree<Type> implements DynamicSet<Type>{
	
	/** The size. */
	private int size = 0;
	
	/** The root. */
	private BNode<Type> root = null;
	
	/** The empty node to return; handles null pointer exceptions. */
	private BNode<Type> empty = null;
	
	/*Used for the iterators*/
	/** The list. */
	private ArrayList<Type> list;
	/**
	 * Instantiates a new b tree.
	 */
	public BTree() {
		empty = new BNode<Type>(null); //for null pointer
		list = new ArrayList<Type>();
	}
	
	/**
	 * Checks if is empty.
	 *
	 * @return True, if is empty
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * Gets the root.
	 *
	 * @return The root
	 */
	public BNode<Type> getRoot(){
		return root;
	}
	
	/**
	 * Inorder tree walk.
	 *
	 * @param node The node to start the walk from
	 */
	public void inorderTreeWalk(BNode<Type> node) {
		if (node != null) {
			inorderTreeWalk(node.getLeftChild());
			System.out.println(node.getKey());
			inorderTreeWalk(node.getRightChild());
		}
	}
	
	/* (non-Javadoc)
	 * @see main.DynamicSet#size()
	 */
	
	public int size() {
		return size;
	}
	
	/* (non-Javadoc)
	 * @see main.DynamicSet#insert(java.lang.Object, java.lang.Object)
	 */
	
	public void insert(Type key, Object e) {
		BNode<Type> node = new BNode<Type>(key);
		node.setKey(key);
		BNode<Type> y = null;
		BNode<Type> x = this.root;
		
		while (x != null) {
			y = x;
			if (Utils.compareValue(key.toString(), x.getKey().toString()) == Utils.LESSER) {
				x = x.getLeftChild();
			}
			else {
				x = x.getRightChild();
			}
		}
		node.setParent(y);
		if (y == null) {
			this.root = node; //tree is empty to begin with
		}
		else if (Utils.compareValue(node.getKey().toString(), y.getKey().toString()) == Utils.LESSER) {
			y.setLeftChild(node);
		}
		else {
			y.setRightChild(node);
		}
		list.add(key);
		size++;
	}

	/* (non-Javadoc)
	 * @see main.DynamicSet#delete(java.lang.Object)
	 */
	
	public void delete(Type key) {
		//search for the key to delete
		@SuppressWarnings("unchecked")
		BNode<Type> node = (BNode<Type>) this.search(key);
		if (node == null) {
			System.out.println("Can't delete from a node that doesn't exist!");
			return;
		}
		if (node.getLeftChild() == null) {
			transplant(node, node.getRightChild());
		}
		else if (node.getRightChild() == null) {
			transplant(node, node.getLeftChild());
		}
		else  {
/*			System.out.println("in else");*/
			BNode<Type>y = minimum(node.getRightChild());
			if (y.getParent() != node) {
				transplant(y,y.getRightChild());
				y.setRightChild(node.getRightChild());
				y.getRightChild().setParent(y);
			}
			transplant(node,y);
			y.setLeftChild(node.getLeftChild());
			y.getLeftChild().setParent(y);
		}
		list.remove(key);
		size--;
	}

	/* (non-Javadoc)
	 * @see main.DynamicSet#search(java.lang.Object)
	 */
	
	public Object search(Type key) {
		//matches at the root
		BNode<Type> current = root;
		while (true) {
			int compare = Utils.compareValue(key.toString(), current.getKey().toString());
			if (compare == Utils.LESSER) {
				current = current.getLeftChild();
				if (current == null) {
					//System.out.println("Not in the tree");
					return null;
				}
			}
			else if (compare == Utils.GREATER) {
				current = current.getRightChild();
				if (current == null) {
					//System.out.println("Not in the tree");
					return null;
				}
			}
			else {
				return current;
			}
		}
	}
	/*
	 * Instead of returning the node it returns the actual thing in the node
	 * */
	/**
	 * Search for value.
	 *
	 * @param key the key
	 * @return the object
	 */
	public Object searchForValue(Type key) {
		//matches at the root
		BNode<Type> current = root;
		
		while (true) {
			int compare = Utils.compareValue(key.toString(), current.getKey().toString());
			if (compare == Utils.LESSER) {
				current = current.getLeftChild();
				if (current == null) {
					//System.out.println("Not in the tree");
					return null;
				}
			}
			else if (compare == Utils.GREATER) {
				current = current.getRightChild();
				if (current == null) {
					//System.out.println("Not in the tree");
					return null;
				}
			}
			else {
				return current.getKey();
			}
		}
	}
	/*Gets the minimum of the whole tree*/
	/* (non-Javadoc)
	 * @see main.DynamicSet#minimum()
	 */
	
	public Object minimum() {
		BNode<Type> temp = root;
		if (root == null) {
			return null;
		}
		else {
			while (temp.getLeftChild() != null) {
				temp = temp.getLeftChild();
			}
			return temp;
		}
	}

	/* (non-Javadoc)
	 * @see main.DynamicSet#maximum()
	 */
	
	public Object maximum() {
		BNode<Type> temp = root;
		if (root == null) {
			return null;
		}
		else {
			while(temp.getRightChild() != null) {
				temp = temp.getRightChild();
			}
			return temp;
		}
	}

	/* (non-Javadoc)
	 * @see main.DynamicSet#successor(java.lang.Object)
	 */
	
	public Object successor(Type key) {
		@SuppressWarnings("unchecked")
		//search the tree for this node
		BNode<Type> node = (BNode<Type>) this.search(key);
		BNode<Type> successor = null;
		
		if (node == null){
			System.out.println("The node doesn't exist!");
			return null;
		}
		if (node.getRightChild() != null) {
			BNode<Type> minimum = minimum(node.getRightChild());
			return minimum;
		}
		else {
			successor = node.getParent();
			while (successor != null && node == successor.getRightChild()) {
				node = successor;
				successor = successor.getParent();
			}
			if (successor == null) {
				return empty;
			}
		}
		return successor;
	}

	/* (non-Javadoc)
	 * @see main.DynamicSet#predecessor(java.lang.Object)
	 */
	
	public Object predecessor(Type key) {
		@SuppressWarnings("unchecked")
		BNode<Type> node = (BNode<Type>) this.search(key);
		BNode<Type> predecessor = null;
		
		if (node == null){
			System.out.println("The node doesn't exist!");
			return null;
		}
		if (node.getLeftChild() != null) {
			BNode<Type> maximum = maximum(node.getLeftChild());
			if (maximum == null) {
				return empty;
			}
			return maximum;
		}
		else {
			predecessor = node.getParent();
			while(predecessor != null && node == predecessor.getLeftChild()) {
				node = predecessor;
				predecessor = predecessor.getParent();
			}
			//handle null pointer
			if (predecessor == null) {
				return empty;
			}
			return predecessor;
		}
	}
	

	/**
	 * Gets the minimum of the tree.
	 *
	 * @param node The root of the tree
	 * @return The minimum node of the tree
	 */
	public BNode<Type> minimum(BNode<Type> node){
		while (node.getLeftChild() != null) {
			node = node.getLeftChild();
		}
		return node;
	}
	
	/**
	 * Gets the maximum of the tree.
	 *
	 * @param node The root of the tree
	 * @return The maximum node in the tree
	 */
	public BNode<Type> maximum(BNode<Type> node){
		while (node.getRightChild() != null) {
			node = node.getRightChild();
		}
		return node;
	}
	
	/**
	 * Replaces one subtree as a child of its parent with another subtree.
	 *
	 * @param u The root of a tree
	 * @param v The root of another tree
	 */
	public void transplant(BNode<Type> u, BNode<Type> v) {
		if (u.getParent() == null) //u is the root of its subtree
				setRoot(v);
		else if (u == u.getParent().getLeftChild())
			u.getParent().setLeftChild(v);
		else u.getParent().setRightChild(v);
		if (v != null)
			v.setParent(u.getParent());
	}

	/**
	 * Sets the root.
	 *
	 * @param node the new root
	 */
	public void setRoot(BNode<Type> node) {
		root = node;
	}
	
	/**
	 * Gets the list of nodes.
	 *
	 * @return the list of nodes
	 */
	public ArrayList<Type> getListOfNodes(){
		return list;
	}
}
