package Game;

/**
 * Created by Kate on 06.05.2015.
 */

import Menu.Exit;
import Menu.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Main extends Application {

    private Stage stage, tempStage;
    private Playing_Fields playingField;
    private Replay playingFieldss;

    Thread thread = new Thread();
    //Thread thread = new Thread(this);
    // thread.start();
    //thread.setDaemon(true)


    public void runMainMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Design/MainMenu.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene=new Scene(root, 570, 620);
        stage.setScene(scene);
    }

    public void runGameField() throws IOException {
        playingField = new Playing_Fields(false);
        stage.setScene(playingField.getGameFieldScene());
        playingField.runGame();
        stage.show();
    }

    public void loadGame() throws IOException {
        playingField = new Playing_Fields(false);
        stage.setScene(playingField.getGameFieldScene());
        playingField.load(new DataInputStream(new FileInputStream("gsaves")));
        playingField.startMainAnimationTimer();
    }

    public void runReplay(File file) throws IOException {
        playingField = new Playing_Fields(true);
        stage.setScene(playingField.getGameFieldScene());
        playingField.runReplay(file);
    }

    public void runExitMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Design/ExitMenu.fxml"));
        Parent root = fxmlLoader.load();
        tempStage = new Stage();
        tempStage.setResizable(false);
        tempStage.setTitle("Exit");
        tempStage.setScene(new Scene(root, 297, 145));
        tempStage.show();
    }

    public void runReplayMenu() throws IOException {
        playingFieldss = new Replay();
        stage.setScene(playingFieldss.getReplayScene());
        playingFieldss.countStatistic();
        stage.show();
    }

    public void closeExitMenu() throws IOException {
        tempStage.close();
    }

    public Window getStage() {
        return stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Menu.bindWithApp(this);
        Exit.bindWithApp(this);
        playingFieldss.bindWithApp(this);
        Playing_Fields.bindWithApp(this);

        stage = primaryStage;
        stage.setTitle("TETRIS ^^");
        stage.setResizable(false);
        stage.show();
        runMainMenu();


        Media audio = new Media(this.getClass().getResource("myfortetris.wav").toString());
        final MediaPlayer playerAudio=new MediaPlayer(audio);
        playerAudio.setCycleCount(MediaPlayer.INDEFINITE);
        playerAudio.setAutoPlay(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}