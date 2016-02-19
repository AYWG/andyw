package ca.ece.ubc.cpen221.mp5;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class RestaurantDBClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public RestaurantDBClient(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void sendRequest(String x) throws IOException {
        out.print(x + "\n");
        out.flush(); // important! make sure x actually gets sent
    }

    public String getReply() throws IOException {
        String reply = in.readLine();
        if (reply == null) {
            throw new IOException("connection terminated unexpectedly");
        }

        try {
            return reply;
        } catch (NumberFormatException nfe) {
            throw new IOException("misformatted reply: " + reply);
        }
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            RestaurantDBClient client = new RestaurantDBClient("localhost", 80);
            String request = "getRestaurant(\"\")";
            System.out.println("The request is " + request);
            client.sendRequest(request);
            System.out.println(client.getReply());

            client.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
