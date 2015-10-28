package ca.ubc.ece.cpen221.mp3.graph;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

import java.util.*;

public class Algorithms { 
	/**
	 * *********************** Algorithms ****************************
	 * 
	 * Please see the README for the machine problem for a more detailed
	 * specification of the behavior of each method that one should implement.
	 */

	/**
	 * 
	 * @param graph
	 * @param a
	 * @param b
	 * @return The shortest distance between Vertex a and b. If no distance is found then
	 *         it returns -1
	 */
	public static int shortestDistance(Graph graph, Vertex a, Vertex b) {
	    BFS BFS = new BFS(graph, a);
	    for (List<Vertex> list : BFS.getBFSset()){
	        if (!(list.contains(a) && list.contains(b))){
	            return -1;
	        }
	    }
	        return BFS.getDistance(b);
	}
	
	/*
	 * @param graph
	 * @param Vertex a
	 * @param Vertex b
	 * 
	 * @return List of common upstream vertices that has edges that go from the vertex to 
	 *         vertex a and vertex b. If no vertices are found then it returns an empty list
	 */
	
	public static LinkedList<Vertex> commonUpstreamVertices(Graph graph, Vertex a, Vertex b){
	    LinkedList<Vertex> commonUpstreamList = new LinkedList<Vertex>();
	    for (Vertex v : graph.getVertices()){
	        if (graph.getDownstreamNeighbors(v).contains(a) && graph.getDownstreamNeighbors(v).contains(b)){
	            commonUpstreamList.add(v);
	        }
	    }
	    return commonUpstreamList;
	}

	/*
     * @param graph
     * @param Vertex a
     * @param Vertex b
     * 
     * @return List of down upstream vertices that has edges that go from the vertex to 
     *         vertex a and vertex b. If no vertices are found then it returns an empty list
     */

    public static LinkedList<Vertex> commonDownstreamVertices(Graph graph, Vertex a, Vertex b){
        LinkedList<Vertex> commonDownstreamList = new LinkedList<Vertex>();
        for (Vertex v: graph.getVertices()){
            if (graph.getUpstreamNeighbors(v).contains(a) && graph.getUpstreamNeighbors(v).contains(b)){
                commonDownstreamList.add(v);
            } 
        }
        return commonDownstreamList;
    }
    
}