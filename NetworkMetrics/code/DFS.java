package code;
/*
 * 
 */

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Runs the DFS algorithm on a given graph.
 * @author Original Author Erika Nana
 * 
 */
public class DFS {
	
	/** The time. */
	static int time;
	
	/** The scc. */
	static long scc;
	
	/**
	 * Run DFS on a graph and returns the annotated graph.
	 *
	 * @param graph the graph
	 * @return the directed graph
	 */
	public static DirectedGraph runDFS(DirectedGraph graph) {
		Iterator<Vertex>iterator = graph.vertices();
		while (iterator.hasNext()) {
			Vertex next = iterator.next();
			graph.setAnnotation(next, "color", "white");
			graph.setAnnotation(next, "parent", null);
		}
		time = 0;
		iterator = graph.vertices();
		while (iterator.hasNext()) {
			Vertex next = iterator.next();
			if (graph.getAnnotation(next, "color").equals("white")) {
				DFSVisit(graph,next);
			}
		}
		return graph;
	}
	
	/**
	 * Helper method for DFS.
	 *
	 * @param graph the graph
	 * @param vertex the vertex to start at
	 */
	public static void DFSVisit(DirectedGraph graph, Vertex vertex) {
		time = time + 1;
		graph.setAnnotation(vertex, "discover", time);
		graph.setAnnotation(vertex, "color", "gray");
		Iterator<Vertex> iterator = graph.outAdjacentVertices(vertex);
		while (iterator.hasNext()) {
			Vertex next = iterator.next();
			if (graph.getAnnotation(next, "color").equals("white")) {
				graph.setAnnotation(next, "parent", vertex);
				DFSVisit(graph,next);
			}
		}
		graph.setAnnotation(vertex, "color", "black");
		time = time + 1;
		graph.setAnnotation(vertex, "finish", time);
	}
	
	/**
	 * Runs DFS but with SCC counter modifications
	 *
	 * @param graph the graph
	 * @return the annotated graph
	 */
	public static DirectedGraph runDFSWithSCC(DirectedGraph graph) {
		//get a list of the vertices ordered by finish time
		HashMap<Vertex, BTree<Arc>> collection = graph.getATree();
		List<Vertex> vertices = new ArrayList<Vertex>(collection.keySet());
		Collections.sort(vertices, new VertexComparable());
		for (Vertex vertex: vertices) {
			graph.setAnnotation(vertex, "color", "white");
			graph.setAnnotation(vertex, "parent", null);
		}
		time = 0;
		scc = 0;
		for (Vertex vertex: vertices) {
			if(graph.getAnnotation(vertex, "color").equals("white")) {
				scc = scc + 1;
				DFSVisitWithSCC(graph, vertex);
			}
		}
		return graph;
	}
	
	/**
	 * Helper method DFS Visit with SCC components implemented.
	 *
	 * @param graph the graph
	 * @param vertex the vertex
	 */
	public static void DFSVisitWithSCC(DirectedGraph graph, Vertex vertex) {
		time = time + 1;
		graph.setAnnotation(vertex, "discover", time);
		graph.setAnnotation(vertex, "color", "gray");
		graph.setAnnotation(vertex, "scc", scc);
		Iterator<Vertex> iterator = graph.outAdjacentVertices(vertex);
		while (iterator.hasNext()) {
			Vertex next = iterator.next();
			if (graph.getAnnotation(next, "color").equals("white")) {
				graph.setAnnotation(next, "parent", vertex);
				DFSVisitWithSCC(graph,next);
			}
		}
		graph.setAnnotation(vertex, "color", "black");
		time = time + 1;
		graph.setAnnotation(vertex, "finish", time);
	}
}
