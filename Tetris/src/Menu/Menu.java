package Menu;


import Game.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Menu implements Initializable {

    @FXML
    static private Main runWindows;

    public static void bindWithApp(Main main){
        runWindows = main;
    }

    public void quitButtonEvent(javafx.event.ActionEvent actionEvent) throws IOException {
        runWindows.runExitMenu();
    }

    public void startGameButtonEvent(javafx.event.ActionEvent actionEvent) throws IOException {
        runWindows.runGameField();
    }

    public void loadButtonEvent(javafx.event.ActionEvent actionEvent) throws IOException {
        runWindows.loadGame();
    }

    public void replayButtonEvent(javafx.event.ActionEvent actionEvent) throws IOException {
        runWindows.runReplayMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
