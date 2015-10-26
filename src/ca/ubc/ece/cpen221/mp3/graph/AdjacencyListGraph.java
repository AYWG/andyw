package ca.ubc.ece.cpen221.mp3.graph;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;
import java.util.*;

public class AdjacencyListGraph implements Graph {
    // TODO: Implement this class

    // Maps vertices to their adjacency lists
    private Map<Vertex, List<Vertex>> adjLists = new HashMap<Vertex, List<Vertex>>();

    /**
     * Adds a vertex to the graph.
     * 
     * Precondition: v is not already a vertex in the graph
     */
    public void addVertex(Vertex v) {
        // add vertex to map, along with an empty adjacency list
        adjLists.put(v, new ArrayList<Vertex>());

    }

    /**
     * Adds an edge that points from v1 to v2.
     *
     * Precondition: v1 and v2 are vertices in the graph
     */
    public void addEdge(Vertex v1, Vertex v2) {

        //Adds v2 to the adjacency list of v1
        adjLists.get(v1).add(v2);

    }

    /**
     * Check if there is an edge from v1 to v2.
     *
     * Precondition: v1 and v2 are vertices in the graph Postcondition: return
     * true iff an edge from v1 connects to v2
     */
    public boolean edgeExists(Vertex v1, Vertex v2) {

        // Check if v1's adjacency list contains v2
        if (adjLists.get(v1).contains(v2))
            return true;

        return false;
    }

    /**
     * Get an array containing all downstream vertices adjacent to v.
     *
     * Precondition: v is a vertex in the graph
     * 
     * Postcondition: returns a list containing each vertex w such that there is
     * an edge that points from v to w. The size of the list must be as small as possible
     * (No trailing null elements). This method should return a list of size 0
     * iff v has no downstream neighbors.
     */
    public List<Vertex> getDownstreamNeighbors(Vertex v) {
        List<Vertex> downstreamNeighborList = new ArrayList<Vertex>();

        // Adjacency list of v consist only of downstream neighbors
        for (Vertex ver : adjLists.get(v)) {
            downstreamNeighborList.add(ver);
        }

        return downstreamNeighborList;
    }

    /**
     * Get an array containing all upstream vertices adjacent to v.
     *
     * Precondition: v is a vertex in the graph
     * 
     * Postcondition: returns a list containing each vertex u such that there is
     * an edge that points from u to v. The size of the list must be as small as possible
     * (No trailing null elements). This method should return a list of size 0
     * iff v has no upstream neighbors.
     */
    public List<Vertex> getUpstreamNeighbors(Vertex v) {
        List<Vertex> upstreamNeighborList = new ArrayList<Vertex>();

        for (Vertex u : adjLists.keySet()) {
            if (adjLists.get(u).contains(v)) {
                upstreamNeighborList.add(u);
            }
        }

        return upstreamNeighborList;
    }

    /**
     * Get all vertices in the graph.
     *
     * Postcondition: returns a list containing all vertices in the graph. This
     * method should return a list of size 0 iff the graph has no vertices.
     */
    public List<Vertex> getVertices() {

        List<Vertex> graphVertices = new ArrayList<Vertex>();
        for (Vertex v : adjLists.keySet()) {
            graphVertices.add(v);
        }
        return graphVertices;

    }
}
