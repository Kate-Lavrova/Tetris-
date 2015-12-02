package Game;

/**
 * Created by Kate on 10.05.2015.
 */


import Game.Types_figures.*;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.awt.*;
import java.io.*;
import java.util.Calendar;
import java.util.Random;

public class Playing_Fields  {

    private static final int fastDroppingSpeed = 450000;
    private static final int defaultSpeedMode = 500000000;
    private static final int autoSpeedMode = 900000000;
    private static final int replaySpeedMode = 150000000;

    private static final String replayFolderPath =  "C:\\Users\\SONY\\Desktop\\67\\Tetris\\replayFolder\\";

    private Random randomGenerator;
    private DataOutputStream replayOutStream;
    private DataInputStream replayInStream;
// входной поток данных

    private int speedMode;
    private int tempSpeedMode;
    private int deletedLinesCount;
    private int currentScore; // наш текущий рейтинг
    private int newFigureMode;

    private Point gameFieldBasicPosition;
    private Point nextFigureFieldBasicPosition;
    private boolean keyEventProcessing = false;
    private boolean autoRegime = false;

    static private Main runWindows;

    private Scene gameScene;
    private ImageView backgroundImage;
    private Group root;
    private AnchorPane anchorPane;
    private Label nextFigureLabel;
    private Label topTextLabel;
    private Label topScoreLabel;
    private Label scoresTextLabel;
    private Label scoresScoreLabel;
    private Label loosingLabel;
    private Button backToMainButton;
    private Button restartButton;
    private Button autoGameButton;
    private Button saveGameButton;
    private Button gameFieldFrame;
    private Playing_Field_cell playingFieldCellArray[][];
    private Playing_Field_cell nextFigureCellArray[][];
    private Figure activeFigure;
    private Figure nextFigure;

    Thread thread = new Thread();

