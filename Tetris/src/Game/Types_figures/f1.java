package Game.Types_figures;

import Game.Figure;

import java.awt.*;

//  вадратик
public class f1 extends Figure {

    public f1(){
        super();
        positionMode = 0;
        positionModeMax = 0;
        figureType = 0;
        coordinates[0] = new Point();
        coordinates[1] = new Point();
        coordinates[2] = new Point();
        coordinates[3] = new Point();
    }

    @Override
    public void setStartPosition(Point basicElementPosition){

        coordinates[0].setLocation((int)basicElementPosition.getX()+1, (int)basicElementPosition.getY());
        coordinates[1].setLocation((int)coordinates[0].getX() + 1, (int)coordinates[0].getY());
        coordinates[2].setLocation((int)coordinates[0].getX() + 1,(int)coordinates[0].getY() + 1);
        coordinates[3].setLocation((int)coordinates[0].getX(), (int)coordinates[0].getY() + 1);
    }
}
