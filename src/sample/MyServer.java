package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jessicatracy on 9/1/16.
 */
public class MyServer {
    public void startServer() {

        try {
            ServerSocket serverListener = new ServerSocket(8005);
            System.out.println("Listener ready to accept connections");
            Socket clientSocket = serverListener.accept();
            System.out.println("Incoming connection from " + clientSocket.getInetAddress().getHostAddress());

            // Do I need this???
//            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            PrintWriter outputToClient = new PrintWriter(clientSocket.getOutputStream());

        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyServer myServer = new MyServer();
        myServer.startServer();
    }

}
