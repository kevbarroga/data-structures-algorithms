
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
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class that represents the graph object
 * @author Kevin Barroga
 *
 * @param <T>, the key
 * @param <E>, the value of the annotation
 */
public class Graph<T extends Comparable<T>, E> {
	protected ArrayList<Vertex<T,E>> vertices = new ArrayList<Vertex<T,E>>();
	protected ArrayList<Arc<T,E>> edges = new ArrayList<Arc<T,E>>();
	protected ArrayList<Arc<T,E>> directedEdges = new ArrayList<Arc<T,E>>();
//	protected ArrayList<Edge<T,E>> undirectedEdges = new ArrayList<Edge<T,E>>();
	protected ArrayList<Vertex<T,E>> finished = new ArrayList<Vertex<T,E>>();
	protected ArrayList<Vertex<T,E>> temporary;
	protected ArrayList<ArrayList<Vertex<T,E>>> allSCC = new ArrayList<ArrayList<Vertex<T,E>>>();
	Integer time;
	protected Boolean findingSCC = false;
	
	// Constructors
	/**
	 * No-argument constructor
	 */
	public Graph() {
		super();
	}	

	/**
	 * Two-argument constructor
	 * @param vertices, an array of vertices
	 * @param directedEdges, an array of directed edges
	 */
	public Graph(ArrayList<Vertex<T,E>> vertices, ArrayList<Arc<T,E>> directedEdges) {  //, ArrayList<Arc<T,E>> undirectedEdges
		super();
		this.vertices = vertices;
		this.directedEdges = directedEdges;
//		this.undirectedEdges = undirectedEdges;
	}

	// Accessor Methods 
	
	/**
	 * Returns the number of vertices in the graph
	 * @return integer, the number of vertices in the graph
	 */
	public Integer numVertices() {
	    return vertices.size();
	}

	/**
	 * Returns the number of directed edges in the graph
	 * @return integer, the number of directed edges in the graph
	 */
	public Integer numEdges(){
	    return directedEdges.size();  // + undirectedEdges.size();
	}
	
	/**
	 * Returns the density of the graph
	 * @return double, density of the graph
	 */
	public Double density(){
		return (double)numEdges() / (numVertices() * (numVertices() - 1));
	}
	
	/**
	 * Returns the largest Strongly Connected Component
	 * @return integer, the largest Strongly Connected Component
	 */
	public Integer largestSCC(){
		int largest = 0;
    	for(int i = 0; i < allSCC.size(); i++) {
    		if(largest < allSCC.get(i).size()) {
    			largest = allSCC.get(i).size();
    			}
    		} 
    	return largest;
	}
	
	/**
	 * Returns the percent of vertices in the Largest Strongly Connected Component
	 * @return double, the percent of vertices in the Largest Strongly Connected Component
	 */
	public Double largestSCCPercent(){
		return 100 * (double)largestSCC() / numVertices();
	}

	/**
	 * Checks to see if the two parameter vertices are adjacent
	 * @param brother, first vertex to be compared
	 * @param sister, second vertex to be compared
	 * @return
	 */
	public boolean areAdjacent(Vertex<T,E> brother, Vertex<T,E> sister){
		Iterator<Vertex<T,E>> itr = brother.adjacentVertices();
		Vertex<T,E> current = null;
	    while (itr.hasNext()) {
	    	current = itr.next();
	    	if (current.getKey().compareTo(sister.getKey()) == 0) {
	    		return true;
	    	}
	    }
	    return false;
	}
	
	//	Accessing Directed Graphs
	
	/**
	 * Returns an iterator to the vertices list of this graph
	 * @return iterator for the vertices list
	 */
	public Iterator<Vertex<T,E>> vertices() {
		return vertices.iterator();
	}
	    
	/**
	 * Returns an iterator to the edges list of this graph
	 * @return iterator for the edges of this graph
	 */
	public Iterator<Arc<T,E>> edges(){
		return edges.iterator();
	}

	/**
	 * Returns an iterator to the directed edges list of this graph
	 * @return iterator for the edges list of this graph
	 */
	public Iterator<Arc<T,E>> directedEdges(){
	    return directedEdges.iterator();
	}
	
