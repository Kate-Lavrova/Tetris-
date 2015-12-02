
package Game;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Replay {


    static private Main runWindows;

    private static final String replayFolderPath = "C:\\Users\\SONY\\Desktop\\67\\Tetris\\replayFolder\\";

    private Scene replayScene;
    private ImageView backgroundImage;
    private Group root;
    private AnchorPane anchorPane;

    private Button backToMainButton;
    private Button loadReplayButton;
    private Button sortByJavaButton;
    private Button sortByScalaButton;
    private Button figureStatisticButton;
    private Button scoreStatisticButton;
    private Button findSequenceButton;

    private ListView replayListView;
    private ObservableList observableList;
    private HashMap hashMap;
    private File[] files;
    private int filesSizeArray[];
    private ArrayList figureTypeStatistic; //****************
    private ArrayList scoreStatistic;   //*****************

    public Replay() {

        root = new Group();
        replayScene = new Scene(root, 570, 620);
        replayScene.getStylesheets().add("Design/ApplicationStyle.css");

        backgroundImage = new ImageView(new javafx.scene.image.Image("for_Tetris/fon2.jpg", 570, 620, false, false));
        root.getChildren().add(backgroundImage);

        observableList = FXCollections.observableArrayList();
        // *************************************************************************************
        figureTypeStatistic = new ArrayList<Integer>();
        scoreStatistic = new ArrayList<Integer>();
        // *************************************************************************************

        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(620, 570);
        anchorPane.setLayoutX(0);
        anchorPane.setLayoutY(0);
        root.getChildren().add(anchorPane);

        replayListView = new ListView();
        replayListView.setLayoutX(51);
        replayListView.setLayoutY(25);
        replayListView.setPrefHeight(300);
        replayListView.setPrefWidth(470);
        root.getChildren().add(replayListView);

        loadReplayButton = new Button();
        setButton(200, 330, 180, 45, loadReplayButton, "Button", "LOAD REPLAY");
        sortByJavaButton = new Button();
        setButton(51, 390, 180, 45, sortByJavaButton, "Button", "BY JAVA");
        sortByScalaButton = new javafx.scene.control.Button();
        setButton(51, 450, 180, 45, sortByScalaButton, "Button", "BY SCALA");

        figureStatisticButton = new Button();
        setButton(370, 390, 180, 45, figureStatisticButton, "Button", "FIGURE STAT");
        scoreStatisticButton = new javafx.scene.control.Button();
        setButton(370, 450, 180, 45, scoreStatisticButton, "Button", "SCORE STAT");

        backToMainButton = new javafx.scene.control.Button();
        setButton(200, 558, 180, 45, backToMainButton, "Button", "MENU");
        findSequenceButton = new javafx.scene.control.Button();
        setButton(370, 510, 180, 45, findSequenceButton, "Button", "FIND SEQUENCE");

        backToMainButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    runWindows.runMainMenu();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        loadReplayButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("");
                    File file = fileChooser.showOpenDialog(runWindows.getStage());
                    if (file != null) {
                        runWindows.runReplay(file);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        sortByJavaButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                sortListByJava();
            }
        });

        sortByScalaButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                sortListByScala();
            }
        });


        // *************************************************************************************
        figureStatisticButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                countFigureStatistic();
            }
        });

        scoreStatisticButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                countScoreStatistic();
            }
        });
        // *************************************************************************************

        findSequenceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                findSequence();
            }
        });

    }

    public void setButton(double x, double y, double width, double height, javafx.scene.control.Button button, String styleClass,  String text){
        button.setText(text);
        button.setPrefSize(width, height);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.getStyleClass().add(styleClass);
        root.getChildren().add(button);
    }

    public static void bindWithApp(Main main) {
        runWindows = main;
    }

    public Scene getReplayScene() {
        return replayScene;
    }

    public void countStatistic() throws IOException {

        DataInputStream loadStream;
        getFilesInfo();

        for (int i = 0; i < files.length; i++) {

            loadStream = new DataInputStream(new FileInputStream(files[i]));

            while (loadStream.available() > 12) {


                loadStream.readInt();
                loadStream.readInt();
                loadStream.readInt();
                // *************************************************************************************
                scoreStatistic.add(loadStream.readInt());
                loadStream.readInt();
                figureTypeStatistic.add(loadStream.readInt());
                loadStream.readInt();
                // *************************************************************************************

                for(int j=0; j < 16; j++) {
                    loadStream.readDouble();
                }

                for(int m=0; m<208; m++) {
                    loadStream.readBoolean();
                }


            }
        }
    }

    public void getFilesInfo() {

        File myFolder = new File(replayFolderPath);
        files = myFolder.listFiles();

        int i;
        hashMap = new HashMap<Integer,String >();
        for(i=0;i<files.length;i++) {
            hashMap.put((int)files[i].length(), files[i].getName());
            observableList.add("      " + files[i].getName() + " / " + files[i].length());
        }

        filesSizeArray = new int[i];
        for(i=0;i<filesSizeArray.length;i++){
            filesSizeArray[i] = (int)files[i].length();
        }

        replayListView.setItems(observableList);

    }

    public void sortListByJava() {

        long tStart = System.currentTimeMillis();

        Arrays.sort(filesSizeArray);

        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        System.out.println(" " + tDelta/1000.0);

        refreshList();
    }

    public void sortListByScala() {
        long tStart = System.currentTimeMillis();

        filesSizeArray = aza.sort((filesSizeArray));

        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        System.out.println("" + tDelta/1000.0);

        refreshList();
    }

    public void refreshList() {
        replayListView.getItems().clear();
        for(int i=0; i<filesSizeArray.length;i++){
            observableList.add("     " + hashMap.get(filesSizeArray[i]) +
                    " / " + filesSizeArray[i]);
        }
    }


    private void countFigureStatistic(){
        int[] ms = new int[figureTypeStatistic.size()];
        for(int i=0;i<figureTypeStatistic.size();i++)
            ms[i] = (int)figureTypeStatistic.get(i);
        observableList.add(" Figure Type: " + (Statistic6.countFigureStatistic(ms)));
        System.out.println(" Figure Type: " + (Statistic6.countFigureStatistic(ms)));
    }


    private void countScoreStatistic(){

        int[] ms = new int[scoreStatistic.size()];
        for(int i=0;i<scoreStatistic.size();i++)
            ms[i] = (int)scoreStatistic.get(i);


        observableList.add(" Score: " + (int) Statistic6.countScoreStatistic(ms));
        System.out.println(" Score: " + (int) Statistic6.countScoreStatistic(ms));
    }


    private void findSequence(){
        System.out.println("Most common sequence" + sem.findMostCommonSequence((figureTypeStatistic)));

        observableList.add("Most common sequence" + sem.findMostCommonSequence((figureTypeStatistic)));
    }



}
