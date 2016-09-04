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
    private boolean serverTurn = false;
    private int serverStrokeSize = 10;
    private boolean serverKeepDrawing = true;

    public void run() {
        startServer();
    }

    public void startServer() {
        Main myMain = new Main();

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
                // If it's the client's turn, get json stroke, deserialize, and draw it out.
                if (!serverTurn) {
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
                // if it's the server's turn, let the server draw and send strokes to client!
                else {
                    System.out.println("Now it is the server's turn to draw");
                    // send server's strokes!
                }

            }

        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        Server myServer = new Server();
//        myServer.startServer();

        launch(args);
//        Main myMain = new Main();
        // when client sends their strokes...
//        myMain.startSecondStage(myServer.serverGC);
        // ^ or maybe just call start on main and see what happens?
        // for now, just make it print out what it gets from the client.
        // All of this should not be here anyway! Should be in start server, and then make other methods from there!
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

        Button button = new Button("My turn to draw!");
        HBox hbButton = new HBox(10);
        hbButton.setAlignment(Pos.TOP_LEFT);
        hbButton.getChildren().add(button);
        grid.add(hbButton, 0, 1);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Setting server's turn to true...");
                serverTurn = true;
            }
        });



        // add canvas
        Canvas canvas = new Canvas(DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT-100);

        //Make graphics thing here??
//        GraphicsContext secondGC = canvas.getGraphicsContext2D();
//        secondGC.setFill(Color.GREEN);
//        secondGC.setStroke(Color.BLUE);
//        secondGC.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
//        secondGC.setLineWidth(5);

        serverGC = canvas.getGraphicsContext2D();
        serverGC.setFill(Color.GREEN);
        serverGC.setStroke(Color.BLUE);
        serverGC.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
        serverGC.setLineWidth(5);

        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (serverTurn && serverKeepDrawing) {
                    serverGC.strokeOval(event.getX(), event.getY(), serverStrokeSize, serverStrokeSize);

                    //set stroke object in order and send it to the client.
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
                    serverKeepDrawing = !serverKeepDrawing;
                }

                if (e.getText().equalsIgnoreCase("A")) {
                    serverGC.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
                }

                if (e.getCode() == KeyCode.UP) {
                    serverStrokeSize++;
                    int maxStrokeSize = 60;
                    if (serverStrokeSize > maxStrokeSize) {
                        System.out.println("sample.Stroke size can't increase past " + maxStrokeSize + "!");
                        serverStrokeSize = maxStrokeSize;
                    }
                }

                if (e.getCode() == KeyCode.DOWN) {
                    serverStrokeSize--;
                    if (serverStrokeSize < 1) {
                        System.out.println("sample.Stroke size must be at least 1!");
                        serverStrokeSize = 1;
                    }
                }
            }
        });




        grid.add(canvas, 0 ,2);

        // set our grid layout on the scene
        Scene defaultScene = new Scene(grid, DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT);


        secondaryStage.setScene(defaultScene);
        System.out.println("About to show the server stage");

        secondaryStage.show();

        Thread myServerThread = new Thread(this);
        myServerThread.start();
    }


}
