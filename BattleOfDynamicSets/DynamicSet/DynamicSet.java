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

package DynamicSet;

public abstract class DynamicSet<T> {


	protected int size;
	
	//Create an instance of DynamicSet and initialize it with an empty set.
	public DynamicSet() {
		
		this.size = 0;
	}
	
	//Returns the number of elements currently in the set.
	public int size() {
		
		return this.size();	
	}
	
	//Insert element e in the set.
	public abstract void insert(SetElement<T> e);
	
	//Given a pointer to element e in set, removes e from set.
	public abstract void delete(SetElement<T> e);
	
	//Finds a SetElement with key 'k' and returns a pointer to it or null if not found
	public abstract SetElement<T> search(T k);
	
	//Finds a SetElement that has the smallest key, and returns a pointer to it, or null if the set is empty.
	public abstract SetElement<T> minimum();
	
	//Finds a SetElement that has the largest key, and returns a pointer to it, or null of the set is empty.
	public abstract SetElement<T> maximum();
	
	//Given a pointer to element e in the set, find a SetElement in the set that has the next larger key,
	//and returns a pointer to it, or null if e is the maximum element.
	public abstract SetElement<T> successor(SetElement<T> e);
	
	//Given a pointer to element e in the set, finds a SetElement in the set that has the next smaller key,
	//and returns a pointer to it, or null if e is the minimum element.
	public abstract SetElement<T> predecessor(SetElement<T> e);
	
}
