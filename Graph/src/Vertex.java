
/*
 * Copyright (c) 2011, Kevin Barroga
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Kevin Barroga nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Kevin Barroga ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Kevin Barroga BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class that represents the vertex object in a graph
 * @author Kevin Barroga
 *
 * @param <T>, the key
 * @param <E>, the value of the annotation
 */
public class Vertex<T extends Comparable<T>, E>{
	protected T key;  // key for the vertex
	protected HashMap<T, E> annotation; // hashmap that will hold all the annotations/description list for the vertex
	protected ArrayList<Vertex<T,E>> adjacentVertices = new ArrayList<Vertex<T,E>>();  // array list that will hold the pointers to the vertices adjacent to this vertex
	protected ArrayList<Vertex<T,E>> inVertices = new ArrayList<Vertex<T,E>>();  // array list that will hold the pointers to the vertices that are pointing to this vertex 
	protected ArrayList<Vertex<T,E>> outVertices = new ArrayList<Vertex<T,E>>(); // array list that will hold the pointers to the vertices that this vertex is pointing to
	protected ArrayList<Arc<T,E>> incidentEdges = new ArrayList<Arc<T,E>>();  // array list that holds all the edges associated with this vertex
	protected ArrayList<Arc<T,E>> inEdges = new ArrayList<Arc<T,E>>(); // array list that holds all the edges pointing to this vertex
	protected ArrayList<Arc<T,E>> outEdges = new ArrayList<Arc<T,E>>();  // array list that holds all the edges pointing out of this vertex
	
	// CONSTRUCTOR METHODS
	/**
	 * One-argument constructor
	 * @param key, the key for the vertex
	 */
	public Vertex(T key) {
		super();
		this.key = key;
		annotation = new HashMap<T,E>();
	}
	
	/**
	 * Two-argument constructor
	 * @param key, the key for the vertex
	 * @param annotation, a hashmap of annotations if they have already been created
	 */
	public Vertex(T key, HashMap<T,E> annotation) {
		super();
		this.key = key;
		this.annotation = annotation;
	}

	// ACCESSOR METHODS

	/**
	 * Returns the key for this vertex
	 * @return key for this vertex
	 */
	public T getKey() {
		return key;
	}

	/**
	 * Returns the object that corresponds to the key specified in the annotations hashmap
	 * @param key, to be found in the annotations hashmap
	 * @return value, that corresponds with the key, null if the key does not exist
	 */
	public E getAnnotation(T key) {
		return annotation.get(key);
	}
	
	/**
	 * Returns the in degree for this vertex
	 * @return integer, the number of edges coming into this vertex
	 */
	public Integer getInDeg() {
		return inEdges.size();
	}

	/**
	 * Returns the out degree for this vertex
	 * @return integer, the number of edges going out of this vertex
	 */
	public Integer getOutDeg() {
		return outEdges.size();
	}
	
	/**
	 * Returns the total degree for this vertex
	 * @return integer, the total number of directed edges incident to this vertex
	 */
	public Integer getDeg() {
		return inEdges.size() + outEdges.size();
	}

	/**
	 * Returns the number of incident edges
	 * @return integer, number of total incident edges 
	 */
	public Integer getNumIncidentEdges() {
		return inEdges.size() + outEdges.size();  // will add undirected edges when needed
	}

	/**
	 * Returns the number of adjacent vertices pointing to this vertex
	 * @return integer, the number of vertices pointing to this vertex
	 */
	public Integer getNumInAdjacentVertices() {
		return inVertices.size();
	}

	/**
	 * Returns the number of adjacent vertices this vertex is pointing to 
	 * @return integer, the number of vertices this vertex is pointing to
	 */
	public Integer getNumOutAdjacentVertices() {
		return outVertices.size();
	}

	/**
	 * Returns the number of total adjacent vertices
	 * @return integer, the total number of vertices adjacent to this vertex
	 */
	public Integer getNumAdjacentVertices() {
		return inVertices.size() + outVertices.size();
	}
	
