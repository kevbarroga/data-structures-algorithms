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

public class DLLNode<T extends Comparable<T>> extends SetElement<T> implements Comparable<T> {
	
	private DLLNode<T> next;
	private DLLNode<T> prev;
	
	public DLLNode(T newKey, T data) {
		
		super(newKey, data);
		this.next = null;
		this.prev = null;
	}
	
	//returns next node in linked list
	public DLLNode<T> getNext() {
		
		return this.next;
	}
	
	//returns previous node in linked list
	public DLLNode<T> getPrev() {
		
		return this.prev;
	}
	
	//set next element for the referenced node
	public void setNext(DLLNode<T> next) {
		
		this.next = next;
	}
	
	public void setPrev(DLLNode<T> prev) {
		
		this.prev = prev;
	}
	
	//compareTo method that will compare two DLL nodes and return
	//negative if the referenced node is less than element, 
	//positive if the referenced node is greater than element,
	//or 0 if both nodes are equal
	public int compareTo(T element) {
		
		int result = super.key.compareTo(element);
		
		return result;
	}

}
