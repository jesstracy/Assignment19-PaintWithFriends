//package sample;
//
//import javafx.scene.canvas.GraphicsContext;
//
///**
// * Created by jessicatracy on 9/3/16.
// */
//public class RunnableGC implements Runnable {
//
//    private GraphicsContext gc = null;
//    private Stroke stroke = null;
//
//    public RunnableGC(GraphicsContext gc, Stroke stroke) {
//        this.gc = gc;
//        this.stroke = stroke;
//    }
//
//    public void run() {
//        gc.strokeOval(stroke.getxCoordinate(), stroke.getyCoordinate(), stroke.getStrokeSize(), stroke.getStrokeSize());
//    }
//}
