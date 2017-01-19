package stages;

import core.VoxelMemory;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import logic.BoardLogic;

import java.util.Optional;

/**
 * Created by Mateusz on 19.01.2017.
 * Project InferenceEngine
 */
public class MainStage extends Application {
    public void start(Stage primaryStage) {
        BorderPane mainBorderPane = new BorderPane();
        GridPane mainGridPane = new GridPane();
        RowConstraints rowInMainMenu = new RowConstraints();
        rowInMainMenu.setPercentHeight(20);
        RowConstraints logoRowInMainMenu = new RowConstraints();
        logoRowInMainMenu.setPercentHeight(55);
        RowConstraints downRowInMainMenu = new RowConstraints();
        logoRowInMainMenu.setPercentHeight(55);

        mainGridPane.getRowConstraints().addAll(logoRowInMainMenu,rowInMainMenu,rowInMainMenu,downRowInMainMenu);


        ColumnConstraints columnInMainMenu = new ColumnConstraints();
        columnInMainMenu.setPercentWidth(100);
        mainGridPane.getColumnConstraints().add(columnInMainMenu);

        HBox voxelMemoryTitleHBox = new HBox();
        voxelMemoryTitleHBox.setAlignment(Pos.CENTER);
        voxelMemoryTitleLabel = new Text();
        voxelMemoryTitleLabel.setText("Voxel \nMemory");
        voxelMemoryTitleLabel.setId("logo");
        voxelMemoryTitleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 120));
        voxelMemoryTitleHBox.getChildren().add(voxelMemoryTitleLabel);
        mainGridPane.add(voxelMemoryTitleHBox,0,0);

        HBox singlePlayerGameButtonHBox = new HBox();
        singlePlayerGameButtonHBox.setAlignment(Pos.CENTER);
        singlePlayerGameButton = new Button("Gra Jednoosobowa");
        singlePlayerGameButton.setId("buttonInMainMenu");
        singlePlayerGameButton.setMaxWidth(300);
        singlePlayerGameButton.setMaxHeight(180);
        singlePlayerGameButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,25));
        singlePlayerGameButtonHBox.getChildren().add(singlePlayerGameButton);
        HBox.setMargin(singlePlayerGameButton, new Insets(10, 0, 10, 0));
        HBox.setHgrow(singlePlayerGameButton, Priority.ALWAYS);
        singlePlayerGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().getStylesheets().add(Theme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/stone.png")));
                alert.setTitle("Błąd uruchamiania gry");
                alert.setHeaderText("Gra z komputerem zostanie dopisana wkrótce");
                alert.setContentText("Możesz zagrać w grę dwuosobową");
                ButtonType buttonYes = new ButtonType("Ok");
                alert.getButtonTypes().setAll(buttonYes);
                alert.showAndWait();
            }
        });
        mainGridPane.add(singlePlayerGameButtonHBox,0,1);

        HBox twoPlayersGameButtonHBox = new HBox();
        twoPlayersGameButtonHBox.setAlignment(Pos.CENTER);
        twoPlayersGameButton = new Button("Gra Dwuosobowa");
        twoPlayersGameButton.setId("buttonInMainMenu");
        twoPlayersGameButton.setMaxWidth(300);
        twoPlayersGameButton.setMaxHeight(180);
        twoPlayersGameButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD,25));
        twoPlayersGameButtonHBox.getChildren().add(twoPlayersGameButton);
        HBox.setMargin(twoPlayersGameButton, new Insets(10, 0, 10, 0));
        HBox.setHgrow(twoPlayersGameButton, Priority.ALWAYS);
        twoPlayersGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BoardLogic boardLogic = new BoardLogic(6,5);
                GamePane gamePane = new GamePane(boardLogic,1);
                sceneOfTheGame = new Scene(gamePane,mainScene.getWidth(),mainScene.getHeight());
                sceneOfTheGame.getStylesheets().addAll(Theme);
                mainStage.setScene(sceneOfTheGame);
                gamePane.drawFrame();

                sceneOfTheGame.setOnKeyReleased(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        if(event.getCode() == KeyCode.ESCAPE) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.getDialogPane().getStylesheets().add(Theme);
                            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                            alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/stone.png")));
                            alert.setTitle("Potwierdzenie Wyjścia");
                            alert.setHeaderText("Chcesz wrócić do menu głównego?");
                            alert.setContentText("Obecna rozgrywka nie zostanie zapisana.");
                            ButtonType buttonYes = new ButtonType("Tak");
                            ButtonType buttonNo = new ButtonType("Anuluj");
                            alert.getButtonTypes().setAll(buttonNo, buttonYes);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.isPresent() && result.get() == buttonYes) {
                                backToMenu();
                            }
                        }
                    }
                });

            }
        });
        mainGridPane.add(twoPlayersGameButtonHBox,0,2);

        Label programInfoLabel = new Label();
        programInfoLabel.setMaxWidth(Double.MAX_VALUE);
        programInfoLabel.setAlignment(Pos.CENTER);
        programInfoLabel.setText("Wersja: 0.1 pre-alpha");
        programInfoLabel.setTextAlignment(TextAlignment.CENTER);

        mainGridPane.add(programInfoLabel,0,3);

        mainBorderPane.setCenter(mainGridPane);
        mainScene = new Scene(mainBorderPane,500,500);
        mainStage =new Stage();
        mainStage.setTitle("Voxel Memory");
        mainStage.setScene(mainScene);
        mainStage.setWidth(750);
        mainStage.setHeight(650);

        mainStage.setResizable(false);

        mainScene.getStylesheets().add(Theme);

        mainStage.setMinWidth(750);
        mainStage.setMinHeight(650);

        mainStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/stone.png")));

        mainStage.show();

        mainStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                singlePlayerGameButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, newValue.doubleValue() / 25));
                twoPlayersGameButton.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, newValue.doubleValue() / 25));
            }
        });

    }
    Scene sceneOfTheGame;
    int[] getSizeAsArray() throws NullPointerException {
        int[] size = new int[2];
        if (VoxelMemory.mainStage.sceneOfTheGame == null) {
            throw new NullPointerException();
        }
        size[0] = (int) VoxelMemory.mainStage.sceneOfTheGame.getWidth();
        size[1] = (int) VoxelMemory.mainStage.sceneOfTheGame.getHeight();
        return size;
    }


    void backToMenu(){
        double width = mainStage.getWidth();
        double height = mainStage.getHeight();
        mainStage.setScene(mainScene);
        mainStage.setWidth(width);
        mainStage.setHeight(height);
    }

    public String Theme = MainStage.class.getResource("styles/normalstyle.css").toExternalForm();

    private Button singlePlayerGameButton;
    private Button twoPlayersGameButton;

    private Stage mainStage;
    private Scene mainScene;
    private Text voxelMemoryTitleLabel;

}

