package sample;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Created by jessicatracy on 9/1/16.
 */
public class Stroke {
    private double xCoordinate;
    private double yCoordinate;
    private int strokeSize;
//    private Instant strokeTime;

    public Stroke() {

    }

    public Stroke(double xCoordinate, double yCoordinate, int strokeSize) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.strokeSize = strokeSize;
    }

//    public Stroke(double xCoordinate, double yCoordinate, int strokeSize, Instant strokeTime) {
//        this.xCoordinate = xCoordinate;
//        this.yCoordinate = yCoordinate;
//        this.strokeSize = strokeSize;
//        this.strokeTime = strokeTime;
//    }

    @Override
    public String toString() {
        return "Stroke info: " +
                "xCoordinate=" + xCoordinate + ", " +
                "yCoordinate=" + yCoordinate + ", " +
                "strokeSize=" + strokeSize;
    }

    // getters and setters
    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public int getStrokeSize() {
        return strokeSize;
    }

    public void setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
    }

//    public Instant getStrokeTime() {
//        return strokeTime;
//    }
//
//    public void setStrokeTime(Instant strokeTime) {
//        this.strokeTime = strokeTime;
//    }
}