    private AnimationTimer animationTimer = new AnimationTimer() {
        private long lastTime = 0;

        @Override
        public void handle(long now) {

            if ((now - lastTime) >= speedMode) {
                setFieldCellsEmpty(activeFigure.returnCoordinates(), playingFieldCellArray);
                if (movingAvailable(activeFigure.figureNextDroppingPosition(), playingFieldCellArray)) {
                    activeFigure.figureSetPosition(activeFigure.figureNextDroppingPosition());
                    setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                    updateField(playingFieldCellArray);

                } else {

                    if (speedMode == fastDroppingSpeed)
                        speedMode = tempSpeedMode;

                    setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                    processBusyLines(playingFieldCellArray);
                    activeFigure = dropNewFigure(newFigureMode);
                    activeFigure.setStartPosition(gameFieldBasicPosition);

                    if (movingAvailable(activeFigure.returnCoordinates(), playingFieldCellArray)) {
                        setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                        updateField(playingFieldCellArray);
                    } else loosingEvent();

                    newFigureMode = randomGenerator.nextInt(7);

                    setFieldCellsEmpty(nextFigure.returnCoordinates(), nextFigureCellArray);
                    nextFigure = dropNewFigure(newFigureMode);
                    nextFigure.setStartPosition(nextFigureFieldBasicPosition);
                    setFieldCellsBusy(nextFigure.returnCoordinates(), nextFigureCellArray);
                    updateField(nextFigureCellArray);
                }

                save(replayOutStream);
                lastTime = now;
            }
        }
    };
    private AnimationTimer autoAnimationTimer = new AnimationTimer() {
        private long lastTime = 0;

        @Override
        public void handle(long now) {

            if ((now - lastTime) >= autoSpeedMode) {
                int newAutoMode = randomGenerator.nextInt(4);
                switch(newAutoMode) {

                    case 0:
                        if (!keyEventProcessing) {
                            keyEventProcessing = true;
                            setFieldCellsEmpty(activeFigure.returnCoordinates(), playingFieldCellArray);
                            if (movingAvailable(activeFigure.figureNextMovingRightPosition(), playingFieldCellArray)) {
                                activeFigure.setNewCoordinates(activeFigure.figureNextMovingRightPosition());
                                setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                                updateField(playingFieldCellArray);
                            } else setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                            keyEventProcessing = false;
                        }
                        break;

                    case 1:
                        if (!keyEventProcessing) {
                            keyEventProcessing = true;
                            setFieldCellsEmpty(activeFigure.returnCoordinates(), playingFieldCellArray);
                            if (movingAvailable(activeFigure.figureNextMovingLeftPosition(), playingFieldCellArray)) {
                                activeFigure.setNewCoordinates(activeFigure.figureNextMovingLeftPosition());
                                setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                                updateField(playingFieldCellArray);
                            } else setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                            keyEventProcessing = false;
                        }
                        break;

                    case 2:
                        if (!keyEventProcessing) {
                            keyEventProcessing = true;
                            setFieldCellsEmpty(activeFigure.returnCoordinates(), playingFieldCellArray);
                            if (movingAvailable(activeFigure.figureNextDroppingPosition(), playingFieldCellArray)) {
                                activeFigure.setNewCoordinates(activeFigure.figureNextDroppingPosition());
                                setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                                updateField(playingFieldCellArray);
                            } else setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                            keyEventProcessing = false;
                        }
                        break;

                    case 3:
                        if (!keyEventProcessing) {
                            keyEventProcessing = true;
                            activeFigure.increasePositionMode();
                            if (activeFigure.returnPositionMode() > activeFigure.returnPositionModeMax())
                                activeFigure.setPositionMode(1);

                            setFieldCellsEmpty(activeFigure.returnCoordinates(), playingFieldCellArray);
                            if (movingAvailable(activeFigure.figureNextRotatePosition(), playingFieldCellArray)) {
                                activeFigure.setNewCoordinates(activeFigure.figureNextRotatePosition());
                                setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                                updateField(playingFieldCellArray);
                            } else {
                                setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                                activeFigure.decreasePositionMode();
                            }
                            keyEventProcessing = false;
                        }
                        break;
                }

                save(replayOutStream);
                lastTime = now;
            }
        }
    };
    private AnimationTimer replayAnimationTimer = new AnimationTimer(){

        private long lastTime = 0;

        @Override
        public void handle(long now) {

            try {
                if ((now - lastTime) >= replaySpeedMode) {
                    if(load(replayInStream) == 0){
                        replayAnimationTimer.stop();
                        root.getChildren().add(loosingLabel);
                        loosingLabel.setText(" THE END");
                        loosingLabel.setVisible(true);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public Playing_Fields(boolean isReplay) {

        gameFieldBasicPosition = new Point(3, 0);
        nextFigureFieldBasicPosition = new Point(0, 0);

        try {
            if (!isReplay) {
                Calendar now = Calendar.getInstance();
                replayOutStream = new DataOutputStream(new FileOutputStream(replayFolderPath +
                        now.get(Calendar.DAY_OF_MONTH) + "_" + now.get(Calendar.HOUR_OF_DAY) + "_" + now.get(Calendar.MINUTE) + "_" +
                        now.get(Calendar.SECOND)));

            }
        } catch (IOException p) {
            p.printStackTrace();
        }

        randomGenerator = new Random();

        root = new Group();
        gameScene = new Scene(root, 570, 620);
        gameScene.getStylesheets().add("Design/ApplicationStyle.css");

        backgroundImage = new ImageView(new Image("for_Tetris/MainMenuBackground.jpg", 570, 620, false, false));
        root.getChildren().add(backgroundImage);

        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(620, 570);
        anchorPane.setLayoutX(0);
        anchorPane.setLayoutY(0);
        root.getChildren().add(anchorPane);


        gameFieldFrame = new Button();
        setButton(9, 4.5, 310, 610, gameFieldFrame, "gameFieldFrame", "");
        autoGameButton = new Button();

        setButton(374, 477, 130, 50, autoGameButton, "Button", "AUTO_PLAYER");
        autoGameButton.setStyle("  -fx-background-color: \n" +
                "     #000000,\n" +
                "     linear-gradient(#4682b4 0%, #1f2429 20%, #191d22 100%),\n" +
                "     linear-gradient(#4682b4, #191d22),\n" +
                "     radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                //  "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 10 20 10 20;" );

        saveGameButton = new Button();
        setButton(374, 417, 130, 50, saveGameButton, "Button", "SAVE");
        saveGameButton.setStyle("  -fx-background-color: \n" +
                "     #000000,\n" +
                "     linear-gradient(#4682b4 0%, #1f2429 20%, #191d22 100%),\n" +
                "     linear-gradient(#4682b4, #191d22),\n" +
                "     radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                //  "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 10 20 10 20;" );

        restartButton = new Button();
        setButton(374, 357, 130, 50, restartButton, "Button", "NEW");
        restartButton.setStyle("  -fx-background-color: \n" +
                "     #000000,\n" +
                "     linear-gradient(#4682b4 0%, #1f2429 20%, #191d22 100%),\n" +
                "     linear-gradient(#4682b4, #191d22),\n" +
                "     radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                //  "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 10 20 10 20;" );

        backToMainButton = new Button();
        setButton(374, 537, 130, 50, backToMainButton, "Button", "MENU");
        backToMainButton.setStyle("  -fx-background-color: \n" +
                "     #000000,\n" +
                "     linear-gradient(#4682b4 0%, #1f2429 20%, #191d22 100%),\n" +
                "     linear-gradient(#4682b4, #191d22),\n" +
                "     radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                //  "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 10 20 10 20;" );

        nextFigureLabel = new Label();
        setLabel(385, 200, nextFigureLabel, "Label", "NEXT FIGURE");
        scoresTextLabel = new Label();
        setLabel(385, 122, scoresTextLabel, "Label", "SCORES");
        scoresScoreLabel = new Label();
        setLabel(385, 148, scoresScoreLabel, "Label", "0");
        topTextLabel = new Label();
        setLabel(385, 48, topTextLabel, "Label", "TOP SCORES");
        topScoreLabel = new Label();
        setLabel(385, 74, topScoreLabel, "Label", "0");
        updateTopScoreLabel();


        loosingLabel = new Label("GAME OVER Т_Т");
        loosingLabel.setLayoutX(61);
        loosingLabel.setLayoutY(175);
        loosingLabel.getStyleClass().add("LoosingLabel");


        playingFieldCellArray = new Playing_Field_cell[20][10];
        nextFigureCellArray = new Playing_Field_cell[2][4];

        for (int i = 0, y = 10; i < 20; i++, y += 30)
            for (int j = 0, x = 14; j < 10; j++, x += 30) {
                Playing_Field_cell playingFieldCell = new Playing_Field_cell();
                playingFieldCell.returnGameFieldCell().setPrefSize(30, 30);
                playingFieldCell.returnGameFieldCell().setLayoutX(x);
                playingFieldCell.returnGameFieldCell().setLayoutY(y);
                root.getChildren().add(playingFieldCell.returnGameFieldCell());
                playingFieldCellArray[i][j] = playingFieldCell;
            }


        for (int i = 0, y = 269; i < 2; i++, y += 30)
            for (int j = 0, x = 391; j < 4; j++, x += 30) {
                Playing_Field_cell playingFieldCell = new Playing_Field_cell();
                playingFieldCell.returnGameFieldCell().setPrefSize(30, 30);
                playingFieldCell.returnGameFieldCell().setLayoutX(x);
                playingFieldCell.returnGameFieldCell().setLayoutY(y);
                root.getChildren().add(playingFieldCell.returnGameFieldCell());
                nextFigureCellArray[i][j] = playingFieldCell;
            }


        autoGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                turnAutoRegime();
            }
        });

        backToMainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                animationTimer.stop();
                autoAnimationTimer.stop();
                replayAnimationTimer.stop();
                try {
                    runWindows.runMainMenu();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                animationTimer.stop();

                try {
                    replayOutStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                restartEvent();
            }
        });



        saveGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    save(new DataOutputStream(new FileOutputStream("gsaves")));
                } catch (IOException p) {
                    p.printStackTrace();
                }
            }

        });

        setKeyboardsEvent();
    }

    public void setButton(double x, double y, double width, double height, Button button, String styleClass,  String text){
        button.setText(text);
        button.setPrefSize(width, height);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.getStyleClass().add(styleClass);
        root.getChildren().add(button);
    }

    public void setLabel(double x, double y, Label label, String styleClass,  String text){
        label.setText(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.getStyleClass().add(styleClass);
        root.getChildren().add(label);
    }

    public Scene getGameFieldScene() {
        return gameScene;
    }

    public void loosingEvent() {

        DataInputStream topInputStream;


        try {
            topInputStream = new DataInputStream(new FileInputStream("topScore"));
            if(topInputStream.readInt()<currentScore){
                DataOutputStream topOutputStream = new DataOutputStream(new FileOutputStream("topScore"));
                topOutputStream.writeInt(currentScore);
                updateTopScoreLabel();
            }

        } catch (FileNotFoundException p) {
            p.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        animationTimer.stop();
        autoAnimationTimer.stop();
        loosingLabel.setText("GAME OVER");
        loosingLabel.setVisible(true);
        root.getChildren().add(loosingLabel);

    }

    public void restartEvent() {

        root.getChildren().remove(loosingLabel);
        scoresScoreLabel.setText("000000");
        currentScore = 0;

        for (int i = 0; i < playingFieldCellArray.length; i++)
            for (int j = 0; j < playingFieldCellArray[i].length; j++) {
                playingFieldCellArray[i][j].setGameFieldCellStatus(false);
            }

        for (int i = 0; i < nextFigureCellArray.length; i++)
            for (int j = 0; j < nextFigureCellArray[i].length; j++) {
                nextFigureCellArray[i][j].setGameFieldCellStatus(false);
            }

        updateField(playingFieldCellArray);
        updateField(nextFigureCellArray);

        try {
            replayOutStream = new DataOutputStream(new FileOutputStream("replay"));
        } catch (IOException p) {
            p.printStackTrace();
        }

        runGame();
    }

    public static void bindWithApp(Main main) {
        runWindows = main;
    }

    public void runReplay(File replayFile){

        try {
            replayInStream = new DataInputStream(new FileInputStream(replayFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        restartButton.setVisible(false);
        saveGameButton.setVisible(false);
        autoGameButton.setVisible(false);
        replayAnimationTimer.start();
    }

    public void startMainAnimationTimer() {
        animationTimer.start();
    }



    public int load(DataInputStream loadStream) throws IOException {


       // if (loadStream.available() < 12) {
         //   return 0;
      //  }

        speedMode = loadStream.readInt();
        tempSpeedMode = loadStream.readInt();
        deletedLinesCount = loadStream.readInt();
        currentScore = loadStream.readInt();
        newFigureMode = loadStream.readInt();

        activeFigure = dropNewFigure(loadStream.readInt());
        activeFigure.setPositionMode(loadStream.readInt());

        for(int i=0; i< activeFigure.returnCoordinates().length; i++) {
            activeFigure.returnCoordinates()[i].setLocation(loadStream.readDouble(),activeFigure.returnCoordinates()[i].getY());
            activeFigure.returnCoordinates()[i].setLocation(activeFigure.returnCoordinates()[i].getX(),loadStream.readDouble());
        }

        nextFigure = dropNewFigure(newFigureMode);

        for(int i=0; i< nextFigure.returnCoordinates().length; i++) {
            nextFigure.returnCoordinates()[i].setLocation(loadStream.readDouble(),nextFigure.returnCoordinates()[i].getY());
            nextFigure.returnCoordinates()[i].setLocation(nextFigure.returnCoordinates()[i].getX(),loadStream.readDouble());
        }

        for(int i=0; i< playingFieldCellArray.length; i++)
            for(int j=0; j< playingFieldCellArray[i].length; j++)
                playingFieldCellArray[i][j].setGameFieldCellStatus(loadStream.readBoolean());

        for(int i=0; i<nextFigureCellArray.length; i++)
            for(int j=0; j<nextFigureCellArray[i].length; j++)
                nextFigureCellArray[i][j].setGameFieldCellStatus(loadStream.readBoolean());

        updateField(playingFieldCellArray);
        updateField(nextFigureCellArray);
        updateLabel(scoresScoreLabel, currentScore);
        updateTopScoreLabel();
        return 1;
    }

    public void runGame(){

        speedMode = defaultSpeedMode;
        deletedLinesCount = 0;
        currentScore = 0;

        activeFigure = dropNewFigure(randomGenerator.nextInt(7));
        activeFigure.setStartPosition(gameFieldBasicPosition);
        setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
        updateField(playingFieldCellArray);

        newFigureMode = randomGenerator.nextInt(7);
        nextFigure = dropNewFigure(newFigureMode);
        nextFigure.setStartPosition(nextFigureFieldBasicPosition);
        setFieldCellsBusy(nextFigure.returnCoordinates(), nextFigureCellArray);
        updateField(nextFigureCellArray);

        try {
            save(new DataOutputStream(new FileOutputStream("gsaves")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        animationTimer.start();
    }

    // Обработчик нажатий на клавиши
    private void setKeyboardsEvent() {
        root.setOnKeyPressed((event) -> {
            switch (event.getCode()) {

                case ENTER:
                    if(speedMode == fastDroppingSpeed)
                        break;
                    tempSpeedMode = speedMode;
                    speedMode = fastDroppingSpeed;
                    break;

                case RIGHT:
                case D:
                    if (!keyEventProcessing) {
                        keyEventProcessing = true;
                        setFieldCellsEmpty(activeFigure.returnCoordinates(), playingFieldCellArray);
                        if (movingAvailable(activeFigure.figureNextMovingRightPosition(), playingFieldCellArray)) {
                            activeFigure.setNewCoordinates(activeFigure.figureNextMovingRightPosition());
                            setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                            updateField(playingFieldCellArray);
                        } else setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                        keyEventProcessing = false;
                    }
                    break;

                case LEFT:
                case A:
                    if (!keyEventProcessing) {
                        keyEventProcessing = true;
                        setFieldCellsEmpty(activeFigure.returnCoordinates(), playingFieldCellArray);
                        if (movingAvailable(activeFigure.figureNextMovingLeftPosition(), playingFieldCellArray)) {
                            activeFigure.setNewCoordinates(activeFigure.figureNextMovingLeftPosition());
                            setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                            updateField(playingFieldCellArray);
                        } else setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                        keyEventProcessing = false;
                    }
                    break;

                case DOWN:
                case S:
                    if (!keyEventProcessing) {
                        keyEventProcessing = true;
                        setFieldCellsEmpty(activeFigure.returnCoordinates(), playingFieldCellArray);
                        if (movingAvailable(activeFigure.figureNextDroppingPosition(), playingFieldCellArray)) {
                            activeFigure.setNewCoordinates(activeFigure.figureNextDroppingPosition());
                            setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                            updateField(playingFieldCellArray);
                        } else setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                        keyEventProcessing = false;
                    }
                    break;

                case SHIFT:
                    if (!keyEventProcessing) {
                        keyEventProcessing = true;
                        activeFigure.increasePositionMode();
                        if (activeFigure.returnPositionMode() > activeFigure.returnPositionModeMax())
                            activeFigure.setPositionMode(1);

                        setFieldCellsEmpty(activeFigure.returnCoordinates(), playingFieldCellArray);
                        if (movingAvailable(activeFigure.figureNextRotatePosition(), playingFieldCellArray)) {
                            activeFigure.setNewCoordinates(activeFigure.figureNextRotatePosition());
                            setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                            updateField(playingFieldCellArray);
                        } else {
                            setFieldCellsBusy(activeFigure.returnCoordinates(), playingFieldCellArray);
                            activeFigure.decreasePositionMode();
                        }
                        keyEventProcessing = false;
                    }

                    break;
            }
        });
    }

    private boolean movingAvailable(Point[] coordinates, Playing_Field_cell[][] cellArray) {

        for (int i = 0; i < coordinates.length; i++) {    //проверяем если падает др фигура или внизу

            if (coordinates[i].getY() > 19 || coordinates[i].getX() < 0 || coordinates[i].getX() > 9 || coordinates[i].getY() < 0) {
                return false;
            }

            if (cellArray[(int) coordinates[i].getY()][(int) coordinates[i].getX()].returnGameFieldCellStatus())
                return false;
        }
        return true;
    }

    private void setFieldCellsBusy(Point[] coordinates, Playing_Field_cell[][] cellArray) {
        for (int i = 0; i < 4; i++)
            cellArray[(int) coordinates[i].getY()][(int) coordinates[i].getX()].setGameFieldCellStatus(true);
    }

    private void setFieldCellsEmpty(Point[] coordinates, Playing_Field_cell[][] cellArray) {
        for (int i = 0; i < 4; i++) {
            cellArray[(int) coordinates[i].getY()][(int) coordinates[i].getX()].setGameFieldCellStatus(false);
        }
    }

    private void updateField(Playing_Field_cell[][] cellArray) {
        for (int i = 0; i < cellArray.length; i++)
            for (int j = 0; j < cellArray[i].length; j++) {
                if (!cellArray[i][j].returnGameFieldCellStatus()) {
                    cellArray[i][j].returnGameFieldCell().getStyleClass().clear();
                    cellArray[i][j].returnGameFieldCell().getStyleClass().add("emptyCell");
                } else {
                    cellArray[i][j].returnGameFieldCell().getStyleClass().clear();
                    cellArray[i][j].returnGameFieldCell().getStyleClass().add("busyCellWhite");
                }
            }
    }

    private Figure dropNewFigure(int mode) {
        switch (mode) {
            // падения наших фигур
            case 0:
                Figure firstFigure = new f1();
                return firstFigure;
            case 1:
                Figure secondFigure = new f2();
                return secondFigure;
            case 2:
                Figure thirdFigure = new f3();
                return thirdFigure;
            case 3:
                Figure fourthFigure = new f4();
                return fourthFigure;
            case 4:
                Figure fifthFigure = new f5();
                return fifthFigure;
            case 5:
                Figure sixthFigure = new f6();
                return sixthFigure;
            case 6:
                Figure seventhFigure = new f7();
                return seventhFigure;
        }
        return new Figure();
    }

    private void processBusyLines(Playing_Field_cell[][] cellArray) {

        int tempDeletedLines = 0;

        for (int i = 0, busyCellsCount; i < cellArray.length ; i++) {

            busyCellsCount = 0;

            for (int j = 0; j < cellArray[i].length; j++)
                if (cellArray[i][j].returnGameFieldCellStatus())
                    busyCellsCount++;                                //количество занятых клеток i в линии

            if (busyCellsCount == cellArray[i].length) {

                for (int g = i; g > 0; g--)
                    for (int j = 0; j < cellArray[g].length; j++)
                        cellArray[g][j].setGameFieldCellStatus(cellArray[g - 1][j].returnGameFieldCellStatus());

                updateField(cellArray);

                tempDeletedLines++;
                deletedLinesCount++;
            }
        }

        if (deletedLinesCount % 8 == 0 && deletedLinesCount != 0) {
            deletedLinesCount = 0;
            speedMode /= 1.2;
        }

        switch (tempDeletedLines) {
            case 1:
                currentScore += 50;
                break;
            case 2:
                currentScore += 100;
                break;
            case 3:
                currentScore += 200;
                break;
            case 4:
                currentScore += 400;
                break;
        }

        updateLabel(scoresScoreLabel, currentScore);
    }

    private void updateLabel(Label label, int currentScore) {

        if (currentScore == 0)
            return;

        int digitsNumber = 0;
        String tempString = "";

        for (int number = currentScore; number > 0; digitsNumber++)
            number /= 10;

        for (int i = 6 - digitsNumber; i > 0; i--)
            tempString += '0';

        tempString += Integer.toString(currentScore);
        label.setText(tempString);
    }

    private void updateTopScoreLabel(){

        DataInputStream topInputStream;
        int topScore;

        try {
            topInputStream = new DataInputStream(new FileInputStream("topScore"));
            topScore = topInputStream.readInt();
            updateLabel(topScoreLabel,topScore);
        } catch (FileNotFoundException p) {
            p.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void turnAutoRegime(){

        if(autoRegime) {
            autoAnimationTimer.stop();
            autoRegime = false;
        } else {
            autoAnimationTimer.start();
            autoRegime = true;
        }
    }

    private void save(DataOutputStream saveStream) {

        try {

            saveStream.writeInt(speedMode);
            saveStream.writeInt(tempSpeedMode);
            saveStream.writeInt(deletedLinesCount);
            saveStream.writeInt(currentScore);
            saveStream.writeInt(newFigureMode);
            saveStream.writeInt(activeFigure.returnFigureType());
            saveStream.writeInt(activeFigure.returnPositionMode());

            for(int i=0;i< activeFigure.returnCoordinates().length; i++) {
                saveStream.writeDouble(activeFigure.returnCoordinates()[i].getX());
                saveStream.writeDouble(activeFigure.returnCoordinates()[i].getY());
            }

            for(int i=0;i< nextFigure.returnCoordinates().length; i++) {
                saveStream.writeDouble(nextFigure.returnCoordinates()[i].getX());
                saveStream.writeDouble(nextFigure.returnCoordinates()[i].getY());
            }

            for(int i=0; i< playingFieldCellArray.length; i++)
                for(int j=0; j< playingFieldCellArray[i].length; j++)
                    saveStream.writeBoolean(playingFieldCellArray[i][j].returnGameFieldCellStatus());

            for(int i=0; i<nextFigureCellArray.length; i++)
                for(int j=0; j<nextFigureCellArray[i].length; j++)
                    saveStream.writeBoolean(nextFigureCellArray[i][j].returnGameFieldCellStatus());


        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}





