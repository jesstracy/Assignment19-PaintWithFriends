package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jessicatracy on 9/1/16.
 */
public class MyClient {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 8005);

            // I don't think I need this? Or maybe just not yet?
//            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // paint things!! call methods from main class. But what about launch(args) in other main? If doesn't work, check that.
            // maybe just call main method in main class?
            Main myMain = new Main();
            myMain.main(args);

            clientSocket.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

