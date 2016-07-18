package code;

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
 * The Interface DynamicSet.  Simulates an ADT.
 *
 * @param <Type> the generic type
 * @author Original Author      Daniel Suthers
 * @author Derivative Author    Erika Nana
 * 
 */
public interface DynamicSet<Type> {
    
    /**
     * Size.
     *
     * @return The size of the set.
     */
  	public int size();

    /**
     * Inserts the element e in the set under key k.
     *
     * @param key The key to insert.
     * @param e The object to insert.
     */
    public void insert(Type key, Object e); 
    
    /**
     * Given a key k, removes elements indexed by k from the set.
     *
     * @param key The key to delete
     */
    public void delete(Type key); 

    /**
     * Finds an Object with key k and returns a pointer to it, or null if
     * not found.
     *
     * @param key The key to search.
     * @return The object with key k.
     */
    public Object search(Type key); 

    // The following operations apply when there is a total ordering on KeyType   

    /**
     * Finds an Object that has the smallest key, and returns a pointer to it,
     * or null if the set is empty.
     *
     * @return The minimum object in the set.
     */
    public Object minimum( ); 

    /**
     * Finds an Object that has the largest key, and returns a pointer to it, or null
     * if the set is empty.
     *
     * @return the Object with the largest key
     */
    public Object maximum( ); 

    /**
     * Finds an Object that has the next larger key in the set above k, 
     * and returns a pointer to it, or null if k is the maximum element.
     *
     * @param key The key of the node to find the successor of.
     * @return the Object that is the successor of that key.
     */
    public Object successor(Type key); 


    /**
     * Finds an Object that has the next smaller key in the set below k,
     * and returns a pointer to it, or null if k is the minimum element.
     *
     * @param key The key of the node to find the predecessor of.
     * @return the Object that is the predecessor of that key.
     */
    public Object predecessor(Type key); 

}
