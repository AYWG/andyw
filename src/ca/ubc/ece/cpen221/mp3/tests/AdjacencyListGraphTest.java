package ca.ubc.ece.cpen221.mp3.tests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import ca.ubc.ece.cpen221.mp3.graph.*;
import ca.ubc.ece.cpen221.mp3.staff.*;

public class AdjacencyListGraphTest {
    
    AdjacencyListGraph testAdjList; 
    List <Vertex> testVerticesList;
    Vertex testVertexA = new Vertex("A");
    Vertex testVertexB = new Vertex("B");
    Vertex testVertexC = new Vertex("C");
    Vertex testVertexD = new Vertex("D");
    Vertex testVertexE = new Vertex("E");
    Vertex testVertexF = new Vertex("F");
    Vertex testVertexG = new Vertex("G");

    @Test
    public void GraphWithThreeVerticesNoEdges() {
        testAdjList = new AdjacencyListGraph();
        testVerticesList = new ArrayList<Vertex>();
        testVerticesList.add(testVertexA);
        testVerticesList.add(testVertexB);
        testVerticesList.add(testVertexC);
        testAdjList.addVertex(testVertexA);
        testAdjList.addVertex(testVertexB);
        testAdjList.addVertex(testVertexC);
        assertEquals(testAdjList.getVertices(), testVerticesList);
    }
    
    @Test
    public void GraphWithNoVertices() {
        testAdjList = new AdjacencyListGraph();
        assertEquals(testAdjList.getVertices().size(), 0);
    }
    
    @Test
    public void DirectedEdgeBetweenTwoVertices() {
        testAdjList = new AdjacencyListGraph();
        testAdjList.addVertex(testVertexA);
        testAdjList.addVertex(testVertexD);
        testAdjList.addEdge(testVertexA, testVertexD);
        assertEquals(testAdjList.edgeExists(testVertexA, testVertexD), true);
    }
    
    @Test
    public void OppositeDirectedEdgeBetweenTwoVertices() {
        testAdjList = new AdjacencyListGraph();
        testAdjList.addVertex(testVertexA);
        testAdjList.addVertex(testVertexD);
        testAdjList.addEdge(testVertexA, testVertexD);
        assertEquals(testAdjList.edgeExists(testVertexD, testVertexA), false);
    }
    
    @Test
    public void NoDirectedEdgeBetweenTwoVertices() {
        testAdjList = new AdjacencyListGraph();
        testAdjList.addVertex(testVertexA);
        testAdjList.addVertex(testVertexG);
        assertEquals(testAdjList.edgeExists(testVertexA, testVertexG), false);
    }
    
    @Test
    public void OneDownstreamNeighbor() {
        testAdjList = new AdjacencyListGraph();
        testVerticesList = new ArrayList<Vertex>();
       
        testAdjList.addVertex(testVertexD);
        testAdjList.addVertex(testVertexE);
        testAdjList.addEdge(testVertexD, testVertexE);
        testVerticesList.add(testVertexE);
        assertEquals(testAdjList.getDownstreamNeighbors(testVertexD), testVerticesList );
    }
    
    @Test
    public void ThreeDownstreamNeighbors() {
        testAdjList = new AdjacencyListGraph();
        testVerticesList = new ArrayList<Vertex>();
        testAdjList.addVertex(testVertexD);
        testAdjList.addVertex(testVertexE);
        testAdjList.addVertex(testVertexF);
        testAdjList.addVertex(testVertexG);
        testAdjList.addEdge(testVertexD, testVertexE);
        testAdjList.addEdge(testVertexD, testVertexF);
        testAdjList.addEdge(testVertexD, testVertexG);
        testVerticesList.add(testVertexE);
        testVerticesList.add(testVertexF);
        testVerticesList.add(testVertexG);
        assertEquals(testAdjList.getDownstreamNeighbors(testVertexD).containsAll(testVerticesList), true );
    }
    
    @Test
    public void NoDownstreamNeighbors() {
        testAdjList = new AdjacencyListGraph();
        testVerticesList = new ArrayList<Vertex>();
       
        testAdjList.addVertex(testVertexD);
        testAdjList.addVertex(testVertexE);
        testAdjList.addEdge(testVertexD, testVertexE);
        assertEquals(testAdjList.getDownstreamNeighbors(testVertexE).isEmpty(), true );
    }
    
    @Test
    public void OneUpstreamNeighbor() {
        testAdjList = new AdjacencyListGraph();
        testVerticesList = new ArrayList<Vertex>();
       
        testAdjList.addVertex(testVertexD);
        testAdjList.addVertex(testVertexE);
        testAdjList.addEdge(testVertexD, testVertexE);
        testVerticesList.add(testVertexD);
        assertEquals(testAdjList.getUpstreamNeighbors(testVertexE), testVerticesList );
    }
    
    @Test
    public void ThreeUpstreamNeighbors() {
        testAdjList = new AdjacencyListGraph();
        testVerticesList = new ArrayList<Vertex>();
        testAdjList.addVertex(testVertexD);
        testAdjList.addVertex(testVertexE);
        testAdjList.addVertex(testVertexF);
        testAdjList.addVertex(testVertexG);
        testAdjList.addEdge(testVertexD, testVertexE);
        testAdjList.addEdge(testVertexF, testVertexE);
        testAdjList.addEdge(testVertexG, testVertexE);
        testVerticesList.add(testVertexD);
        testVerticesList.add(testVertexF);
        testVerticesList.add(testVertexG);
        assertEquals(testAdjList.getUpstreamNeighbors(testVertexE).containsAll(testVerticesList), true );
    }
    
    @Test
    public void NoUpstreamNeighbors() {
        testAdjList = new AdjacencyListGraph();
       
        testAdjList.addVertex(testVertexD);
        testAdjList.addVertex(testVertexE);
        testAdjList.addEdge(testVertexD, testVertexE);
        assertEquals(testAdjList.getUpstreamNeighbors(testVertexD).isEmpty(), true );
    }
    
    
}
