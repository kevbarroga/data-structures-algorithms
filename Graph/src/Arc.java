
/*
 * Copyright (c) 2013, Kevin Barroga
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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that represents the edge object in a graph
 * @author Kevin Barroga
 *
 * @param <T>, the key
 * @param <E>, the value of the annotation
 */
public class Arc<T extends Comparable<T>, E> {
	protected T key;  // key for the edge
	protected Boolean directed; // whether the edge is directed or not
	protected HashMap<T, E> annotation;  // hashmap that will hold all the annotations/description list for the edge
	protected ArrayList<Vertex<T,E>> ends = new ArrayList<Vertex<T,E>>();  // array list that will hold the end vertices for the edge
	protected Integer weight;  // the weight of the edge
	
	// Constructors
	/**
	 * No-argument constructor
	 */
	public Arc() {
		super();
		this.key = null;
		annotation = new HashMap<T,E>();
		this.directed = false;
		weight = 0;
	}
	
	/**
	 * Two-argument constructor
	 * @param directed boolean, true if the edge is directed and false if undirected
	 * @param weight double, the weight of the edge
	 */
	public Arc(Boolean directed, Integer weight) {
		super();
		this.key = null;
		annotation = new HashMap<T,E>();
		this.directed = directed;
		this.weight = weight;
	}
	
	/**
	 * Returns true if the edge is directed and false if it is not
	 * @return boolean, whether the edge is directed or not
	 */
	public boolean isDirected() {
		return directed;
	}
	
	/**
	 * Returns the key of the edge
	 * @return T, the key of the edge
	 */
	public T getKey() {
		return this.key;
	}
	
