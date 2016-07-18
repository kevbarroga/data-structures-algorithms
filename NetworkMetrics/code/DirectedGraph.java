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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * ADT for a directed graph where V is a set of vertices ands A a set of directed
 * edges (arcs).
 * @author Original Author Erika Nana
 * @author Derivative Author Kevin Barroga
 */

public class DirectedGraph {

	/** The a list. */
	AList aList;

	/** Graph of vertices with a BTree to store the arcs */
	HashMap<Vertex,BTree<Arc>> map ; //since keys are strings
	
	/** Annotations */
	HashMap<Object,ArrayList<Object>> annotation;
	
	/**
	 * Instantiates a new directed graph.
	 */
	public DirectedGraph(){
		aList = new AList();
		map = new HashMap<Vertex,BTree<Arc>>();
	}
	
	/**
	 * Number of vertices in the graph.
	 *
	 * @return the number of vertices
	 */
	
	public int numVertices() {
		return aList.getNumOfVertices();
	}
	
	/**
	 * Number of arcs in the graph.
	 *
	 * @return the number of arcs
	 */
	public int numArcs() {
		return aList.getNumOfEdges();
	}
	
	/**
	 * Returns an iterator over the vertices V.
	 *
	 * @return the iterator
	 */
	public Iterator<Vertex> vertices() {
		return map.keySet().iterator();
	}
	
	/**
	 * Returns an iterator over the arcs A of G.
	 *
	 * @return the iterator
	 */
	public Iterator<Arc> arcs() {
		ArrayList<Arc> list = new ArrayList<Arc>();
		Iterator<Vertex> iterator = this.vertices();
		while (iterator.hasNext()) {
			Vertex vertex = iterator.next();
			BTree<Arc> tree = map.get(vertex);
			ArrayList<Arc> listOfArcs = tree.getListOfNodes();
			for(Arc arc: listOfArcs) {
				list.add(arc);
			}
		}
		return new ArcIterator(list);
	}
	

	/**
	 * Gets the vertex associated with String key.
	 *
	 * @param key the key of the vertex
	 * @return the vertex
	 */
	public Vertex getVertex(Object key) {
		Set<Vertex> keys = map.keySet();
		for (Iterator<Vertex> i = keys.iterator(); i.hasNext();) {
			Vertex currentKey = i.next();
			if (currentKey.getKey().equals(key.toString())){
				return currentKey;
			}
		}
		return null;
	}
	/**
    * Returns the arc that connects client keys source and target, or null if none.
    *
    * @param source the source vertex
    * @param target the target vertex
    * @return the arc
    */
    public Arc getArc(Object source, Object target) {
		Vertex start = getVertex(source.toString());
		Vertex end = getVertex(target.toString());
		if (start != null && end != null) {
			BTree<Arc> tree = map.get(start);
			//dummy: just using it to check variables
			Arc arc = new Arc(start,end);
			return (Arc) tree.searchForValue(arc);
		}
		return null;
	}
    
	/**
    * Returns the client data Object associated with vertex v.
    *
    * @param v the vertex
    * @return the vertex data
    */
    public Object getVertexData(Vertex v) {
		Vertex vertex = getVertex(v.toString());
		return vertex.getData();
	}
	/**
	 * Returns the client data Object associated with Arc a.
	 *
	 * @param a the Arc
	 * @return the arc data
	 */
    public Object getArcData(Arc a) {
		Arc arc = getArc(a.getStartVertex(), a.getEndVertex());
		return arc.getData();
	}
	
	/**
	 * Returns the number of arcs incoming to v.
	 *
	 * @param v the v
	 * @return the in degree
	 */
    public int inDegree(Vertex v) {
		return aList.getInDegree(v);
	}
	
	/**
	 * Returns the number of arcs outgoing from v.
	 *
	 * @param v the vertex
	 * @return the out degree
	 */
	public int outDegree(Vertex v) {
		return aList.getOutDegree(v);
	}
	/**
	 * Returns an iterator over the vertices adjacent to v by incoming arcs.
	 *
	 * @param v the vertex
	 * @return the iterator
	 */
	public Iterator<Vertex> inAdjacentVertices(Vertex v) {
		ArrayList<Vertex> inVertices = new ArrayList<Vertex>();
		//get an arrayList for the iterator
		Set<Vertex> keys = map.keySet();
		for (Iterator<Vertex> i = keys.iterator(); i.hasNext();) {
			Vertex currentKey = i.next();
			BTree<Arc> arcs = map.get(currentKey);
			ArrayList<Arc> list = arcs.getListOfNodes();
			for (Arc arc: list) {
				if (arc.getEndVertex() == v) {
					inVertices.add(currentKey);
				}
			}
		}
		return new VertexIterator(inVertices);
	}
	
