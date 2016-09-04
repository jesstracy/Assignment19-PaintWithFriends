//package sample;
//
//import java.io.IOException;
//
///**
// * Created by jessicatracy on 9/3/16.
// */
//public class Listener implements Runnable{
//    private Main myMain;
//    private Client myClient;
//
//    public Listener(Main myMain, Client myClient) {
//        this.myMain = myMain;
//        this.myClient = myClient;
//    }
//
//    public void run() {
//        while (true) {
//            try{
//                if(!myClient.in.readLine().equals("0")){
//                    myClient.decideWhoIsDrawing(myMain);
//                }
//            }catch(IOException ex){
//                ex.printStackTrace();
//            }
//
//        }
//    }
//}
