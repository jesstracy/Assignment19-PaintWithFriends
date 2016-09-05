package sample;

import java.io.*;
import java.net.Socket;
import java.time.Instant;
import java.util.ArrayList;

/**
 * Created by jessicatracy on 9/1/16.
 */
public class Client {
    private ArrayList<Stroke> strokeList = new ArrayList<Stroke>();
//    private ArrayList<String> serializedStrokeList =  new ArrayList<String>();
    Stroke strokeToSend = new Stroke();
    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;
//    private Instant timeOfFirstStroke;
    private String ipAddress;

    //For testing
    private Stroke clientStroke;
    public Stroke getClientStroke() {
        return clientStroke;
    }
    public void setClientStroke(Stroke clientStroke) {
        this.clientStroke = clientStroke;
    }

    public void startClientSocket() {
        try {
//            clientSocket = new Socket("localhost", 8005);        /* me */
//            clientSocket = new Socket("192.168.1.207", 8005);  /* Ben */
            clientSocket = new Socket(ipAddress, 8005);
            System.out.println("\tClient socket connected");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String getIPAddress() {
        return "";
    }

    public void sendStrokesToServer(Main myMain) {
        try {
//            System.out.println("Now serializing strokes in arraylist...");
//            for (Stroke stroke : strokeList) {
//                String jsonStroke = myMain.jsonSerialize(stroke);
//                serializedStrokeList.add(jsonStroke);
//            }

            // TESTING
            System.out.println("\tClient side's testing stroke: " + clientStroke);
            System.out.println("\tNow serializing testing stroke....");
            String serializedTestingStroke = myMain.jsonSerialize(clientStroke);
            System.out.println("\tSerialized testing stroke: " + serializedTestingStroke);
            System.out.println("\tNow sending clientStroke to server....");
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

//    public Instant getTimeOfFirstStroke() {
//        return timeOfFirstStroke;
//    }

//    public void setTimeOfFirstStroke(Instant timeOfFirstStroke) {
//        this.timeOfFirstStroke = timeOfFirstStroke;
//    }


    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
