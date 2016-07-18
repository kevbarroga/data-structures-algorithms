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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Parses .vna files and creates the corresponding graph.
 * This does no error checking on file names
 *  @author Derivative Author    Erika Nana
 * 
 */
public class VNAParser {
	/**
	 * Generate graph.
	 *
	 * @param fileName the file name of the graph to be generated
	 * @return the directed graph
	 */
	public static DirectedGraph generateGraph(String fileName) {
		boolean create = false;
		boolean tie = false;
		
		DirectedGraph graph = new DirectedGraph();
		File file = new File(fileName);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			//read file in line by line
			Scanner fileReader = new Scanner(br);
			fileReader.useDelimiter(Pattern.compile("[\\r\\n]+"));
			while(fileReader.hasNext()) {
				String next = fileReader.next();
				if (next.contains("*node") || next.contains("*Node")) {
					create = true;
					continue;
				}
				if (next.contains("*tie") || next.contains("*Tie")){
					create = false;
					tie = true;
					continue;
				}
				if (create) {
					if (!next.matches("^ID.*")) { //match beginning of the line
						String[] words = next.split("\\s+");
						graph.insertVertex(words[0]);
					}
				}
				if (tie) {
					if (!next.matches("from.*")) {
						//split by ALL the white space
						String[] info = next.split("\\s+");
						Vertex start = graph.getVertex(info[0]);
						Vertex end = graph.getVertex(info[1]);
						graph.insertArc(start, end);
					}
				}
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			return null;
		}
		return graph;
	}
}
