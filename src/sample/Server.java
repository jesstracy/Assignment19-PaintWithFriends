package sample;

import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jessicatracy on 9/1/16.
 */
public class Server {
    GraphicsContext serverGC;

    public void startServer() {
        Main myMain = new Main();

        try {
            ServerSocket serverListener = new ServerSocket(8005);
            System.out.println("Listener ready to accept connections");
            Socket clientSocket = serverListener.accept();
            System.out.println("Incoming connection from " + clientSocket.getInetAddress().getHostAddress());

            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);

//            String clientMessage = inputFromClient.readLine();
            String clientMessage;
            while((clientMessage = inputFromClient.readLine()) != null) {
                System.out.println("Stroke json string received from client: " + clientMessage);


                outputToClient.println("Received your stroke! " + clientMessage);

                // Deserialize json string into a stroke object
                System.out.println("Now deserializing client's message...");
                Stroke myStrokeFromClient = myMain.jsonDeserializeStroke(clientMessage);
                System.out.println("Deserialized stroke object received from client: " + myStrokeFromClient.toString());
            }

        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server myServer = new Server();
        myServer.startServer();

//        Main myMain = new Main();
        // when client sends their strokes...
//        myMain.startSecondStage(myServer.serverGC);
        // ^ or maybe just call start on main and see what happens?
        // for now, just make it print out what it gets from the client.
        // All of this should not be here anyway! Should be in start server, and then make other methods from there!
    }

}
