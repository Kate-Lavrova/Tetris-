package Game;

import javafx.scene.control.Button;

/**
 * Created by kate on 02.06.15.
 */

public class Playing_Field_cell {
    private Button cell;
    private boolean gameFieldCellStatus;

    public Playing_Field_cell(){
        cell = new Button();
        gameFieldCellStatus = false;
    }

    public boolean returnGameFieldCellStatus(){
        return gameFieldCellStatus;
    }

    public void setGameFieldCellStatus(boolean newStatus){
        gameFieldCellStatus = newStatus;
    }

    public Button returnGameFieldCell(){
        return cell;
    }
}