	/**
	 * Returns a pointer to the vertex on the opposite end of the argument edge, throws an InvalidEdgeException if it is not an incident edge
	 * @param edge, pointer to the edge that connects this vertex to the opposite
	 * @return pointer to the vertex opposite the argument edge to this vertex
	 * @throws InvalidEdgeException
	 */
	public Vertex<T,E> getOpposite (Arc<T,E> edge) throws InvalidEdgeException{
		if (edge.getEnds().get(0).getKey().compareTo(this.getKey()) == 0) {
			return edge.getEnds().get(1);
		} else if (edge.getEnds().get(1).getKey().compareTo(this.getKey()) == 0) {
			return edge.getEnds().get(0);
		} else {
			throw new InvalidEdgeException(this.getKey() + " is not an endpoint of e.");
		}
	}
	
	/**
	 * Returns true if the argument vertex is adjacent to this vertex
	 * @param otherVertex, a pointer to the vertex to be determined to be adjacent to this vertex
	 * @return boolean, true if the argument vertex is adjacent
	 */
	public Boolean isAdjacentWith(Vertex<T,E> otherVertex){
		Vertex<T,E> search = null;
		Vertex<T,E> other = null;
 		if (this.getNumAdjacentVertices() >= otherVertex.getNumAdjacentVertices()) { // search through the shorter of the two adjacency lists
 			search = otherVertex;
 			other = this;
 		} else {
 			search = this;
 			other = otherVertex;
 		}
 		Iterator<Vertex<T,E>> itr = search.adjacentVertices();
 		while (itr.hasNext()) { // iterate through the adjacency list and return true if found
		    if (itr.next().getKey().compareTo(other.getKey()) == 0) {
		    	return true;
		    }
		}
 		return false;  // otherwise return false
	}
	
	// ITERATOR METHODS
	
	/**
	 * Returns an iterator to the adjacent vertices of this vertex
	 * @return iterator for the adjacent vertices list
	 */
	public Iterator<Vertex<T,E>> adjacentVertices() {
		return adjacentVertices.iterator();
	}
	
	/**
	 * Returns an iterator to the in vertices of this vertex
	 * @return iterator for the in vertices list
	 */
	public Iterator<Vertex<T,E>> inVertices() {
		return inVertices.iterator();
	}
	
	/**
	 * Returns an iterator to the out vertices of this vertex
	 * @return iterator for the out vertices list
	 */
	public Iterator<Vertex<T,E>> outVertices() {
		return outVertices.iterator();
	}
	
	/**
	 * Returns an iterator to the incident edges of this vertex
	 * @return iterator for the incident edges list
	 */
	public Iterator<Arc<T,E>> incidentEdges() {
		return incidentEdges.iterator();
	}
	
	/**
	 * Returns an iterator to the in edges of this vertex
	 * @return iterator for the in edges list
	 */
	public Iterator<Arc<T,E>> inEdges() {
		return inEdges.iterator();
	}
	
	/**
	 * Returns an iterator to the out edges of this vertex
	 * @return iterator for the out edges list
	 */
	public Iterator<Arc<T,E>> outEdges() {
		return outEdges.iterator();
	}
	
	// MUTATOR METHODS

	/**
	 * Sets a new annotation in the annotations hashmap
	 * @param key, the key for the annotation
	 * @param value, the value for the annotation
	 */
	public void setAnnotation(T key, E value) {
		annotation.put(key, value);
	}
	
	/**
	 * Removes an annotation from the annotations hashmap
	 * @param key, a key for the hashmap that corresponds to a value in the hashmap
	 * @return a value corresponding to the key in the annotations hashmap
	 */
	public E removeAnnotation(T key) {
		return annotation.remove(key);
	}

	/**
	 * Removes the argument corresponding vertex from the argument iterator
	 * @param iterator, iterator for the adjacency list to be modified
	 * @param remove, key for the vertex to remove
	 */
	public void removeFromVertices(Iterator<Vertex<T,E>> iterator, T remove) {
		Vertex<T,E> current = null;
		while (iterator.hasNext()) {
			current = iterator.next();
			if (current.getKey().compareTo(remove) == 0) {
				iterator.remove();
				break;
			}
		}
	}
	
	/**
	 * Removes the argument corresponding edge from the argument iterator
	 * @param iterator, iterator for the adjacency list to be modified
	 * @param remove, key for the edge to remove
	 */
	public void removeFromEdges(Iterator<Arc<T,E>> iterator, T remove) {
		Arc<T,E> current = null;
		while (iterator.hasNext()) {
			current = iterator.next();
			if (current.getKey().compareTo(remove) == 0) {
				iterator.remove();
				break;
			}
		}
	}	
}
