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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Computes all of the output required for graphs.
 *  @author Derivative Author    Erika Nana
 * 
 */
public class Metrics {
	
	/**
	 * Gets the density of the graph.
	 *
	 * @param graph the graph
	 * @return the density
	 */
	public static double getDensity(DirectedGraph graph) {
		double arcs = (double) graph.numArcs();
		int denominator = graph.numVertices() * (graph.numVertices() - 1);
		return arcs/denominator;
	}
	
	/**
	 * Gets the in degree statistics [min, max, ave].
	 *
	 * @param graph the graph
	 * @return the in degree statistics
	 */
	public static Object[] getInDegreeStats(DirectedGraph graph) {
		Object[] array = new Object [3];
		long min = 1000000000; //biggest long possible
		long max = 0;
		long runningTotal = 0;
		Iterator<Vertex> iterator = graph.vertices();
		while (iterator.hasNext()) {
			Vertex next = iterator.next();
			int inDegree = graph.inDegree(next);
			if (inDegree < min) {
				min = inDegree;
			}
			if (inDegree > max) {
				max = inDegree;
			}
			runningTotal = runningTotal + inDegree;
		}		
		
		array[0] = min;
		array[1] = max;
		array[2] = (double) runningTotal / graph.numVertices();
		return array;
	}
	/**
	 * Gets the out degree statistics [min, max, ave].
	 *
	 * @param graph the graph
	 * @return the out degree statistics
	 */
	public static Object[] getOutDegreeStats(DirectedGraph graph) {
		Object[] array = new Object[3];
		long min = 1000000000; //biggest long possible
		long max = 0;
		long runningTotal = 0;
		Iterator<Vertex> iterator = graph.vertices();
		while (iterator.hasNext()) {
			Vertex next = iterator.next();
			int outDegree = graph.outDegree(next);
			if (outDegree < min) {
				min = outDegree;
			}
			if (outDegree > max) {
				max = outDegree;
			}
			runningTotal = runningTotal + outDegree;
		}
		
		array[0] = min;
		array[1] = max;
		array[2] = (double) runningTotal / graph.numVertices();
		return array;
	}
	
	/**
	 * Creates the transpose of the graph.
	 *
	 * @param graph the graph
	 */
	public static void createTranspose(DirectedGraph graph) {
		Iterator<Arc> iterator = graph.arcs();
		while (iterator.hasNext()) {
			Arc next = iterator.next();
			graph.reverseDirection(next);
		}
	}
	/**
	 * Gets all SCC related output [number of SCC, percent 
	 * vertices in largest SCC, list of sorted SCC].
	 *
	 * @param graph the graph
	 * @return the object[]
	 */
	public static Object[] runSCC(DirectedGraph graph) {
		Object[] sCCStats = new Object[3];
		HashMap<Long, ArrayList<Vertex>> list = new HashMap<Long, ArrayList<Vertex>>();
		long scc = 0;
		long totalSCC;
		DirectedGraph newGraph = DFS.runDFS(graph);
		Metrics.createTranspose(newGraph);
		DirectedGraph sccGraph = DFS.runDFSWithSCC(newGraph);
		Iterator<Vertex> iterator = sccGraph.vertices();
		
		//create a hash of SCC to size
		while(iterator.hasNext()){
			Vertex vertex = iterator.next();
			long currentSCC = (Long) sccGraph.getAnnotation(vertex, "scc");
			if (currentSCC > scc) {
				scc = currentSCC;
			}
			if (list.containsKey(currentSCC)){
				ArrayList<Vertex> vertexList = list.get(currentSCC);
				vertexList.add(vertex);
			}
			else {
				ArrayList<Vertex> newList = new ArrayList<Vertex>();
				newList.add(vertex);
				list.put(currentSCC, newList);
			}
		}
		totalSCC = scc;
		
		//get the max SCC size
		Collection<ArrayList<Vertex>> listOfVertices = list.values();
		Iterator<ArrayList<Vertex>> listIterator = listOfVertices.iterator();
		ArrayList<Vertex> max = new ArrayList<Vertex>();
		while (listIterator.hasNext()) {
			 ArrayList<Vertex> nextList = listIterator.next();
			 if (nextList.size() > max.size()) {
				 max = nextList;
			 }
		}
		
		//create a list of sortedSCC
		List<ArrayList<Vertex>> vertices = new ArrayList<ArrayList<Vertex>>(list.values());
		Collections.sort(vertices, new VertexListComparable());
		
		sCCStats[0] = totalSCC;
		sCCStats[1] = ((double) max.size()/graph.numVertices()) * 100;
		sCCStats[2] = vertices;
		return sCCStats;
	}
}