	/**
	 * Returns an iterator over the vertices adjacent to v by outgoing arcs.
	 *
	 * @param v the vertex
	 * @return the iterator
	 */
	public Iterator<Vertex> outAdjacentVertices(Vertex v) {
		ArrayList<Vertex> outVertices = new ArrayList<Vertex>();
		Set<Vertex> keys = map.keySet();
		for (Iterator<Vertex> i = keys.iterator(); i.hasNext();) {
			Vertex currentKey = i.next();
			if (currentKey == v) {
				BTree<Arc> arcs = map.get(currentKey);
				ArrayList<Arc> list = arcs.getListOfNodes();
				for (Arc arc: list) {
					outVertices.add(arc.getEndVertex());
				}
				break;
			}
		}
		return new VertexIterator(outVertices);
	}

	/**
	 * Returns the origin (start) vertex of Arc a
	 *
	 * @param a the Arc
	 * @return the vertex
	 */
	public Vertex origin(Arc a) {
		return a.getStartVertex();
	}
	
	
	/**
	 * Returns the destination (end) vertex of Arc a
	 *
	 * @param a the Arc
	 * @return the vertex
	 */
	public Vertex destination(Arc a) {
		return a.getEndVertex();
	}
	
	/**
	 * Inserts a vertex into the graph.
	 *
	 * @param key the key of the vertex
	 * @return the vertex
	 */
	
	//can only store Strings as keys
	public Vertex insertVertex(Object key) {
		Vertex vertex = new Vertex((String) key);
		BTree<Arc> tree = new BTree<Arc>();
		map.put(vertex, tree);
		aList.addVertex(vertex);
		return vertex;
	}
	
	/**
	 * Inserts a new isolated vertex indexed under (retrievable via) the
	 * key and optionally containing an object data (which defaults to null)
	 * and returns the new Vertex
	 *
	 * @param key the key
	 * @param data the data
	 * @return the vertex
	 */
	public Vertex insertVertex(Object key, Object data) {
		Vertex vertex = new Vertex((String) key, data);
		BTree<Arc> tree = new BTree<Arc>();
		map.put(vertex, tree);
		aList.addVertex(vertex);
		return vertex;
	}	
	
	/**
	 * Insert an arc into the graph.
	 *
	 * @param u the start vertex of the Arc
	 * @param v the end vertex of the Arc
	 * @return the new Arc
	 */
	public Arc insertArc(Vertex u, Vertex v) {
		Arc arc = new Arc(u,v);
		BTree<Arc> tree = map.get(u);
		tree.insert(arc, null);
		map.put(u, tree);
		aList.addEdge(u, v);
		return arc;
	}
	
	/**
	 * Inserts a new arc from an existing vertex to another, 
	 * optionally containing an object data and returns the new Arc.
	 *
	 * @param u the start vertex of the Arc
	 * @param v the end vertex of the Arc
	 * @param data the data
	 * @return the new Arc
	 */
	public Arc insertArc(Vertex u, Vertex v, Object data) {
		Arc arc = new Arc(u,v,data);
		BTree<Arc> tree = map.get(u);
		tree.insert(arc, null);
		map.put(u, tree);
		aList.addEdge(u, v);
		return arc;
	}

	/**
	 * Changes the data Object associated with Vertex v to data.
	 *
	 * @param v the vertex
	 * @param data the data
	 */
	public void setVertexData(Vertex v, String data) {
		Vertex vertex = getVertex(v.toString());
		vertex.addData(data);
	}
	
	/**
	 * Changes the data Object associated with Arc a to data.
	 *
	 * @param a the Arc
	 * @param data the data
	 */
    	public void setArcData(Arc a, Object data) {
		Arc arc = getArc(a.getStartVertex(), a.getEndVertex());
		arc.setData(data);
	}
		
	/**
	 * Deletes a vertex and all its incident arcs.
	 * Returns the client data object formerly stored at v.
	 *
	 * @param v the vertex
	 * @return the object 
	 */
	public Object removeVertex(Vertex v) {
		if (map.containsKey(v)) {
			//remove from edges
			for (BTree<Arc> tree: map.values()) {
				if (!tree.isEmpty()) {
					//dummy arc
					Vertex dummy = new Vertex("dummy");
					Arc arc = new Arc(dummy,v);
					Arc foundArc = (Arc) tree.searchForValue(arc);
					if (foundArc != null) {
						tree.delete(foundArc);
						map.put(v, tree);
					}
				}
			}
			map.remove(v);
			//remove from aList
			aList.deleteVertex(v);
			return v;
		}
		System.out.println("The vertex you want to remove does not exist.");
		return null;
	}
	