	/**
	 * Returns an iterator to the list produced by DFS of this graph
	 * @return iterator for the list produced by DFS of this graph
	 */
	public Iterator<Vertex<T,E>> finished() {
		return finished.iterator();
	}
	
//	undirectedEdges
//	public Iterator<Edge<T,E>> undirectedEdges(){
//	    return undirectedEdges.iterator();
//	}
	
	/**
	 * Returns a pointer to the vertex that corresponds to the argument key
	 * @param key, the key for the vertex to be found
	 * @return pointer to the vertex corresponding to the argument key
	 */
	public Vertex<T,E> getVertex (T key) {
		Vertex<T,E> found = null;
		Iterator<Vertex<T,E>> itr = this.vertices();
		while (itr.hasNext()) {
			found = itr.next();
			if (found.getKey().compareTo(key) == 0) {
				return found;
			}
		}
		return null;
	}
	
	//	Mutators (Undirected and Directed)
	
	/**
	 * Inserts a vertex into the graph and returns a pointer to it
	 * @param a pointer to the new vertex
	 * @return a pointer to the vertex inserted
	 */
	public Vertex<T,E> insertVertex(Vertex<T,E> newVertex){
	    vertices.add(newVertex);
	    return newVertex;
	}
	
	/**
	 * Inserts an edge into the graph and returns a pointer to it
	 * @param a pointer to the new edge
	 * @return a pointer to the edge inserted
	 */
	public Arc<T,E> insertEdge(Vertex<T,E> u, Vertex<T,E> v, Arc<T,E> newEdge) {
	    edges.add(newEdge);
	    newEdge.addEnds(u, v);
	    return newEdge;
	}
	
	/**
	 * Inserts a directed edge into the graph and returns a pointer to it
	 * @param a pointer to the new directed edge
	 * @return a pointer to the directed edge inserted
	 */
	public Arc<T,E> insertDirectedEdge(Vertex<T,E> u, Vertex<T,E> v, Arc<T,E> edge) throws InvalidEdgeException{
		Arc<T,E> newEdge = insertEdge(u, v, edge);
	    newEdge.setDirectionFrom(u);
	    directedEdges.add(newEdge);
	    return newEdge;
	}
	
	/**
	 * Removes a vertex from the graph and returns a pointer to it
	 * @param a pointer to the vertex to be removed
	 * @return a pointer to the vertex removed
	 */
	public Vertex<T,E> removeVertex(Vertex<T,E> v) throws InvalidEdgeException{
		removeVertex(vertices(), v);
	    Iterator<Arc<T,E>> itrEdge = v.incidentEdges();
	    Arc<T,E> edge = null;
	    while (itrEdge.hasNext()){
	    	edge = itrEdge.next();
	    	removeEdge(edge);
	    }
	    return v;
	}
	
	/**
	 * Removes the parameter vertex from an adjacency list specified by the parameter iterator
	 * @param itrVert the iterator for the adjacency list
	 * @param v the pointer to the vertex to be removed from the specified list
	 */
	private void removeVertex(Iterator<Vertex<T,E>> itrVert, Vertex<T,E> v) {
		Vertex<T,E> removedVertex = null;
    	while (itrVert.hasNext()) {
    		removedVertex = itrVert.next();
    		if (removedVertex.getKey().compareTo(v.getKey()) == 0) {
    			itrVert.remove();
    			break;
    		}
    	}
	}

	/**
	 * Removes the parameter edge from the edges list of this graph
	 * @param e the pointer to the edge to be removed from list
	 */
	public Arc<T,E> removeEdge(Arc<T,E> e) throws InvalidEdgeException {
		e.deleteEnds();
		removeEdge(edges(), e); 
		if (e.isDirected()) removeEdge(directedEdges(), e);
		return e;
	}

