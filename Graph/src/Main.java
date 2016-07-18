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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InvalidEdgeException 
	 * @throws ProgramException 
	 */
	public static void main(String[] args) throws IOException, InvalidEdgeException, ProgramException {
		// Check if there is a single argument on the command line.
        if (args.length != 1) {
            System.err.println("Usage: expected only one argument");
            System.exit(1);
        }
		// Create graph object
		Graph<String, Object> myGraph = new Graph<String, Object>();
		// Open file to be read
		String file = new String(args[0]);  
		 
		StringTokenizer tokenizer;
	    BufferedReader br = null;
	    boolean isNode = true;
		 
	    try {	// read in file
	    	br = new BufferedReader(new FileReader(file));
			String line;
			line = br.readLine();	//read and skip *node data line
			//System.out.println(line);	
			line = br.readLine();	//read and skip ID line
			//System.out.println(line + "\n");
			
			//keep reading vertex data until arc data is reached
			while (isNode) {
				line = br.readLine();	// start of vertices
				tokenizer = new StringTokenizer(line, " ");
				//System.out.println(line);	
				String vertex;
				vertex = tokenizer.nextToken();
					
				if (vertex.equalsIgnoreCase("*tie")) {
					//System.out.println("EXIT");
					isNode = false;
				}
				else {	//insert vertex into graph object
					Vertex<String,Object> newVertex = new Vertex<String,Object>(vertex);
					myGraph.insertVertex(newVertex);
				}
				
			}
			
			//read the through the rest of the file for arc data
			line = br.readLine();	//skip from to strength line
			//System.out.println("\n" + line + "\n");
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				tokenizer = new StringTokenizer(line, " ");
				
				String from, to;
				from = tokenizer.nextToken();
				to = tokenizer.nextToken();
				
				//the first token will be the start vertex
				Vertex<String, Object> origin = myGraph.getVertex(from);
				//System.out.println("The origin is : " + origin.getKey());
				
				//the second token will be the second end vertex
				Vertex<String, Object> destination = myGraph.getVertex(to);
				//System.out.println("The destination is : " + destination.getKey());
				
				//the last token of the line will be the weight of the edge
				Integer weight = 0;
				//weight = Integer.parseInt(strength);
				
				//create new directed edge instance with weight
				Arc<String, Object> newEdge = new Arc<String, Object>(true, weight);
				//insert the new edge into the graph object with start and end vertex, and weight
				myGraph.insertDirectedEdge(origin, destination, newEdge);
				//System.out.println(origin.getKey() + "'s inDeg is " + origin.getInDeg());
        		//System.out.println(origin.getKey() + "'s outDeg is " + origin.getOutDeg());
			}
						
		} catch (final NoSuchElementException e) {
			//prints error if the string is invalid
			throw new ProgramException("Unexpected end of file " + args[0], e);
		}
	    br.close();
	    
	    //variables to hold min, max, total values
		Integer minOut = 0, maxOut = 0, minIn = 0, maxIn = 0, totalOut = 0, totalIn = 0;
		//get an iterator over all vertices
		Iterator<Vertex<String,Object>> itr= myGraph.vertices();
		while (itr.hasNext()){  // iterate through all the vertices in the graph, getting their stats
        	Vertex<String,Object> vertex = itr.next();
        	// adjust the minimums and maximums for the graph accordingly
        	minOut = (vertex.getOutDeg() < minOut) ? vertex.getOutDeg() : minOut; 
        	maxOut = (vertex.getOutDeg() > maxOut) ? vertex.getOutDeg() : maxOut;
        	totalOut += vertex.getOutDeg();   // increment the totals
        	minIn = (vertex.getInDeg() < minIn) ? vertex.getInDeg() : minIn;
        	maxIn = (vertex.getInDeg() > maxIn) ? vertex.getInDeg() : maxIn;
        	totalIn += vertex.getInDeg();
        }
		
		// instantiate a new double for the number of vertices to be used in the division to result in an average 
		Double totalVert = new Double(myGraph.numVertices());  
        Double averageIn = totalIn/totalVert;
        Double averageOut = totalOut/totalVert;
        // Print out the results of the graph statistics
        DecimalFormat threeDec = new DecimalFormat("#.###");
        System.out.println("--------------------------------------------------------");
        System.out.println("Graph " + args[0]);
        System.out.println("--------------------------------------------------------");
        System.out.println("|V| = " + myGraph.numVertices());
        System.out.println("|A| = " + myGraph.numEdges());
        System.out.println("Density = " + myGraph.density());
        System.out.println();
        System.out.println("Degree distribution:" + '\t' + "minimum" + '\t' + "average" + '\t' + "maximum");
    	System.out.println('\t' + "inDegree(v)" + '\t' + minIn + '\t' + threeDec.format(averageIn) + '\t' + maxIn);
    	System.out.println('\t' + "outDegree(v)" + '\t' + minOut + '\t' + threeDec.format(averageOut) + '\t' + maxOut);
    	
    	// get an iterator over all the vertices
    	Iterator<Vertex<String, Object>> itrVert = myGraph.vertices();  
    	myGraph.depthFS(itrVert);  // perform a DFS search on the graph, iterating from the first vertex added to the graph, annotating their discovery/finish times and adding them to a new list by finish time
    	myGraph.reverseGraph();  // reverse all the directed edges of the graph
    	myGraph.getAllSCC();  // obtain all the strongly connected components of the graph
    	System.out.println();
    	System.out.println("Number of Strongly Connected Components: " + myGraph.allSCC.size());
    	System.out.println("Number of Members in Largest Strongly Connected Component: " +myGraph.largestSCC());
    	System.out.println("Percent Vertices in Largest Strongly Connected Component: " + myGraph.largestSCCPercent());
    	System.out.println();
    	System.out.println("SCC: ");
    	System.out.println("ID\tRoot\tSize\tMembers");
    	
    	//System.out.println(myGraph.largestSCC());
    	int largest = 0;
    	for(int i = 0; i < myGraph.allSCC.size(); i++) {
    		if(largest < myGraph.allSCC.get(i).size()) {
    			largest = myGraph.allSCC.get(i).size();
    			}
    		}  
    	//System.out.println(largest);
    	
    	// sorts the array list of SCCs by list size first then if they are equal, by highest degree for a vertex among their lists
    	Collections.sort(myGraph.allSCC, new Comparator<Object>() {
			@SuppressWarnings("unchecked")
			public int compare(Object arg0, Object arg1) {
				ArrayList<Vertex<String,Object>> first = (ArrayList<Vertex<String, Object>>) arg0;
				ArrayList<Vertex<String,Object>> second = (ArrayList<Vertex<String, Object>>) arg1;
				if (first.size() < second.size()) {
					return 1;
				} else if (first.size() > second.size()) {
					return -1;
				} else {  // if they are equal the number of components, then sort next by highest degree number
					if (getHighestDegree(first) < getHighestDegree(second)) {
						return 1;
					} else if (getHighestDegree(first) > getHighestDegree(second)) {
						return -1;
					}
					return 0;
				}
			}
			
			/**
			 * 
			 * Returns the integer of the highest degree in the list sent to it
			 * @param first an array list of vertices 
			 * @return an integer that is the highest degree in that list
			 */
			private int getHighestDegree(ArrayList<Vertex<String, Object>> first) {
				Iterator<Vertex<String,Object>> itrVert = first.iterator();
				Integer highest = 0;
				Vertex<String,Object> current = null;
				while (itrVert.hasNext()) {
					current = itrVert.next();
					if (current.getDeg() > highest) {
						highest = current.getDeg();
					}
				}
				return highest;
			}
    	});
		
    	// get an iterator for the list of the lists of strongly connected components
    	Iterator<ArrayList<Vertex<String,Object>>> itrSCC = myGraph.allSCC.iterator();
    	ArrayList<Vertex<String,Object>> list = null;
    	int n = 1;  // integer that will be the numbering for the list and also the loop breaker for the print loop
    	while(itrSCC.hasNext()) {  // loop through and print the information for the SCCs
    		System.out.print(n+".\t");  // prints the number of the line
    		list = itrSCC.next();  // gets the next list from the array list of lists
    		System.out.print(list.get(0).getKey());  // prints out the root before sorting the list
    		Collections.sort(list, new Comparator<Object>() {  // sorts the list by the highest degreed vertices
    			@SuppressWarnings("unchecked")
    			public int compare(Object arg0, Object arg1) {
    				Vertex<String,Object> first = (Vertex<String, Object>) arg0;
    				Vertex<String,Object> second = (Vertex<String, Object>) arg1;
    				if (first.getDeg() < second.getDeg()) {
    					return 1;
    				} else if (first.getDeg() > second.getDeg()) {
    					return -1;
    				} else {
    					return 0;
    				}
    			}
        	});
    		System.out.print("\t" + list.size() + "\t");  // adds to the formatting for the output
    		Iterator<Vertex<String,Object>> itrList = list.iterator(); 
    		Vertex<String, Object> current = null;
    		current = itrList.next();
    		System.out.print(current.getKey());
    		while (itrList.hasNext()) {  // iterates over the list and prints out more keys if they exist 
    			current = itrList.next();
    			System.out.print(", " + current.getKey());
    		}
    		System.out.println();
    		n++;
    	}
    	
	}
}