	/**
	 * Removes the arc and returns the data object formerly stored at a.
	 *
	 * @param a the Arc
	 * @return the object
	 */
	public Object removeArc(Arc a) {
		Vertex key = a.getStartVertex();
		Set<Vertex> keys = map.keySet();
		for (Iterator<Vertex> i = keys.iterator(); i.hasNext();) {
			Vertex currentKey = i.next();
			if (Utils.compareValue(currentKey.toString(), key.toString()) == Utils.EQUAL){
				BTree<Arc> tree = map.get(currentKey);
				tree.delete(a);
				Vertex start = a.getStartVertex();
				Vertex end = a.getEndVertex();
				aList.deleteEdge(start, end);
				return a;
			}
		}
		System.out.println("The arc you want to remove does not exist");
		return null;
	}	
	
	/**
	 * Reverses the direction of the Arc.
	 *
	 * @param a the Arc
	 */
	public void reverseDirection(Arc a) {
		//delete and update
		Vertex start = a.getStartVertex();
		Vertex end = a.getEndVertex();
		//update aList
		aList.reverseEdge(start, end);
		
		//update BTrees
		BTree<Arc> tree = map.get(start);
		tree.delete(a);
		map.put(start, tree);
		tree = map.get(end);
		Object data = a.getData();
		Arc arc;
		arc = new Arc(end,start);
		if (a.getData() != null) {
			arc = new Arc(end, start,data);
		}
		else {
			arc = new Arc(end,start);
		}
		tree.insert(arc, null);
		map.put(end, tree);

	} 
	
	/**
	 * Annotates a vertex v with object o indexed by key k.
	 *
	 * @param v the vertex
	 * @param k the key
	 * @param o the object
	 */
	public void setAnnotation(Vertex v, Object k, Object o) {
		v.setAnnotation(k, o);
	} 
	
	/**
	 * Annotates an arc a with object o indexed by key k.
	 *
	 * @param a the arc
	 * @param k the key
	 * @param o the object
	 */
	public void setAnnotation(Arc a, Object k, Object o) {
		a.setAnnotation(k, o);
	} 
	
	/**
	 * Gets the annotation object indexed by key k.
	 *
	 * @param v the vertex
	 * @param k the key
	 * @return the annotation
	 */
	public Object getAnnotation(Vertex v, Object k) {
		return v.getAnnotation(k);
	} 
	
	/**
	 * Gets the annotation object indexed by key k.
	 *
	 * @param a the a
	 * @param k the k
	 * @return the annotation
	 */
	public Object getAnnotation(Arc a, Object k) {
		return a.getAnnotation(k);
	} 

	/**
	 * Removes the annotation from the vertex indexed by k and returns it.
	 *
	 * @param v the vertex
	 * @param k the key
	 * @return the object
	 */
	public Object removeAnnotation(Vertex v, Object k) {
		Object annotation = v.getAnnotation(k);
		v.removeAnnotation(k);
		return annotation;
	} 
	
	/**
	 * Removes the annotation from the arc indexed by k and returns it.
	 *
	 * @param a the a
	 * @param k the k
	 * @return the object
	 */
	public Object removeAnnotation(Arc a, Object k) {
		Object annotation = a.getAnnotation(k);
		a.removeAnnotation(k);
		return annotation;
	} 
	/**
	 * Removes all annotations on vertices or arcs indexed by k
	 *
	 * @param k the k
	 */
	public void clearAnnotations(Object k) {
		if (k instanceof Arc) {
			((Arc) k).clearAnnotations();
		}
		else if (k instanceof Vertex) {
			((Vertex) k).clearAnnotations();
		}
	} 

	/**
     * Class needed to get an iterator over Arcs.
     */
    class ArcIterator implements Iterator<Arc>{
		
		/** The current index. */
		int currentIndex = 0;
		
		/** The list. */
		ArrayList<Arc>list;
		
		/**
		 * Instantiates a new arc iterator.
		 *
		 * @param list the list
		 */
		public ArcIterator(ArrayList<Arc>list) {
			this.list = list;
		}
		
		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			if (currentIndex >= list.size()) {
				return false;
			}
			return true;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public Arc next() {
			return list.get(currentIndex++);
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			list.remove(--currentIndex);
		}
		
	}
	
	/**
	 * Class needed to get an iterator over vertices specified by the input list.
	 */
	class VertexIterator implements Iterator<Vertex>{
		
