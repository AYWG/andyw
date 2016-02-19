package ca.ece.ubc.cpen221.mp5;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RestaurantDBServer {
    /** Default port number where the server listens for connections. */
    private ServerSocket serverSocket;
    private String restaurant, userReview, userDetail;

    public RestaurantDBServer(int port, String restaurant, String userReview, String userDetail) throws IOException {
        this.restaurant = restaurant;
        this.userReview = userReview;
        this.userDetail = userDetail;
        serverSocket = new ServerSocket(port);
    }

    /**
     * Run the server, listening for connections and handling them.
     * 
     * @throws IOException
     *             if the main server socket is broken
     */
    public void serve() throws IOException {
        while (true) {
            // block until a client connects
            final Socket socket = serverSocket.accept();
            // create a new thread to handle that client
            Thread handler = new Thread(new Runnable() {
                public void run() {
                    try {
                        try {
                            handle(socket, restaurant, userReview, userDetail);
                        } finally {
                            socket.close();
                        }
                    } catch (IOException ioe) {
                        // this exception wouldn't terminate serve(),
                        // since we're now on a different thread, but
                        // we still need to handle it
                        ioe.printStackTrace();
                    }
                }
            });
            // start the thread
            handler.start();
        }
    }

    /**
     * Handle one client connection. Returns when client disconnects.
     * 
     * @param socket
     *            socket where client is connected
     * @throws IOException
     *             if connection encounters an error
     */
    private void handle(Socket socket, String restaurant, String userReview, String userDetail) throws IOException {
        String[] operations = new String[2];
        System.err.println("client connected");
        RestaurantDB DB = new RestaurantDB(restaurant, userReview, userDetail);
        // get the socket's input stream, and wrap converters around it
        // that convert it from a byte stream to a character stream,
        // and that buffer it so that we can read a line at a time
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // similarly, wrap character=>bytestream converter around the
        // socket output stream, and wrap a PrintWriter around that so
        // that we have more convenient ways to write Java primitive
        // types to it.
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        String result;

        Set<Restaurant> restaurants;
        try {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                System.err.println("request: " + line);
                try {
                    operations = QueryFactory.query(line);
                    restaurants = DB.query(QueryRichFactory.richQuery(line));
                    /*
                     * operation[0] == null when the query is invalid
                     */
                    if (operations[0] != "null") {
                        result = (QueryOperations.startOperation(DB, operations[0], operations[1]));
                        if (result == null) {
                            // result can is null if the server could not
                            // complete the given task
                            out.println("invalid order");
                        } else
                            // else print the result out
                            out.println(result);
                    }
                    
                    if (!restaurants.isEmpty()) {
                        for (Restaurant rest : restaurants) {
                            out.println(QueryOperations.RestJsonFormat(rest));
                        }
                    }

                    else {
                        System.err.println("invalid order");
                        out.println("invalid order");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } finally {
            out.close();
            in.close();
        }
    }

    public static void main(String[] args) {
        try {
            RestaurantDBServer server = new RestaurantDBServer(80, "data/restaurants.json", "data/reviews.json",
                    "data/users.json");

            server.serve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
