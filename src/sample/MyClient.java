package sample;

import javafx.scene.canvas.GraphicsContext;

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

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Main myMain = new Main();


            // for now, hardcode a Stroke object and make sure I can serialize it using jsonSerialize method and send it to
            // the server.

//            Stroke testStroke = new Stroke(100, 100, 5);
//            String serializedTestStroke = myMain.jsonSerialize(testStroke);
//            out.println(serializedTestStroke);

            // now test if I can serialize a stroke that is not already an object (using addStroke method) and send it to
            // the server.
            // NO because doesn't return anything for me to send. Would have to modify it to also return a string OR make
            // it send to server inside that method, which I would maybe have to pass it my out, don't want to do.

            // now test if I can deserialize it on the server side!!
            // done





            // paint things! And send to server for each stroke!
            myMain.main(args);

            // send painting to server when click the button

            clientSocket.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void startServerStage(Main myMain, GraphicsContext serverGC) {
//        myMain.startSecondStage(serverGC);
        myMain.startSecondStage();
    }
}

