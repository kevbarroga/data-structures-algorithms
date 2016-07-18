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

public class RBTNode<T extends Comparable<T>> extends SetElement<T> implements Comparable<T> {

	private boolean color;
	
	private RBTNode<T> left;
	private RBTNode<T> right;
	private RBTNode<T> parent;
	
	public RBTNode(T newKey, T data) {
		
		super(newKey, data);
		this.left = null;
		this.right = null;
		this.parent = null;
		this.color = false;
	}
	
	public boolean getColor()
	{
		return this.color;
	}
	
	public RBTNode<T> getLeft()
	{
		return this.left;
	}
	
	public RBTNode<T> getRight()
	{
		return this.right;
	}
	
	public RBTNode<T> getParent()
	{
		return this.parent;
	}
	
	public void setColor(boolean color)
	{
		this.color = color;
	}
	
	public void setLeft(RBTNode<T> left)
	{
		this.left = left;
	}
	
	public void setRight(RBTNode<T> right)
	{
		this.right = right;
	}
	
	public void setParent(RBTNode<T> parent)
	{
		this.parent = parent;
	}
	
	public int compareTo(T k)
	{
		return super.key.compareTo(k);
	}
	
}