	/**
	 * Returns the weight of the edge
	 * @return double, the weight of the edge
	 */
	public Integer getWeight() {
		return weight;
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
	 * Returns a pointer to a vertex that is the destination vertex of the directed edge, throws InvalidEdgeException if undirected
	 * @return pointer, to the destination vertex of the edge
	 * @throws InvalidEdgeException
	 */
	public Vertex<T,E> getDestination() throws InvalidEdgeException{
		if (this.isDirected()) {
			return ends.get(1);
		} else {
			throw new InvalidEdgeException("This edge is undirected.");
		}
	}
	
	/**
	 * Returns a pointer to a vertex that is the origin vertex of the directed edge, throws InvalidEdgeException if undirected
	 * @return pointer, to the origin vertex of the edge
	 * @throws InvalidEdgeException
	 */
	public Vertex<T,E> getOrigin() throws InvalidEdgeException {
		if(this.isDirected()){
			return ends.get(0);
		} else {
			throw new InvalidEdgeException("This edge is undirected.");
		}
	}
	
	/**
	 * Returns an array list of the end vertices of the edge
	 * @return ArrayList, the array list of the end vertices
	 */
	public ArrayList<Vertex<T,E>> getEnds() {
		return ends;
	}
	
	/**
	 * Sets the new key for the edge
	 * @param brother, the first part of the new key
	 * @param sister, the second part of the new key
	 */
	@SuppressWarnings("unchecked")
	public void setKey(T brother, T sister) {
		key = (T) (brother + " " + sister);
	}
	
	/**
	 * Adds vertices to the edge with the assumption that none had previously existed
	 * @param brother, the first end vertex
	 * @param sister, the second end vertex
	 */
	public void addEnds(Vertex<T,E> brother, Vertex<T,E> sister) {
		ends.add(brother);
	    ends.add(sister);
	    brother.adjacentVertices.add(sister);
	    sister.adjacentVertices.add(brother);
	    setKey(brother.getKey(), sister.getKey());
	}
	
	/**
	 * Deletes the end vertices of this edge and updates each vertices corresponding adjacency lists
	 */
	public void deleteEnds() {
		if (this.isDirected()) {  // if the edge is directed, first make it undirected, updating all corresponding directed adjacency lists
			this.makeUndirected();
		}
		ends.get(0).removeFromVertices(ends.get(0).adjacentVertices(), ends.get(1).getKey());
		ends.get(0).removeFromEdges(ends.get(0).incidentEdges(), this.getKey());
		ends.get(1).removeFromVertices(ends.get(1).adjacentVertices(), ends.get(0).getKey());
		ends.get(1).removeFromEdges(ends.get(1).incidentEdges(), this.getKey());
	}
	
	/**
	 * Sets the direction for the edge and make it directed
	 * @param brother, pointer to the new origin vertex
	 * @param sister, pointer to the new destination vertex
	 */
	public void setDirection (Vertex<T,E> brother, Vertex<T,E> sister)  {
		ends.set(0, brother);
		ends.set(1, sister);
		this.makeDirected(brother, sister);
	}

	/**
	 * Sets the direction of the edge to the argument vertex as long as it is already an end vertex, throws InvalidEdgeException otherwise
	 * @param newDestination, pointer to the new destination vertex for the edge
	 * @throws InvalidEdgeException
	 */
	public void setDirectionTo(Vertex<T,E> newDestination) throws InvalidEdgeException{
		if (newDestination.getKey().compareTo(ends.get(0).getKey()) == 0) {
			this.makeUndirected();  // first make the edge undirected to update corresponding directed edge adjacency lists
			setDirection(ends.get(1), newDestination);  // then set the new direction
		} else if (newDestination.getKey().compareTo(ends.get(1).getKey()) == 0) {
			this.makeUndirected();  
			setDirection(ends.get(0), newDestination);
		} else {
			throw new InvalidEdgeException ("The vertex is not one of the end vertices of this edge.");
		}
	}
	
	/**
	 * Sets the direction of the edge from the argument vertex as long as it is already an end vertex, throws InvalidEdgeException otherwise
	 * @param newOrigin, pointer to the new origin vertex
	 * @throws InvalidEdgeException
	 */
	public void setDirectionFrom(Vertex<T,E> newOrigin) throws InvalidEdgeException{
		if (newOrigin.getKey().compareTo(ends.get(0).getKey()) == 0) {
			this.makeUndirected();
			setDirection(newOrigin, ends.get(1));
		} else if (newOrigin.getKey().compareTo(ends.get(1).getKey()) == 0) {
			this.makeUndirected();
			setDirection(newOrigin, ends.get(0));
		} else {
			throw new InvalidEdgeException ("The vertex is not one of the end vertices of this edge.");
		}
	}
	
	/**
	 * Makes the edge directed and updates corresponding directed adjacency lists on the end vertices
	 * @param brother, pointer to the origin of the edge
	 * @param sister, pointer to the destination of the edge
	 */
	public void makeDirected(Vertex<T, E> brother, Vertex<T, E> sister) {
		brother.outVertices.add(sister);
		brother.outEdges.add(this);
		sister.inVertices.add(brother);
		sister.inEdges.add(this);
		directed = true;
		this.setKey(brother.getKey(), sister.getKey());
	}
	
	/**
	 * If the edge is directed, makes it undirected and updates all corresponding directed edge adjacency lists, does nothing if the edge is already undirected
	 */
	public void makeUndirected() {
		if (directed) {
			ends.get(0).removeFromVertices(ends.get(0).outVertices(), ends.get(1).getKey());
			ends.get(0).removeFromEdges(ends.get(0).outEdges(), key);
			ends.get(1).removeFromVertices(ends.get(1).inVertices(), ends.get(0).getKey());
			ends.get(1).removeFromEdges(ends.get(1).inEdges(), key);
			directed = false;
		}
	}

	/**
	 * Reverses the direction of the edge if it is directed, otherwise throws InvalidEdgeException
	 * @throws InvalidEdgeException
	 */
	public void reverseDirection() throws InvalidEdgeException{
		if (directed) {
			this.makeUndirected();
			setDirectionFrom(ends.get(1));
		} else {
			throw new InvalidEdgeException("This edge is undirected.");
		}
	}
}
