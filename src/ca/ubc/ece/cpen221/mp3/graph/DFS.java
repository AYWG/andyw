package ca.ubc.ece.cpen221.mp3.graph;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

import java.util.*;

public class DFS {
    private Map<Vertex, Boolean> markedVertex;
    private Set<List<Vertex>> vertexListSet;
    private List<Vertex> result;
    private Stack<Vertex> vertexStack;
    
    public DFS(Graph graph){
            vertexListSet = new HashSet<List<Vertex>>();
        
            for (Vertex v: graph.getVertices()){
            vertexStack = new Stack<Vertex>();
            result = new LinkedList<Vertex>();
            setAllFalse(graph);
            result.add(v);
            vertexStack.push(v);
            markedVertex.replace(v, true);
            
            while(!vertexStack.isEmpty()){
                Vertex n, child;
                n = (vertexStack.peek());
                child = getUnvisitedChildVertex(graph, n);
                if (child != null){
                    markedVertex.replace(child, true);
                    result.add(child);
                    vertexStack.push(child);
                }
                else{
                    vertexStack.pop();
               }
               }         
            vertexListSet.add(result);
            }
           }
            
        
    
    private void setAllFalse(Graph graph){
        markedVertex = new HashMap<Vertex, Boolean>();
        for (Vertex w: graph.getVertices()){
            markedVertex.put(w,false);
        }
    }
    
    public Set<List<Vertex>> getDFSset(){
        return vertexListSet;
    }
    
    private Vertex getUnvisitedChildVertex( Graph graph, Vertex v){
        for (int j = 0; j < graph.getDownstreamNeighbors(v).size(); j++){
           if (markedVertex.get(graph.getDownstreamNeighbors(v).get(j))!= true){
               return graph.getDownstreamNeighbors(v).get(j);
           }
        }
            return null;}
}