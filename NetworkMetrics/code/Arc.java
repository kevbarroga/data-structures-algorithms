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
 * The Arc class
 * @author Original Author Erika Nana
 * @author Derivative Author Kevin Barroga
 */
public class Arc {

	/** The start vertex. */
	private Vertex startVertex;
	
	/** The end vertex. */
	private Vertex endVertex;
	
	/** Arc data */
	private Data dataList;
	
	/** Annotations */
	private HashMap<Object,Object> annotations;
	
	/**
	 * Instantiates a new arc.
	 *
	 * @param start the start vertex
	 * @param end the end vertex
	 */
	public Arc(Vertex start, Vertex end) {
		this.startVertex = start;
		this.endVertex = end;
		this.dataList = new Data();
		this.annotations = new HashMap<Object,Object>();
	}
	
	/**
	 * Instantiates a new arc with data.
	 *
	 * @param start the start vertex
	 * @param end the end vertex
	 * @param data the data
	 */
	public Arc(Vertex start, Vertex end, Object data) {
		this.startVertex = start;
		this.endVertex = end;
		if (dataList == null) {
			this.dataList = new Data();
		}
		this.dataList.addData((String)data);
		this.annotations = new HashMap<Object,Object>();
	}
	
	/**
	 * Gets the start vertex.
	 *
	 * @return the start vertex 
	 */
	public Vertex getStartVertex() {
		return startVertex;
	}
	
	/**
	 * Gets the end vertex.
	 *
	 * @return the end vertex
	 */
	public Vertex getEndVertex() {
		return endVertex;
	}
	
	/**
	 * Sets the start vertex.
	 *
	 * @param vertex the new start vertex
	 */
	public void setStartVertex(Vertex vertex) {
		this.startVertex = vertex;
	}
	
	/**
	 * Sets the end vertex.
	 *
	 * @param vertex the new end vertex
	 */
	public void setEndVertex(Vertex vertex) {
		this.endVertex = vertex;
	}
	
	/**
	 * Sets the data.
	 *
	 * @param data the new data of the arc
	 */
	public void setData(Object data) {
		if (dataList == null) {
			dataList = new Data();
		}
		this.dataList.addData((String) data);
	}
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Object getData() {
		return dataList.getData();
	}
	
	//overloaded to help with comparisons
	public String toString() {
		return getFullArc();
	}
	
	/**
	 * Gets the description of the arc.
	 *
	 * @return the description
	 */
	public String getFullArc() {
		return startVertex.toString() + " to " + endVertex.toString();
	}
	
	/**
	 * Sets the annotation.
	 *
	 * @param key the key for the annotation to be stored under
	 * @param value the actual annotation
	 */
	public void setAnnotation(Object key, Object value) {
		annotations.put(key, value);
	}
	
	/**
	 * Gets the annotation.
	 *
	 * @param annotation the key of the annotation
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
