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

import DynamicSet.*;

public class SLNode<T extends Comparable<T>> extends SetElement<T> implements Comparable<T> {
	
	private SLNode<T> above;
	private SLNode<T> below;
	private SLNode<T> next;
	private SLNode<T> prev;
	
	//initialize new node
	public SLNode(T key, T data) {
		
		super(key, data);
		above = null;
		below = null;
		next = null;
		prev = null;
	}
	
	public void setAbove(SLNode<T> e) {
		
		above = e;
	}
	
	public void setBelow(SLNode<T> e) {
		
		below = e;
	}
	
	public void setNext(SLNode<T> e) {
		
		next = e;
	}
	
	public void setPrev(SLNode<T> e) {
		
		prev = e;
	}
	
	public SLNode<T> getAbove()
	{
		return above;
	}
	
	public SLNode<T> getBelow()
	{
		return below;
	}
	
	public SLNode<T> getNext()
	{
		return next;
	}
	
	public SLNode<T> getPrev()
	{
		return prev;
	}
	
	//compareTo Method
	//This method will compare two DLL nodes and return negative if the referenced node,
	//is less than current, positive if the reference node is greater than current, or
	//0 is both nodes are equal
	public int compareTo(T node) {
		
		int result = super.key.compareTo(node);
		
		return result;
	}

}
