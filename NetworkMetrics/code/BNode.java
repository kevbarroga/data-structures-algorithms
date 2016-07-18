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
 * Creates a node for the binary-search tree.
 *
 * @param <Type> the generic type
 * @author Original Author Erika Nana
 * 
 */
public class BNode<Type>{
	
	/** The key. */
	private Type key = null;
	
	/** The left child. */
	private BNode<Type> leftChild = null;
	
	/** The right child. */
	private BNode<Type> rightChild = null;
	
	/** The parent. */
	private BNode<Type> parent = null;
	
	/**
	 * Instantiates a new b node.
	 *
	 * @param key the value of the node
	 */
	public BNode(Type key) {
		this.key = key;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return key.toString();
	}
	/**
	 * ********************* GETTERS *******************************************.
	 *
	 * @return the key
	 */
	public Type getKey() {
		return key;
	}
	
	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public BNode<Type> getParent() {
		return parent;
	}
	
	/**
	 * Gets the left child.
	 *
	 * @return the left child
	 */
	public BNode<Type> getLeftChild() {
		return leftChild;
	}
	
	/**
	 * Gets the right child.
	 *
	 * @return the right child
	 */
	public BNode<Type> getRightChild() {
		return rightChild;
	}
	
	/**
	 * ********************* SETTERS *******************************************.
	 *
	 * @param key the new key
	 */
	public void setKey(Type key) {
		this.key = key;
	}

	/**
	 * Sets the right child.
	 *
	 * @param right the new right child
	 */
	public void setRightChild(BNode<Type> right) {
		this.rightChild = right;
	}
	
	/**
	 * Sets the left child.
	 *
	 * @param left the new left child
	 */
	public void setLeftChild(BNode<Type> left) {
		this.leftChild = left;
	}
	
	/**
	 * Sets the parent.
	 *
	 * @param parent the new parent
	 */
	public void setParent(BNode<Type> parent) {
		this.parent = parent;
	}
}