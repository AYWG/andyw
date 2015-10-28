package twitterAnalysis;

import java.util.*;
import java.io.*;

import ca.ubc.ece.cpen221.mp3.graph.AdjacencyListGraph;
import ca.ubc.ece.cpen221.mp3.graph.AdjacencyMatrixGraph;
import ca.ubc.ece.cpen221.mp3.graph.Algorithms;

import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class TwitterAnalysis {

    public static final String twitterDataFile = "datasets/twitterTest.txt";
    public static final String inputFile = "datasets/query.txt";
    public static final String outputFile = "datasets/result.txt";
    private static AdjacencyListGraph twitterGraph = new AdjacencyListGraph();
    BufferedReader br = null;

    public TwitterAnalysis(String twitterDataFileName) {

        try {
            String sCurrentLine;

            br = new BufferedReader(new FileReader(twitterDataFileName));
            while ((sCurrentLine = br.readLine()) != null) {
                String[] parts = sCurrentLine.split(" -> ");
                String a = parts[0];
                String b = parts[1];

                Vertex follower = new Vertex(a);
                Vertex followed = new Vertex(b);

                if (!twitterGraph.getVertices().contains(follower)) {
                    twitterGraph.addVertex(follower);
                }

                if (!twitterGraph.getVertices().contains(followed)) {
                    twitterGraph.addVertex(followed);
                }

                twitterGraph.addEdge(follower, followed);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<Vertex> getCommonInfluencers(Vertex userA, Vertex userB) {
        return Algorithms.commonDownstreamVertices(twitterGraph, userA, userB);
    }

    public String getNumRetweets(Vertex userA, Vertex userB) {
        return Integer.toString(Algorithms.shortestDistance(twitterGraph, userA, userB));
    }

    public static void main(String[] args) {
        BufferedReader br = null;
        List<String> doneQuery = new ArrayList<String>();
        TwitterAnalysis ta = new TwitterAnalysis(twitterDataFile);

        try {
            String query;

            br = new BufferedReader(new FileReader(inputFile));
            while ((query = br.readLine()) != null) {
                String[] split = query.split(" ");
                if (split.length < 4) {
                    continue;
                }
                String operation = split[0];
                String follower = split[1];
                String followed = split[2];
                boolean queryFlag = false;
                if (split[3] == "?") {
                    queryFlag = true;
                }
                    
                
                while ((operation.equals("commonInfluencers") || operation.equals("numRetweets")) && !doneQuery.contains(operation + follower + followed) && queryFlag == true) {
                    Vertex a = new Vertex(follower);
                    Vertex b = new Vertex(followed);
                    File result = new File(outputFile);

                    try {
                        if (!result.exists()) {
                            result.createNewFile();
                        }

                        FileWriter fileWriter = new FileWriter(result, true);
                        BufferedWriter out = new BufferedWriter(fileWriter);
                        out.write("query: " + operation + " " + follower + " " + followed);
                        out.newLine();
                        out.write("<result>");
                        out.newLine();

                        if (operation.equals("numRetweets")) {
                            out.write(ta.getNumRetweets(a, b));
                            doneQuery.add(operation + follower + followed);
                        }

                        if (operation.equals("commonInfluencers")) {

                            for (Vertex v : ta.getCommonInfluencers(a, b)) {
                            out.write(v.toString());
                            out.newLine();
                            }
                            doneQuery.add(operation + follower + followed);
                        }
                        out.newLine();
                        out.write("</result>\n");
                        out.newLine();
                        out.newLine();
                        out.close();

                    } catch (IOException e) {
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
