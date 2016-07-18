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
import java.util.Scanner;

/**
 * Driver that allows the user to analyze the various graphs.
 * @author Original Author Erika Nana
 * @author Derivative Author Kevin Barroga
 */
public class Driver {

	/** The input reader. */
	static Scanner inputReader = new Scanner(System.in);
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		DirectedGraph graph;
		while (true) {
			System.out.println("What would you like to do?");
			System.out.println("Options available: testGraph, quit");
			String choice = inputReader.nextLine();
			if (!Utils.isEnum(choice)) {
				System.out.println("That command is not recognized\n");
				continue;
			}
			if (Utils.Command.valueOf(choice).equals(Utils.Command.quit)) {
				graph = null;
				System.out.println("Goodbye!");
				break;
			}
			else {
				System.out.println("Type in the name of the graph:  ");
				String fileName = inputReader.nextLine();
				graph = VNAParser.generateGraph(fileName);
				if (graph == null) {
					System.out.println("File not found!  Please try again\n");
					continue;
				}
				if (Utils.Command.valueOf(choice).equals(Utils.Command.testGraph)) {
					Utils.printTable(graph, fileName);
					System.out.println("\n");
					graph = null;
				}
			}
		}
	}
}
