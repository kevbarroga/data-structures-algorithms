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
import java.util.HashMap;

/**
 * The Vertex class.
 *  @author Derivative Author    Erika Nana
 *  
 */
public class Vertex {

	/** The key. */
	private String key;
	
	/** The data list. */
	private Data dataList;
	
	/** The annotations. */
	private HashMap<Object,Object> annotations;
	
	/**
	 * Instantiates a new vertex.
	 *
	 * @param key the key
	 */
	public Vertex(String key) {
		this.key = key;
		this.annotations = new HashMap<Object,Object>();
		this.dataList = new Data();
	}
	
	/**
	 * Instantiates a new vertex.
	 *
	 * @param key the key
	 * @param data the data
	 */
	public Vertex(String key, Object data) {
		this.key = key;
		if (dataList == null) {
			this.dataList = new Data();
		}
		this.dataList.addData((String) data);
		this.annotations = new HashMap<Object,Object>();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return key;
	}
	
	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Object getData() {
		return dataList.getData();
	}
	
	/**
	 * Adds the data.
	 *
	 * @param data the data
	 */
	public void addData(String data) {
		if (dataList == null) {
			dataList = new Data();
		}
		this.dataList.addData(data);
	}
	/**
	 * Sets the value.
	 *
	 * @param key the new value
	 */
	public void setValue(String key) {
		this.key = key;
	}
	
	/**
	 * Sets the annotation.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void setAnnotation(Object key, Object value) {
		annotations.put(key, value);
	}
	
	/**
	 * Gets the annotation.
	 *
	 * @param annotation the annotation
	 * @return the annotation
	 */
	public Object getAnnotation(Object annotation) {
		return annotations.get(annotation);
	}
	
	/**
	 * Removes the annotation.
	 *
	 * @param annotation the annotation
	 */
	public void removeAnnotation(Object annotation) {
		annotations.remove(annotation);
	}
	
	/**
	 * Clear annotations.
	 */
	public void clearAnnotations() {
		annotations.clear();
	}
}
