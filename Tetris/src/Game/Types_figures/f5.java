package Game.Types_figures;

import Game.Figure;

import java.awt.*;

// Фигурка _ _ _ _
public class f5 extends Figure {

    public f5(){
        super();
        positionMode = 0;
        positionModeMax = 4;
        figureType = 4;
        coordinates[0] = new Point();
        coordinates[1] = new Point();
        coordinates[2] = new Point();
        coordinates[3] = new Point();
    }

    @Override
    public void setStartPosition(Point basicElementPosition){

        coordinates[0].setLocation((int) basicElementPosition.getX(), (int) basicElementPosition.getY() + 1);
        coordinates[1].setLocation((int)coordinates[0].getX()+1, (int)coordinates[0].getY());
        coordinates[2].setLocation((int)coordinates[0].getX()+2,(int)coordinates[0].getY());
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
                figureNextDroppingPosition[0].setLocation((int)coordinates[0].getX() + 1, (int)coordinates[0].getY() - 1);
                figureNextDroppingPosition[1].setLocation((int)coordinates[1].getX(), (int)coordinates[1].getY());
                figureNextDroppingPosition[2].setLocation((int)coordinates[2].getX() - 1,(int)coordinates[2].getY() + 1);
                figureNextDroppingPosition[3].setLocation((int)coordinates[3].getX(), (int)coordinates[3].getY() + 2);
                return figureNextDroppingPosition;

            case 2:
                figureNextDroppingPosition[0].setLocation((int)coordinates[0].getX() + 1, (int)coordinates[0].getY() + 1);
                figureNextDroppingPosition[1].setLocation((int)coordinates[1].getX(), (int)coordinates[1].getY());
                figureNextDroppingPosition[2].setLocation((int)coordinates[2].getX() - 1,(int)coordinates[2].getY() - 1);
                figureNextDroppingPosition[3].setLocation((int)coordinates[3].getX() - 2, (int)coordinates[3].getY());
                return figureNextDroppingPosition;

            case 3:
                figureNextDroppingPosition[0].setLocation((int)coordinates[0].getX() - 1, (int)coordinates[0].getY() + 1);
                figureNextDroppingPosition[1].setLocation((int)coordinates[1].getX(), (int)coordinates[1].getY());
                figureNextDroppingPosition[2].setLocation((int)coordinates[2].getX() + 1,(int)coordinates[2].getY() - 1);
                figureNextDroppingPosition[3].setLocation((int)coordinates[3].getX(), (int)coordinates[3].getY() - 2);
                return figureNextDroppingPosition;

            case 4:
                figureNextDroppingPosition[0].setLocation((int)coordinates[0].getX() - 1, (int)coordinates[0].getY() - 1);
                figureNextDroppingPosition[1].setLocation((int)coordinates[1].getX(), (int)coordinates[1].getY());
                figureNextDroppingPosition[2].setLocation((int)coordinates[2].getX() + 1,(int)coordinates[2].getY() + 1);
                figureNextDroppingPosition[3].setLocation((int)coordinates[3].getX() + 2, (int)coordinates[3].getY());
                return figureNextDroppingPosition;
        }
        return coordinates;
    }

}
