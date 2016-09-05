//package sample;
//
//import javafx.scene.canvas.GraphicsContext;
//
//import java.io.IOException;
//
///**
// * Created by jessicatracy on 9/3/16.
// */
//public class Listener implements Runnable{
////    private Main myMain;
//    private Client myClient;
//    private Server myServer;
//    private Main myMain;
//    private GraphicsContext gc;
//
//    public Listener(Server myServer, Client myClient, Main myMain, GraphicsContext gc) {
//        this.myServer = myServer;
//
//        this.myClient = myClient;
//        this.myMain = myMain;
//        this.gc = gc;
//    }
//
//    public void run() {
////        while (true) {
////            try{
////                if(!myClient.in.readLine().equals("0")){
////                    myClient.decideWhoIsDrawing(myMain);
////                }
////            }catch(IOException ex){
////                ex.printStackTrace();
////            }
////
////        }
//        System.out.println("***STARTING THREAD***");
//        while (true) {
//            if (myServer.isServerTurn()) {
//                myClient.acceptServerStrokes(myMain, gc);
//            }
//        }
//    }
//}