	/**
	 * Removes the parameter edge from an adjacency list specified by the parameter iterator
	 * @param itrEdge the iterator for the adjacency list
	 * @param e the pointer to the edge to be removed from the specified list
	 */
	private void removeEdge(Iterator<Arc<T,E>> itrEdge, Arc<T,E> e) {
		Arc<T,E> removedEdge = null;
		while(itrEdge.hasNext()) {
			removedEdge = itrEdge.next();
			if (e.getKey().compareTo(removedEdge.getKey()) == 0) {
				itrEdge.remove();
				break;
			}
		}
	}
	
	/**
	 * Reverses all the direction of all the directed edges in the graph, throws an InvalidEdgeException if the edge is not directed
	 * @throws InvalidEdgeException
	 */
	public void reverseGraph() throws InvalidEdgeException {
		Iterator<Arc<T,E>> itrEdges = directedEdges();
		Arc<T,E> currentEdge = null;
    	while (itrEdges.hasNext()) {
    		currentEdge = itrEdges.next();
    		currentEdge.reverseDirection();
    	}
	}
	
	/**
	 * Performs a depth first search on the list specified by the parameter iterator
	 * @param itrDFS, iterator that will iterate over the list
	 */
	@SuppressWarnings("unchecked")
	public void depthFS (Iterator<Vertex<T,E>> itrDFS) {
		finished.clear();  // first clears the adjacency list finished
		Vertex<T,E> current = null;
		Iterator<Vertex<T,E>> itrVert = vertices();
		while (itrVert.hasNext()) {  // iterates over all the vertices in the graph and annotates them to be unvisited
			current = itrVert.next();
			current.setAnnotation((T)"color", (E)"white");
			current.setAnnotation((T)"parent", (E)null);
		}
		time = 0;  // sets the timer to zero before visiting the vertices
		while (itrDFS.hasNext()) {
			current = itrDFS.next();  // goes to the first vertex in the list
			if (current.getAnnotation((T)"color").equals("white")) depthFSVisit(current); // performs the depth first visit on that vertex's adjacent vertices
			if (findingSCC){  // if this is the DFS to get all the strongly connected components, do these extra steps
		    	temporary = new ArrayList<Vertex<T,E>>(finished); // create a temporary array to hold this component
				if (temporary.size() != 0) allSCC.add(temporary);  // if this component is not empty, add it to the list of strongly connected components
				finished.clear();  // clear the finished array to be used again
			}
		}
	}

	/**
	 * Recursively visits all the adjacent vertices of the specified vertex
	 * @param current, a pointer to the vertex to be DFS
	 */
	@SuppressWarnings("unchecked")
	private void depthFSVisit(Vertex<T, E> current) {
		time++;  // increment the timer
		current.setAnnotation((T)"discovery", (E) time);  // sets the discovery time annotation on the vertex
		current.setAnnotation((T)"color", (E) "gray");  // sets the visited color annotation on the vertex to be tentatively visited (gray)
		Iterator<Vertex<T,E>> itrVert = current.outVertices();
		Vertex<T,E> nextCurrent = null;
		while (itrVert.hasNext()) {  // iterates over the out vertices of the current vertex
			nextCurrent = itrVert.next();
			if (nextCurrent.getAnnotation((T)"color").equals("white")) {  // if the out vertex is annotated white
				nextCurrent.setAnnotation((T)"parent", (E) current); // sets its parent as the current vertex
				depthFSVisit(nextCurrent); // then recursively visit its out vertices
			}
		}
		current.setAnnotation((T)"color", (E) "black"); // color this node as visited (black)
		time++; // increment the timer
		current.setAnnotation((T)"finishing",(E) time);  // annotate this node with the current time as the finishing time
		finished.add(0, current);  // add this vertex to the finished list
	}
	
	/**
	 * Obtains all the strongly connected components of this graph
	 */
	public void getAllSCC() { 
		ArrayList<Vertex<T,E>> firstFinished = new ArrayList<Vertex<T,E>>(finished); // copies all the list created by the first call to DFS
		Iterator<Vertex<T,E>> itrFirst = firstFinished.iterator();  // iterator over this finished list
		findingSCC = true;  // set to true to indicate the current step is to obtain all the SCCs of the graph
		depthFS(itrFirst);  // perform the second DFS on the finished list to obtain all the SCCs of the graph
		
	}
}
