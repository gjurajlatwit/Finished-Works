package edu.wit.cs.comp2350;

import java.lang.Math;
/**
 * represents a vertex in a graph, including a unique ID to keep track of vertex
 *
 */
public class Vertex implements Comparable<Vertex> {



	/********************************************
	 * 
	 * You shouldn't modify anything past here
	 * 
	 ********************************************/

	public double x;
	public double y;
	public int ID;
	public Vertex[] c;
	public Vertex p;
	
	
	public double dist(Vertex e) // finds distance between vertices
	{
		return Math.pow(Math.pow(this.x - e.x, 2.0) + Math.pow(this.y - e.y, 2.0),0.5);
	}
	
	@Override
	public int compareTo(Vertex e) {
		if (dist(this) - dist(e) > 0)
			return 1;
		else if (dist(this) - dist(e) < 0)
			return -1;
		return 0;
	}
}
