package Game.Types_figures;

import Game.Figure;

import java.awt.*;

// Фигура
public class f7 extends Figure {

    public f7(){
        super();
        positionMode = 0;
        positionModeMax = 2;
        figureType = 6;
        coordinates[0] = new Point();
        coordinates[1] = new Point();
        coordinates[2] = new Point();
        coordinates[3] = new Point();
    }

    @Override
    public void setStartPosition(Point basicElementPosition){

        coordinates[0].setLocation((int)basicElementPosition.getX(), (int)basicElementPosition.getY());
        coordinates[1].setLocation((int)coordinates[0].getX()+1,(int)coordinates[0].getY());
        coordinates[2].setLocation((int)coordinates[0].getX()+2,(int)coordinates[0].getY());
        coordinates[3].setLocation((int)coordinates[0].getX()+3,(int)coordinates[0].getY());
    }

    public Point[] figureNextRotatePosition(){

        Point[] figureNextDroppingPosition = new Point[4];
        for(int i=0;i<4;i++) {
            figureNextDroppingPosition[i] = new Point();
            figureNextDroppingPosition[i].setLocation(coordinates[i].getX(), coordinates[i].getY()+1);
        }

        switch(positionMode){

            case 1:
                figureNextDroppingPosition[0].setLocation((int)coordinates[0].getX() + 2, (int)coordinates[0].getY() - 2);
                figureNextDroppingPosition[1].setLocation((int)coordinates[1].getX() + 1, (int)coordinates[1].getY() - 1);
                figureNextDroppingPosition[2].setLocation((int)coordinates[2].getX(),(int)coordinates[2].getY());
                figureNextDroppingPosition[3].setLocation((int)coordinates[3].getX() - 1, (int)coordinates[3].getY() + 1);
                return figureNextDroppingPosition;

            case 2:
                figureNextDroppingPosition[0].setLocation((int)coordinates[0].getX() - 2, (int)coordinates[0].getY() + 2);
                figureNextDroppingPosition[1].setLocation((int)coordinates[1].getX() - 1, (int)coordinates[1].getY() + 1);
                figureNextDroppingPosition[2].setLocation((int)coordinates[2].getX(),(int)coordinates[2].getY());
                figureNextDroppingPosition[3].setLocation((int)coordinates[3].getX() + 1, (int)coordinates[3].getY() - 1);
                return figureNextDroppingPosition;

        }
        return coordinates;
    }


}
