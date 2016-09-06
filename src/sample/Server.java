package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
public class Server extends Application implements Runnable {
    GraphicsContext serverGC;
    final double DEFAULT_SCENE_WIDTH = 800;
    final double DEFAULT_SCENE_HEIGHT = 600;
    private boolean keepDrawing = true;
    static boolean serversTurn = false;
    private int strokeSize = 10;
    Main myMain;

    public void run() {
        startServer();
    }

    public void startServer() {
        myMain = new Main();

        try {
            ServerSocket serverListener = new ServerSocket(8005);
            System.out.println("Listener ready to accept connections");

            // when it accepts a client socket, open a window.
            Socket clientSocket = serverListener.accept();

            System.out.println("Incoming connection from " + clientSocket.getInetAddress().getHostAddress());

//            startSecondStageInServer(myMain);
            // try calling it in main??
//            myMain.startSecondStageServer(serverGC);
//            start(myMain);

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

                //Make the serverGC do the stroke. In a method. And do Dom's run thing on tiyo
                serverGC.strokeOval(myStrokeFromClient.getxCoordinate(), myStrokeFromClient.getyCoordinate(), myStrokeFromClient.getStrokeSize(), myStrokeFromClient.getStrokeSize());
//                Platform.runLater(new RunnableGC(serverGC, myStrokeFromClient));

            }

        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        Server myServer = new Server();
//        myServer.startServer();

        launch(args);
    }

    public void start(Stage secondaryStage) {
        secondaryStage.setTitle("Server Stage");

        // we're using a grid layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(true);
//        grid.setPrefSize(primaryStage.getMaxWidth(), primaryStage.getMaxHeight());

        // add buttons and canvas to the grid
        Text sceneTitle = new Text("Welcome to Paint application");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0);

        Button button = new Button("My turn to draw! (Coming soon)");
        HBox hbButton = new HBox(10);
        hbButton.setAlignment(Pos.TOP_LEFT);
        hbButton.getChildren().add(button);
        grid.add(hbButton, 0, 1);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Server's turn set to true!");
                serversTurn = true;
                Main.myTurn = false;
            }
        });



        // add canvas
        Canvas canvas = new Canvas(DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT-100);


        serverGC = canvas.getGraphicsContext2D();
        serverGC.setFill(Color.GREEN);
        serverGC.setStroke(Color.BLUE);
        serverGC.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
        serverGC.setLineWidth(5);


        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent e) {
                //                System.out.println("x: " + e.getX() + ", y: " + e.getY());
                if (serversTurn) {
                    //
                    serverGC.strokeOval(e.getX(), e.getY(), strokeSize, strokeSize);
                    // save stroke to server's arrayList for replay button
                    //            Stroke saveStroke = new Stroke(e.getX(), e.getY(), strokeSize);
                    //            addStrokeToArrayList(saveStroke);

                    // only add stroke to client's strokeList if the client is running!
                    //            if (isClientRunning) {
                    //                Stroke clientStrokeMain = new Stroke(e.getX(), e.getY(), strokeSize);
                    //                System.out.println("Stroke I'm about to set to client: " + clientStrokeMain);
                    //                System.out.println("Setting to client.");
                    //                myClient.setClientStroke(clientStrokeMain);

                    //                        System.out.println("Now adding strokes to arraylist in client...");
                    //                        addStroke(e.getX(), e.getY(), strokeSize);
                    //                System.out.println("Now calling sendStrokesToServer...");
                    //                myClient.sendStrokesToServer(myMain);


                    //            }
                }
            }

        });

        grid.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent e) {
                System.out.println(e.getCode());
                System.out.println(e.getText());

                if (e.getText().equalsIgnoreCase("D")) {
                    System.out.println("Toggle drawing!");
                    keepDrawing = !keepDrawing;
                }

                if (e.getCode() == KeyCode.UP) {
                    strokeSize++;
                    int maxStrokeSize = 60;
                    if (strokeSize > maxStrokeSize) {
                        System.out.println("sample.Stroke size can't increase past " + maxStrokeSize + "!");
                        strokeSize = maxStrokeSize;
                    }
                }

                if (e.getCode() == KeyCode.DOWN) {
                    strokeSize--;
                    if (strokeSize < 1) {
                        System.out.println("sample.Stroke size must be at least 1!");
                        strokeSize = 1;
                    }
                }
            }
        });



        grid.add(canvas, 0 ,2);

        // set our grid layout on the scene
        Scene defaultScene = new Scene(grid, DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT);


        secondaryStage.setScene(defaultScene);
        System.out.println("About to show the second stage");

        secondaryStage.show();

        Thread myServerThread = new Thread(this);
        myServerThread.start();
    }

    // getters & setters


    public boolean isServersTurn() {
        return serversTurn;
    }

    public void setServersTurn(boolean serversTurn) {
        this.serversTurn = serversTurn;
    }
}