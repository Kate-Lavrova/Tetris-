package Game;

import java.awt.*;

/**
 * Created by Kate on 20.05.2015.
 */


public class Figure {

    protected Point coordinates[];
    protected boolean activeStatus;
    protected int positionMode;
    protected int positionModeMax;
    protected int figureType;

    public Figure() {
        coordinates = new Point[4];
        activeStatus = true;
    }


    public void setNewCoordinates(Point[] newCoordinates){
        for(int i=0;i<coordinates.length; i++)
            coordinates[i].setLocation(newCoordinates[i].getX(),newCoordinates[i].getY());
    }

    public void increasePositionMode(){
        positionMode++;
    }

    public void decreasePositionMode(){
        positionMode--;
    }

    public int returnPositionMode(){
        return positionMode;
    }

    public int returnPositionModeMax(){
        return positionModeMax;
    }

    public int returnFigureType(){
        return figureType;
    }

    public void setPositionMode(int newMode){
     positionMode = newMode;
    }

    public Point[] returnCoordinates(){
        return coordinates;
    }

    public Point[] figureNextDroppingPosition(){
        Point[] figureNextDroppingPosition = new Point[4];
        for(int i=0;i<4;i++) {
            figureNextDroppingPosition[i] = new Point();
            figureNextDroppingPosition[i].setLocation(coordinates[i].getX(), coordinates[i].getY()+1);
        }
        return figureNextDroppingPosition;
    }

    public Point[] figureNextMovingRightPosition(){
        Point[] figureNextDroppingPosition = new Point[4];
        for(int i=0;i<4;i++) {
            figureNextDroppingPosition[i] = new Point();
            figureNextDroppingPosition[i].setLocation(coordinates[i].getX()+1, coordinates[i].getY());
        }
        return figureNextDroppingPosition;
    }

    public Point[] figureNextMovingLeftPosition(){
        Point[] figureNextDroppingPosition = new Point[4];
        for(int i=0;i<4;i++) {
            figureNextDroppingPosition[i] = new Point();
            figureNextDroppingPosition[i].setLocation(coordinates[i].getX()-1,coordinates[i].getY());
        }
        return figureNextDroppingPosition;
    }

    public Point[] figureNextRotatePosition() {
        return coordinates;
    }

    public void figureSetPosition(Point[] nextDroppingPosition){
        for(int i=0;i<4;i++) {
            coordinates[i].setLocation(nextDroppingPosition[i].getX(), nextDroppingPosition[i].getY());
        }
    }

    public void setStartPosition(Point basicElementPosition){
    }
}