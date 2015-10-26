package ca.ubc.ece.cpen221.mp3.graph;

import java.util.*;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

/**
 * This class represents a graph as an adjacency matrix
 * 
 *
 */

public class AdjacencyMatrixGraph implements Graph {
    // TODO: Implement this class

    // Field that corresponds to the very first row of the overall matrix
    // First element is null; afterwards, each element represents a unique
    // vertex
    private List<Vertex> vertexColumns = new ArrayList<Vertex>();

    // This list holds vertexColumns, as well as additional rows that pertain to
    // the vertices.
    // Each row beyond the first (vertexColumns) represents a unique vertex .
    private List<List<Vertex>> vertices = new ArrayList<List<Vertex>>();

    // This field represents the adjacency matrix and contains only boolean
    // values.
    // Note that the overall matrix links adjMatrix and vertices together;
    // however, because we cannot have
    // a single list of lists of different types (i.e. Vertex and Boolean), they
    // must be implemented separately
    private List<List<Boolean>> adjMatrix = new ArrayList<List<Boolean>>();

    /**
     * Adds a vertex to the graph.
     *
     * Precondition: v is not already a vertex in the graph
     */
    public void addVertex(Vertex v) {
        // Declare new row to add to vertices
        List<Vertex> newVRow = new ArrayList<Vertex>();

        // Declare new row to add to adjMatrix
        List<Boolean> newBRow = new ArrayList<Boolean>();

        newVRow.add(v);
        newBRow.add(false);
        if (vertexColumns.isEmpty()) {
            // Only done once (when there are initially no vertices in the
            // graph)

            // first space in row of columns is null; following rows' first
            // space will represent vertices
            vertexColumns.add(null);
            vertexColumns.add(v);

            vertices.add(vertexColumns);
            vertices.add(newVRow);
            adjMatrix.add(newBRow);

            // After at least once vertex has been added to the graph, do the
            // following
        } else {
            vertexColumns.add(v);
            vertices.add(newVRow);

            // Adds a zero to the end of every existing boolean row to account
            // for the new vertex
            for (List<Boolean> BRow : adjMatrix) {
                BRow.add(false);
            }
            // Begins new row of booleans for added vertex (initially contains
            // false in first entry)
            adjMatrix.add(newBRow);
            // Fills in new row of booleans with zeroes
            for (int i = 0; i < vertexColumns.size() - 2; i++) {
                newBRow.add(false);
            }

        }

    }

    /**
     * Adds an edge that points from v1 to v2.
     *
     * Precondition: v1 and v2 are vertices in the graph
     */
    public void addEdge(Vertex v1, Vertex v2) {

        int whichRow = 0;
        int whichColumn = 0;

        // Find which row contains v1
        for (int i = 1; i < vertices.size(); i++) {
            if (vertices.get(i).contains(v1)) {
                whichRow = i;
                break;
            }
        }

        // Find which column contains v2
        for (int i = 1; i < vertexColumns.size(); i++) {
            if (vertexColumns.contains(v2)) {
                whichColumn = i;
                break;
            }
        }

        // Changes the boolean entry in the adjMatrix corresponding to these two
        // vertices to true
        // Note that each row / column value in adjMatrix corresponds to a row /
        // column in vertices
        // that is one value higher
        adjMatrix.get(whichRow - 1).set(whichColumn - 1, true);

    }

    /**
     * Check if there is an edge that points from v1 to v2.
     *
     * Precondition: v1 and v2 are vertices in the graph Postcondition: return
     * true iff an edge points from v1 connects to v2
     */
    public boolean edgeExists(Vertex v1, Vertex v2) {

        int whichRow = 0;
        int whichColumn = 0;

        // Find which row contains v1
        for (int i = 1; i < vertices.size(); i++) {
            if (vertices.get(i).contains(v1)) {
                whichRow = i;
                break;
            }
        }

        // Find which column contains v2
        for (int i = 1; i < vertexColumns.size(); i++) {
            if (vertexColumns.contains(v2)) {
                whichColumn = i;
                break;
            }
        }
        // Note that each row / column value in adjMatrix corresponds to a row /
        // column in vertices
        // that is one value higher
        return (adjMatrix.get(whichRow - 1).get(whichColumn - 1));

    }

    /**
     * Get an array containing all downstream vertices adjacent to v.
     *
     * Precondition: v is a vertex in the graph
     * 
     * Postcondition: returns a list containing each vertex w such that there is
     * an edge pointing from v to w. The size of the list must be as small as
     * possible (No trailing null elements). This method should return a list of
     * size 0 iff v has no downstream neighbors.
     */
    public List<Vertex> getDownstreamNeighbors(Vertex v) {
        List<Vertex> downstreamNeighborList = new ArrayList<Vertex>();
        List<Integer> columnsContainingOne = new ArrayList<Integer>();

        int whichRow = 0;

        // identify which row to examine
        for (int i = 1; i < vertices.size(); i++) {
            if (vertices.get(i).contains(v)) {
                whichRow = i;
                break;
            }
        }

        // loops through corresponding row in adjMatrix, checking if an entry
        // contains true.
        // if so, record the index corresponding to the vertex in vertexColumns
        // and add it to columnsContainingOne.
        for (int i = 0; i < adjMatrix.get(whichRow - 1).size(); i++) {
            if (adjMatrix.get(whichRow - 1).get(i) == true) {
                columnsContainingOne.add(i + 1);
            }
        }

        //
        for (Integer column : columnsContainingOne) {
            downstreamNeighborList.add(vertexColumns.get(column));
        }

        return downstreamNeighborList;
    }

    /**
     * Get an array containing all upstream vertices adjacent to v.
     *
     * Precondition: v is a vertex in the graph
     * 
     * Postcondition: returns a list containing each vertex u such that there is
     * an edge that points from u to v. The size of the list must be as small as
     * possible (No trailing null elements). This method should return a list of
     * size 0 iff v has no upstream neighbors.
     */
    public List<Vertex> getUpstreamNeighbors(Vertex v) {
        List<Vertex> upstreamNeighborList = new ArrayList<Vertex>();
        List<Integer> columnsContainingZero = new ArrayList<Integer>();

        int whichRow = 0;

        // identify which row to examine
        for (int i = 1; i < vertices.size(); i++) {
            if (vertices.get(i).contains(v)) {
                whichRow = i;
                break;
            }
        }

        // loops through corresponding row in adjMatrix, checking if an entry
        // contains false.
        // if so, record the index corresponding to the vertex in vertexColumns
        // and add it to columnsContainingZero.
        for (int i = 0; i < adjMatrix.get(whichRow - 1).size(); i++) {
            if (adjMatrix.get(whichRow - 1).get(i) == false) {
                columnsContainingZero.add(i + 1);
            }
        }

        for (Integer column : columnsContainingZero) {
            // Here, (column -1) corresponds to a -row- of the adjMatrix, and
            // (whichRow -1) corresponds to a -column- of the adjMatrix
            if (adjMatrix.get(column - 1).get(whichRow - 1)) {
                upstreamNeighborList.add(vertexColumns.get(column));
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

        // Retrieves each vertex and adds it to graphVertices
        for (int i = 1; i < vertexColumns.size(); i++) {
            graphVertices.add(vertexColumns.get(i));
        }

        return graphVertices;
    }
}
