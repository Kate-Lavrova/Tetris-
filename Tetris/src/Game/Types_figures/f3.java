package Game.Types_figures;

import Game.Figure;

import java.awt.*;

// Фигурка     _|-
public class f3 extends Figure {

    public f3(){
        super();
        positionMode = 0;
        positionModeMax = 2;
        figureType = 2;
        coordinates[0] = new Point();
        coordinates[1] = new Point();
        coordinates[2] = new Point();
        coordinates[3] = new Point();
    }

    @Override
    public void setStartPosition(Point basicElementPosition){

        coordinates[0].setLocation((int)basicElementPosition.getX(), (int)basicElementPosition.getY()+1);
        coordinates[1].setLocation((int)coordinates[0].getX()+1, (int)coordinates[0].getY());
        coordinates[2].setLocation((int)coordinates[0].getX()+1,(int)coordinates[0].getY()-1);
        coordinates[3].setLocation((int)coordinates[0].getX()+2, (int)coordinates[0].getY()-1);
    }

    public Point[] figureNextRotatePosition(){

        Point[] figureNextDroppingPosition = new Point[4];
        for(int i=0;i<4;i++) {
            figureNextDroppingPosition[i] = new Point();
            figureNextDroppingPosition[i].setLocation(coordinates[i].getX(), coordinates[i].getY()+1);
        }

        switch(positionMode){

            case 1:
                figureNextDroppingPosition[0].setLocation((int)coordinates[0].getX() + 1, (int)coordinates[0].getY() + 1);
                figureNextDroppingPosition[1].setLocation((int)coordinates[1].getX(), (int)coordinates[1].getY());
                figureNextDroppingPosition[2].setLocation((int)coordinates[2].getX() - 1,(int)coordinates[2].getY() + 1);
                figureNextDroppingPosition[3].setLocation((int)coordinates[3].getX() - 2, (int)coordinates[3].getY());
                return figureNextDroppingPosition;

            case 2:
                figureNextDroppingPosition[0].setLocation((int)coordinates[0].getX() - 1, (int)coordinates[0].getY() - 1);
                figureNextDroppingPosition[1].setLocation((int)coordinates[1].getX(), (int)coordinates[1].getY());
                figureNextDroppingPosition[2].setLocation((int)coordinates[2].getX() + 1,(int)coordinates[2].getY() - 1);
                figureNextDroppingPosition[3].setLocation((int)coordinates[3].getX() + 2, (int)coordinates[3].getY());
                return figureNextDroppingPosition;

        }
        return coordinates;
    }
}