		/** The current index. */
		int currentIndex = 0;
		
		/** The list. */
		ArrayList<Vertex> list;
		
		/**
		 * Instantiates a new vertex iterator.
		 *
		 * @param list the list
		 */
		public VertexIterator(ArrayList<Vertex> list) {
			this.list = list;
		}
		
		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			if (currentIndex >= list.size()) {
				return false;
			}
			else {
				return true;
			}
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public Vertex next() {
			return list.get(currentIndex++);		
		}
		
		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			list.remove(--currentIndex);
		}
		
	}
	
	/**
	 * Gets the a list.
	 *
	 * @return the a list
	 */
	public AList getAList() {
		return aList;
	}
	
	/**
	 * Gets the a tree.
	 *
	 * @return the a tree
	 */
	public HashMap<Vertex,BTree<Arc>> getATree(){
		return map;
	}
		
	public double getReciprocity() {
		Set<Vertex> vertices = aList.outVertices.keySet();
		Iterator<Vertex> itrVert = vertices.iterator();
		int edgecount = 0;
		while (itrVert.hasNext()) {
			Vertex vertex = itrVert.next();
			edgecount = edgecount + getUndirectedDegree(vertex);
		}
		return (double) edgecount / aList.numOfEdges;
	}

	public int getUndirectedDegree(Vertex vertex) {
		return (Integer) vertex.getAnnotation("undirectedDegree");
	}
	
	public double getDegreeCorrelation() {
		//get the s1 summation
		int s1 = 0;
		Set<Vertex> vertices = aList.outVertices.keySet();
		Iterator<Vertex> itrVert = vertices.iterator();
		while (itrVert.hasNext()) {
			Vertex currentVert = itrVert.next();
			s1 = s1 + getUndirectedDegree(currentVert);
		}
		
		//get the s2 summation
		int s2 = 0;
		itrVert = vertices.iterator();
		while (itrVert.hasNext()) {
			Vertex currentVert = itrVert.next();
			s2 = (int) (s2 + Math.pow(getUndirectedDegree(currentVert), 2));
		}

		//get the s3 summation
		int s3 = 0;
		itrVert = vertices.iterator();
		while(itrVert.hasNext()) {
			Vertex currentVert = itrVert.next();
			s3 = (int) (s3 + Math.pow(getUndirectedDegree(currentVert), 3));
		}

		//get the se summation
		int se = 0;
		for (Arc edge: aList.undirectedEdges) {
			int startdeg = getUndirectedDegree(edge.getStartVertex());
			int enddeg = getUndirectedDegree(edge.getEndVertex());
			int product = startdeg * enddeg;
			se = se + product;
		}
		se = 2*se;

		//calculate r
		double num = (s1*se) - (Math.pow(s2, 2));
		double denom = ((s1 * s3) - (Math.pow(s2, 2)));
		return num/denom;
	}
	
	public double getClusteringCoefficient() {
		Set<Vertex> vertices = aList.outVertices.keySet();
		Iterator<Vertex> itrVert = vertices.iterator();
		int counter = 0;
		int pathsOfLengthTwo = 0;
		while (itrVert.hasNext()) {
			Vertex currentVert = itrVert.next();
			pathsOfLengthTwo = pathsOfLengthTwo + getUndirectedDegree(currentVert)/2;
			HashSet<Vertex> neighbors = aList.outVertices.get(currentVert);
			ArrayList<Vertex> endpoints = new ArrayList<Vertex>();
			endpoints.addAll(neighbors);
			//check for each neighbor j, if theres an edge for neighbor l > j
			int first;
			int second;
			for (int i = 0; i < endpoints.size(); i++) {
				first = i;
				second = i+1;
				//check if edge for neighbor l > j
				while (second < endpoints.size()) {
					//compare first and second
					Vertex secondVert = endpoints.get(second);
					Vertex firstVert = endpoints.get(first);
					if (aList.outVertices.get(secondVert).contains(firstVert) ||
							aList.outVertices.get(firstVert).contains(secondVert)) {
						//System.out.println(firstVertex + " is found in " + secondVertex);
						counter++;
					}
					second++;
				}
			}
		}
		//System.out.println("counter: "  + counter);
		pathsOfLengthTwo = 2 * pathsOfLengthTwo;
		double numberOfTriads = counter / 3;
		System.out.println("number of paths:  " + pathsOfLengthTwo);
		System.out.println("number of triads:  " + numberOfTriads);
		return numberOfTriads/pathsOfLengthTwo;
	}
	
}
