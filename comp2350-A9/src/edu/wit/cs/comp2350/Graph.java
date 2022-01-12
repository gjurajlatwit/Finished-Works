package edu.wit.cs.comp2350;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * represents a graph as a list of vertices and edges
 *
 */
public class Graph {



	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	private ArrayList<Vertex> vs;
	private ArrayList<Edge> edges;
	private double epsilon; 		// set to maximum edge distance
	private int nextVertexID = 0;	// unique ID of each vertex

	public Graph(double e) {
		vs = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		epsilon = e;
	}

	public void addVertex(double x, double y) {
		Vertex v = new Vertex();
		v.x = x; v.y = y; v.ID = nextVertexID++;
		vs.add(v);
	}

	// adds an edge to graph if it is within epsilon limit
	public void addEdge(Vertex src, Vertex dst) {
		if (dist(src, dst) < epsilon)
			edges.add(new Edge(src, dst, dist(src, dst)));
	}

	// finds the cartesian distance between two vertices... I see you already had this now, but I made a method that does the same thing in the vertices class
	//made more sense to me
	public static double dist(Vertex s, Vertex d) {
		return Math.sqrt(Math.pow(s.x-d.x, 2) + Math.pow(s.y-d.y, 2));
	}

	public int size() {
		return vs.size();
	}

	public Vertex[] getVertices() {
		return vs.toArray(new Vertex[vs.size()]);
	}
	public ArrayList<Vertex> getVerticesList(ArrayList<Vertex> unsorted) {
		for (int i = 0; i< vs.size(); i++) {
			unsorted.add(vs.get(i));
		}
		return unsorted;
	}

	public Edge[] getEdges() {
		return edges.toArray(new Edge[edges.size()]);
	}

	// sums up the costs of all edges in the graph
	public double getTotalEdgeWeight() {
		double ret = 0;
		for (Edge e: edges)
			ret += e.cost;
		return ret;
	}

	public double getEpsilon() {
		return this.epsilon;		
	}
	public Vertex getVertex(int i) 
	{ // get a vertex at a specific index in the graph's array, didn't need to use it in the end
		return vs.get(i);
	}
	
	public Vertex[] findMin(ArrayList<Vertex> sorted, ArrayList<Vertex> unsorted) // find min distance between all vertices crossed to and those we haven't yet
																				  //I used Prim's logic for this algorithm
	{
		Vertex[] min = new Vertex[2];
				min[0] = sorted.get(0);
				min[1] = unsorted.get(0);
		for(int i=0; i<sorted.size();i++)
		{
			for(int j = 0; j<unsorted.size();j++)
			{
				if (sorted.get(i).dist(unsorted.get(j)) < min[0].dist(min[1]) && sorted.get(i).dist(unsorted.get(j))<epsilon) 
				{
					min[0] = sorted.get(i);
					min[1] = unsorted.get(j);
				}
			}
		}
		return min;
	}
}
