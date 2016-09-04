package sample;

import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jessicatracy on 9/1/16.
 */
public class Client {
    private ArrayList<Stroke> strokeList = new ArrayList<Stroke>();
    private ArrayList<String> serializedStrokeList =  new ArrayList<String>();
    Stroke strokeToSend = new Stroke();
    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;

    //For testing
    private Stroke testingStroke;
    public Stroke getTestingStroke() {
        return testingStroke;
    }
    public void setTestingStroke(Stroke testingStroke) {
        this.testingStroke = testingStroke;
    }



    public void startClientSocket() {
        try {
            clientSocket = new Socket("localhost", 8005);        /* me */
//            clientSocket = new Socket("192.168.1.207", 8005);  /* benjamin lee */
            System.out.println("\tClient socket connected");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // for now, hardcode a Stroke object and make sure I can serialize it using jsonSerialize method and send it to
            // the server.

//            Stroke testStroke = new Stroke(100, 100, 5);
//            String serializedTestStroke = myMain.jsonSerialize(testStroke);
//            out.println(serializedTestStroke);

            //read in server's return method
//            String serverResponse = in.readLine();
//            System.out.println("Server replied: " + serverResponse);
            // now test if I can serialize a stroke that is not already an object (using addStroke method) and send it to
            // the server.
            // NO because doesn't return anything for me to send. Would have to modify it to also return a string OR make
            // it send to server inside that method, which I would maybe have to pass it my out, don't want to do.

            // now test if I can deserialize it on the server side!!
            // done

            // test if arraylist is getting strokes
//            System.out.println("Now printing strokes in arraylist...");
//            for (Stroke stroke : strokeList) {
//                System.out.println(stroke.toString());
//            }
//            System.out.println(strokeList.get(0));




            // **** code that's now in sendStrokesToServer was here!!



            // send painting to server when click the button

            // took out when I added method below.
//            clientSocket.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void sendStrokesToServer(Main myMain) {
        try {
//            System.out.println("Now serializing strokes in arraylist...");
//            for (Stroke stroke : strokeList) {
//                String jsonStroke = myMain.jsonSerialize(stroke);
//                serializedStrokeList.add(jsonStroke);
//            }

            // TESTING
            System.out.println("\tClient side's testing stroke: " + testingStroke);
            System.out.println("\tNow serializing testing stroke....");
            String serializedTestingStroke = myMain.jsonSerialize(testingStroke);
            System.out.println("\tSerialized testing stroke: " + serializedTestingStroke);
            System.out.println("\tNow sending testingStroke to server....");
            out.println(serializedTestingStroke);


            String serverResponse = in.readLine();
            System.out.println("\tServer replied: " + serverResponse);
//            clientSocket.close();

        } catch (IOException exception) {
            System.out.println("\tException caught when reading in from server.");
            exception.printStackTrace();
        }
    }

    // arraylist getters and setters
    public ArrayList<Stroke> getStrokeList() {
        return strokeList;
    }

    public void setStrokeList(ArrayList<Stroke> strokeList) {
        this.strokeList = strokeList;
    }

    // Add a stroke to arrayList
    public void addStrokeToArrayList(Stroke myStroke) {
        strokeList.add(myStroke);
    }
}






