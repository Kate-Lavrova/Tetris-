package Menu;

import Game.Main;

import java.io.IOException;


public class Exit {

    static private Main runWindows;

    public static void bindWithApp(Main main) {
        runWindows = main;
    }

    public void yesButtonEvent(javafx.event.ActionEvent actionEvent) throws IOException {
        System.exit(0);
    }

    public void cancelButtonEvent(javafx.event.ActionEvent actionEvent) throws IOException {
        runWindows.closeExitMenu();
    }

}
