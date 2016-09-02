package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Application {

    final double DEFAULT_SCENE_WIDTH = 800;
    final double DEFAULT_SCENE_HEIGHT = 600;
    boolean keepDrawing = true;
    int strokeSize = 10;
//    Stroke myStroke;

    GraphicsContext secondGC;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");

        // we're using a grid layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(true);

        // add buttons and canvas to the grid
        Text sceneTitle = new Text("Welcome to Paint application");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0);

        Button button = new Button("Open second stage");
        HBox hbButton = new HBox(10);
        hbButton.setAlignment(Pos.TOP_LEFT);
        hbButton.getChildren().add(button);
        grid.add(hbButton, 0, 1);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("I can switch to another scene here ...");
//                primaryStage.setScene(loginScene);
                startSecondStage();
            }
        });

        // add canvas
        Canvas canvas = new Canvas(DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT-100);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
        gc.setLineWidth(5);

        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
//                System.out.println("x: " + e.getX() + ", y: " + e.getY());
                if (keepDrawing) {
                    gc.strokeOval(e.getX(), e.getY(), strokeSize, strokeSize);
                    if (secondGC != null) {
                        addStroke(e.getX(), e.getY(), strokeSize);
                    }
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


        grid.add(canvas, 0, 2);



        // set our grid layout on the scene
        Scene defaultScene = new Scene(grid, DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT);

        primaryStage.setScene(defaultScene);
        primaryStage.show();
    }

    public void addStroke(double xCoordinate, double yCoordinate, int strokeSize) {
//        myStroke = new Stroke(xCoordinate, yCoordinate, strokeSize);
        secondGC.strokeOval(xCoordinate, yCoordinate, strokeSize, strokeSize);
    }

    public void startSecondStage() {
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Second Stage");

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

        Button button = new Button("Sample paint button");
        HBox hbButton = new HBox(10);
        hbButton.setAlignment(Pos.TOP_LEFT);
        hbButton.getChildren().add(button);
        grid.add(hbButton, 0, 1);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("I can switch to another scene here ...");
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

        secondGC = canvas.getGraphicsContext2D();
        secondGC.setFill(Color.GREEN);
        secondGC.setStroke(Color.BLUE);
        secondGC.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
        secondGC.setLineWidth(5);



        grid.add(canvas, 0 ,2);

        // set our grid layout on the scene
        Scene defaultScene = new Scene(grid, DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT);


        secondaryStage.setScene(defaultScene);
        System.out.println("About to show the second stage");

        secondaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
