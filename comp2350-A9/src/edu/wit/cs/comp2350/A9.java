package edu.wit.cs.comp2350;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/** Calculates the minimal spanning tree of a graph 
 * 
 * Wentworth Institute of Technology
 * COMP 2350
 * Assignment 9
 * 
 */

public class A9 {

	// TODO document this method
	public static Graph FindMST(Graph g) {
		// TODO implement this method
		
		ArrayList<Vertex> sorted = new ArrayList<>();
		ArrayList<Vertex> unsorted = new ArrayList<>(g.size());//I made two array lists to keep track of what we have traversed through.
																// You could say that I used these lists instead of using sets. Easier to handle.
		unsorted = g.getVerticesList(unsorted);
		sorted.add(unsorted.get(0));
		unsorted.remove(0);
		while(unsorted.size() > 0) // find min dist until we run out of points to connect to.
		{
			Vertex[] min = g.findMin(sorted, unsorted); 
			sorted.add(min[1]);
			unsorted.remove(min[1]);
			g.addEdge(min[0], min[1]); // Yesterday, I was having a lot of trouble because the dist mothod I implemented was not giving me the correct distance.
										// solved that by adding .0 to powers...
		}
		return g;	// return a graph with the same vertices as g and the MST edges
	}

	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/


	// reads in an undirected graph from a specific file formatted with one
	// x/y node coordinate per line:
	private static Graph InputGraph(String file1, double epsilon) {

		Graph g = new Graph(epsilon);
		try (Scanner f = new Scanner(new File(file1))) {
			while(f.hasNextDouble()) // each vertex listing
				g.addVertex(f.nextDouble(), f.nextDouble());
		} catch (IOException e) {
			System.err.println("Cannot open file " + file1 + ". Exiting.");
			System.exit(0);
		}

		return g;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String file1;

		System.out.printf("Enter <points file> <edge neighborhood>\n");
		System.out.printf("(e.g: points/small .5)\n");
		file1 = s.next();

		// read in vertices
		Graph g = InputGraph(file1, s.nextDouble());

		Graph mst = FindMST(g);
		s.close();

		System.out.printf("Weight of tree: %f\n", mst.getTotalEdgeWeight());
	}

}
