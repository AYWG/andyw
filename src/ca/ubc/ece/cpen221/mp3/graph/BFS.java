package ca.ubc.ece.cpen221.mp3.graph;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

import java.util.*;



public class BFS {
    private Map<Vertex, Boolean> markedVertex;
    private Map<Vertex, Integer> distTo;
    private Set<List<Vertex>> vertexListSet;
    private List<Vertex> result;
    private Queue<Vertex> vertexQueue;

    /*
     * Constructor
     * @requires graph
     * @effects constructs setlist for the result of the BFs to be stored in
     */

    
    public BFS(Graph graph){
        vertexQueue = new LinkedList<Vertex>();
        vertexListSet = new HashSet<List<Vertex>>();
        for (Vertex s : graph.getVertices()){
            BreadthFirstSearch(graph, s);
        }
    }
    
    /*
     * @requires graph and vertex
     * @effects conducts BTS and constructs a list the result to be stored in
     */
    
    public BFS(Graph graph, Vertex s){
        vertexListSet = new HashSet<List<Vertex>>();
        vertexQueue = new LinkedList<Vertex>();
        BreadthFirstSearch(graph, s);
    }
    
    
    /*
     * @requires graph and vertex
     * @effects conducts BFS and stores the result in a setlist
     * @effects stores all the distances the given vertex and the rest of the vertices
     */
    
    private void BreadthFirstSearch(Graph graph, Vertex s){
        setAllFalse(graph);
        result = new LinkedList<Vertex>();
        markedVertex.replace(s, true);
        vertexQueue.add(s);
        result.add(s);
        distTo = new HashMap<Vertex, Integer>();
        distTo.put(s, 0);
        while (!vertexQueue.isEmpty()){
            Vertex n, child;
            n = (vertexQueue.peek());
            child = getUnvisitedChildVertex(graph, n);
            if (child != null){
                markedVertex.replace(child, true);
                result.add(child);
                vertexQueue.add(child);
                distTo.put(child, distTo.get(n) + 1);
            }
            else{
                vertexQueue.poll();
            }
            
        }
        vertexListSet.add(result);
    }
    
    /*
     * @requires graph
     * @effects marks all vertexes to false to indicate that they have not been
     * reached
     * 
     */
 
 
    private void setAllFalse(Graph graph){
        markedVertex = new HashMap<Vertex, Boolean>();
        for (Vertex w: graph.getVertices()){
            markedVertex.put(w,false);
        }
    }
    
    /*
     * @requires graph and vertex
     * @effects returns an unreached vertex that is a downstreamneighbor of the given vertex
     * returns null if it does not have a downstreamneighbor or all neighbors have been searched
     */
    
    private Vertex getUnvisitedChildVertex( Graph graph, Vertex v){
        for (int j = 0; j < graph.getDownstreamNeighbors(v).size(); j++){
           if (markedVertex.get(graph.getDownstreamNeighbors(v).get(j))!= true){
               return graph.getDownstreamNeighbors(v).get(j);
           }
        }
            return null;}
    /*
     * @returns the result of the BFS
     */
    public Set<List<Vertex>> getBFSset(){
        return vertexListSet;
    }
    /*
     * @returns the distance between the given vertex and the vertex
     *          the search has been done on
     */
    public int getDistance(Vertex a){
        return distTo.get(a);
    }
    
   }