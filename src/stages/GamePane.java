package stages;

import com.sun.javafx.tk.Toolkit;
import com.sun.org.apache.xpath.internal.operations.Bool;
import core.VoxelMemory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.BoardLogic;
import logic.ComputerPlayer;

import java.util.Optional;

/**
 * Created by Mateusz on 19.01.2017.
 * Project InferenceEngine
 */
public class GamePane extends Pane {
    /**
     *
     * @param initBoard - contains board, instance of BoardLogic from 'logic' package
     * @param gametype: 0 - singlePlayerGame 1 - multiPlayerGame
     */
    public GamePane(BoardLogic initBoard, int gametype) {
        this.board = initBoard;
        this.gamemode = gametype;
        canvas = new Canvas();
        mainGridPane = new GridPane();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        mainGridPane.getColumnConstraints().add(column);
        RowConstraints rowWithGame = new RowConstraints();
        RowConstraints rowWithMenu = new RowConstraints();
        rowWithGame.setPercentHeight(95);
        rowWithMenu.setPercentHeight(5);
        mainGridPane.getRowConstraints().add(rowWithGame);
        mainGridPane.getRowConstraints().add(rowWithMenu);
        mainGridPane.add(canvas, 0, 0);

        getChildren().add(mainGridPane);

        stoneImage = new Image(MainStage.class.getResourceAsStream("resources/stone.png"));
        block0Image = new Image(MainStage.class.getResourceAsStream("resources/block0.png"));
        block1Image = new Image(MainStage.class.getResourceAsStream("resources/block1.png"));
        block2Image = new Image(MainStage.class.getResourceAsStream("resources/block2.png"));
        block3Image = new Image(MainStage.class.getResourceAsStream("resources/block3.png"));
        block4Image = new Image(MainStage.class.getResourceAsStream("resources/block4.png"));
        block5Image = new Image(MainStage.class.getResourceAsStream("resources/block5.png"));
        block6Image = new Image(MainStage.class.getResourceAsStream("resources/block6.png"));
        block7Image = new Image(MainStage.class.getResourceAsStream("resources/block7.png"));
        block8Image = new Image(MainStage.class.getResourceAsStream("resources/block8.png"));
        block9Image = new Image(MainStage.class.getResourceAsStream("resources/block9.png"));
        block10Image = new Image(MainStage.class.getResourceAsStream("resources/block10.png"));
        block11Image = new Image(MainStage.class.getResourceAsStream("resources/block11.png"));

        Label resultsLabel = new Label ("Wyniki: Gracz Pierwszy: 0, Gracz Drugi: 0, Tura: Gracz Pierwszy");
        resultsLabel.setMaxWidth(Double.MAX_VALUE);
        resultsLabel.setAlignment(Pos.CENTER);
        resultsLabel.setFont(Font.font("Comic Sans MS",16));
        mainGridPane.add(resultsLabel,0,1);

        heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                drawFrame();
            }
        });
        widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                drawFrame();
            }
        });


        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getY() < getHeight() * 0.95 && !waiting) {
                    if (!isSelected) {
                        if ((e.getX() > marginX && e.getX() < getWidth() - marginX) && (e.getY() > marginY && e.getY() < getHeight() - marginY)) {
                            double intOneFieldWidth = (oneFieldWidth);
                            double intOneFieldHeight = (oneFieldHeight);

                            for (int i = 0; i < board.width - 1; i++) {
                                if (e.getX() >= (marginX + i * intOneFieldWidth) && e.getX() < marginX + (i + 1) * intOneFieldWidth) {
                                    selectedX = i;
                                    break;
                                }
                                if (e.getX() >= marginX + (board.width - 1) + intOneFieldWidth) {
                                    selectedX = board.width - 1;
                                }
                            }

                            for (int i = 0; i < board.height - 1; i++) {
                                if (e.getY() >= (marginY + i * intOneFieldHeight) && e.getY() < marginY + (i + 1) * intOneFieldHeight) {
                                    selectedY = i;
                                    break;
                                }
                                if (e.getY() >= marginY + (board.height - 1) + intOneFieldHeight) {
                                    selectedY = board.height - 1;
                                }
                            }
                            isSelected = true;
                            board.visibleBoard[selectedY][selectedX] = 1;
                            computerPlayer.addKnownElement(selectedX,selectedY,board.actualBoard[selectedY][selectedX]);
                            drawFrame();
                        }
                    } else {
                        if ((e.getX() > marginX && e.getX() < getWidth() - marginX) && (e.getY() > marginY && e.getY() < getHeight() - marginY)) {
                            double intOneFieldWidth = (oneFieldWidth);
                            double intOneFieldHeight = (oneFieldHeight);

                            secondSelectedX = 0;
                            secondSelectedY = 0;

                            for (int i = 0; i < board.width - 1; i++) {
                                if (e.getX() >= (marginX + i * intOneFieldWidth) && e.getX() < marginX + (i + 1) * intOneFieldWidth) {
                                    secondSelectedX = i;
                                    break;
                                }
                                if (e.getX() >= marginX + (board.width - 1) + intOneFieldWidth) {
                                    secondSelectedX = board.width - 1;
                                }
                            }

                            for (int i = 0; i < board.height - 1; i++) {
                                if (e.getY() >= (marginY + i * intOneFieldHeight) && e.getY() < marginY + (i + 1) * intOneFieldHeight) {
                                    secondSelectedY = i;
                                    break;
                                }
                                if (e.getY() >= marginY + (board.height - 1) + intOneFieldHeight) {
                                    secondSelectedY = board.height - 1;
                                }
                            }

                            if(secondSelectedY!=selectedY || secondSelectedX!=selectedX) {
                                waiting = true;
                                board.visibleBoard[secondSelectedY][secondSelectedX] = 1;
                                computerPlayer.addKnownElement(secondSelectedX,secondSelectedY,board.actualBoard[secondSelectedY][secondSelectedX]);
                                drawFrame();
                                isSelected = false;
                                checkMoveTask = new Task<Boolean>() {
                                    @Override
                                    protected Boolean call() throws Exception {
                                        if (board.actualBoard[selectedY][selectedX] == board.actualBoard[secondSelectedY][secondSelectedX]) {
                                            return true;
                                        }
                                        else {
                                            Thread.sleep(1000);
                                            return false;
                                        }
                                    }
                                };
                                Thread checkMoveThread = new Thread(checkMoveTask);
                                checkMoveThread.start();
                                checkMoveTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                    @Override
                                    public void handle(WorkerStateEvent event) {
                                        if (!checkMoveTask.getValue()) {
                                            board.visibleBoard[selectedY][selectedX] = 0;
                                            board.visibleBoard[secondSelectedY][secondSelectedX] = 0;
                                        } else {
                                            computerPlayer.removeKnownElement(selectedX,selectedY,board.actualBoard[selectedY][selectedX]);
                                            computerPlayer.removeKnownElement(secondSelectedX,secondSelectedY,board.actualBoard[secondSelectedY][secondSelectedX]);
                                            if (player == 1) {
                                                firstPlayerScore++;
                                            } else {
                                                secondPlayerScore++;
                                            }
                                            checkNoMoves();
                                        }
                                        if (player == 1) {
                                            resultsLabel.setText("Wyniki: Gracz Pierwszy: " + firstPlayerScore + ", Gracz Drugi: " + secondPlayerScore +", Tura: Gracz Drugi");
                                            player = 2;
                                        }
                                        else {
                                            resultsLabel.setText("Wyniki: Gracz Pierwszy: " + firstPlayerScore + ", Gracz Drugi: " + secondPlayerScore + ", Tura: Gracz Pierwszy");
                                            player = 1;
                                        }
                                        drawFrame();
                                        if(gamemode==1) {
                                            waiting=false;
                                        }
                                        else {
                                            Task<Boolean> computerMoveTask = new Task<Boolean>() {
                                                @Override
                                                protected Boolean call() throws Exception {
                                                    move1 = computerPlayer.getFirstMove(board);
                                                    board.visibleBoard[move1[1]][move1[0]] = 1;
                                                    drawFrame();
                                                    move2 = computerPlayer.getSecondMove(board);
                                                    board.visibleBoard[move2[1]][move2[0]] = 1;
                                                    drawFrame();
                                                    if(board.actualBoard[move1[1]][move1[0]] == board.actualBoard[move2[1]][move2[0]]) {
                                                        return true;
                                                    }
                                                    else {
                                                        Thread.sleep(1000);
                                                        return false;
                                                    }
                                                }
                                            };

                                            Thread computerMoveThread = new Thread(computerMoveTask);
                                            computerMoveThread.start();
                                            computerMoveTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                                @Override
                                                public void handle(WorkerStateEvent event) {
                                                    if(!computerMoveTask.getValue()) {
                                                        board.visibleBoard[move2[1]][move2[0]] = 0;
                                                        board.visibleBoard[move1[1]][move1[0]] = 0;
                                                    } else {
                                                        computerPlayer.removeKnownElement(move2[0],move2[1],board.actualBoard[move2[1]][move2[0]]);
                                                        computerPlayer.removeKnownElement(move1[0],move1[1],board.actualBoard[move1[1]][move1[0]]);
                                                        secondPlayerScore++;
                                                        resultsLabel.setText("Wyniki: Gracz Pierwszy: " + firstPlayerScore + ", Gracz Drugi: " + secondPlayerScore +", Tura: Gracz Drugi");
                                                        resultsLabel.setText("Wyniki: Gracz Pierwszy: " + firstPlayerScore + ", Gracz Drugi: " + secondPlayerScore + ", Tura: Gracz Pierwszy");
                                                        checkNoMoves();
                                                    }
                                                    drawFrame();
                                                    waiting=false;
                                                    player=1;
                                                }
                                            });

                                        }
                                    }
                                });
                            }
                        }
                        drawFrame();
                    }
                }
            }
        });


    }
    private void checkNoMoves(){
        if(!board.anyMove()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getDialogPane().getStylesheets().add(VoxelMemory.mainStage.Theme);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/pick_iron.png")));
            alert.setTitle("Koniec gry");
            if(gamemode==1 && firstPlayerScore<secondPlayerScore)
                alert.setHeaderText("Koniec ruchów, wygrał gracz drugi, co chcesz zrobić?");
            if(gamemode==1 && firstPlayerScore>secondPlayerScore)
                alert.setHeaderText("Koniec ruchów, wygrał gracz pierwszy, co chcesz zrobić?");
            if(gamemode==1 && firstPlayerScore==secondPlayerScore)
                alert.setHeaderText("Koniec ruchów, remis, co chcesz zrobić?");
            if(gamemode==0 && firstPlayerScore<secondPlayerScore)
                alert.setHeaderText("Koniec ruchów, wygrał komputer, co chcesz zrobić?");
            if(gamemode==0 && firstPlayerScore>secondPlayerScore)
                alert.setHeaderText("Koniec ruchów, wygrałeś, co chcesz zrobić?");
            if(gamemode==0 && firstPlayerScore==secondPlayerScore)
                alert.setHeaderText("Koniec ruchów, remis, co chcesz zrobić?");
            alert.setContentText("Możesz wrócić do menu głównego, bądź zagrać jeszcze raz");
            ButtonType buttonYes = new ButtonType("Powrót do Menu");
            ButtonType buttonNo = new ButtonType("Ponowna Gra");
            alert.getButtonTypes().setAll(buttonNo, buttonYes);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonYes) {
                VoxelMemory.mainStage.backToMenu();
            }
            else {
                board.resetBoard();
                player=1;
                firstPlayerScore=0;
                secondPlayerScore=0;
                drawFrame();
            }
        }
    }

    void drawFrame() {
        try {
            int[] rozmiar = VoxelMemory.mainStage.getSizeAsArray();
            int width = rozmiar[0];
            int height = (int) mainGridPane.getRowConstraints().get(0).getPercentHeight() * rozmiar[1] / 100;
            marginX=20.0;
            marginY=20.0;
            if (width > height) {
                double difference = (width - height);
                if (difference / 2.0 > 20)
                    marginX = difference / 2.0;
                else
                    marginX = 20;
            } else {
                marginX = 20;
            }
            if (height > width) {
                double difference = (height - width);
                if (difference / 2.0 > 20)
                    marginY = difference / 2.0;
                else
                    marginY = 20;
            } else
                marginY = 20;
            canvas.setHeight(height);
            canvas.setWidth(width);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, width, height);

            oneFieldWidth = (width - marginX * 2.0) / (board.getWidht() * 1.0);
            oneFieldHeight = (height - marginY * 2.0) / (board.getHeight() * 1.0);

            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeRect(marginX,marginY,width-marginX*2.0,height - marginY*2.0);

            for (int i = 0; i < board.getHeight(); i++) {
                for (int j = 0; j < board.getWidht(); j++) {
                    if(board.visibleBoard[i][j]==0) {
                        gc.drawImage(stoneImage, j * (oneFieldWidth) + marginX, i * (oneFieldHeight) + marginY, oneFieldWidth, oneFieldHeight);
                    }
                    if(board.visibleBoard[i][j]==1) {
                        if(board.actualBoard[i][j]==0)
                            gc.drawImage(block0Image, j * (oneFieldWidth) + marginX, i * (oneFieldHeight) + marginY, oneFieldWidth, oneFieldHeight);
                        if(board.actualBoard[i][j]==1)
                            gc.drawImage(block1Image, j * (oneFieldWidth) + marginX, i * (oneFieldHeight) + marginY, oneFieldWidth, oneFieldHeight);
                        if(board.actualBoard[i][j]==2)
                            gc.drawImage(block2Image, j * (oneFieldWidth) + marginX, i * (oneFieldHeight) + marginY, oneFieldWidth, oneFieldHeight);
                        if(board.actualBoard[i][j]==3)
                            gc.drawImage(block3Image, j * (oneFieldWidth) + marginX, i * (oneFieldHeight) + marginY, oneFieldWidth, oneFieldHeight);
                        if(board.actualBoard[i][j]==4)
                            gc.drawImage(block4Image, j * (oneFieldWidth) + marginX, i * (oneFieldHeight) + marginY, oneFieldWidth, oneFieldHeight);
                        if(board.actualBoard[i][j]==5)
                            gc.drawImage(block5Image, j * (oneFieldWidth) + marginX, i * (oneFieldHeight) + marginY, oneFieldWidth, oneFieldHeight);
                        if(board.actualBoard[i][j]==6)
                            gc.drawImage(block6Image, j * (oneFieldWidth) + marginX, i * (oneFieldHeight) + marginY, oneFieldWidth, oneFieldHeight);
                        if(board.actualBoard[i][j]==7)
                            gc.drawImage(block7Image, j * (oneFieldWidth) + marginX, i * (oneFieldHeight) + marginY, oneFieldWidth, oneFieldHeight);
                        if(board.actualBoard[i][j]==8)
                            gc.drawImage(block8Image, j * (oneFieldWidth) + marginX, i * (oneFieldHeight) + marginY, oneFieldWidth, oneFieldHeight);
                        if(board.actualBoard[i][j]==9)
                            gc.drawImage(block9Image, j * (oneFieldWidth) + marginX, i * (oneFieldHeight) + marginY, oneFieldWidth, oneFieldHeight);
                        if(board.actualBoard[i][j]==10)
                            gc.drawImage(block10Image, j * (oneFieldWidth) + marginX, i * (oneFieldHeight) + marginY, oneFieldWidth, oneFieldHeight);
                        if(board.actualBoard[i][j]==11)
                            gc.drawImage(block11Image, j * (oneFieldWidth) + marginX, i * (oneFieldHeight) + marginY, oneFieldWidth, oneFieldHeight);
                    }
                }
            }

            for (int i = 0; i < board.getHeight(); i++) {
                if (i > 0) {
                    gc.setStroke(javafx.scene.paint.Color.BLACK);
                    gc.strokeLine(marginX, marginY + i * oneFieldHeight, width - marginX, marginY + i * oneFieldHeight);
                }

                for (int j = 0; j < board.getWidht(); j++) {
                    if (j > 0) {
                        gc.setStroke(javafx.scene.paint.Color.BLACK);
                        gc.strokeLine(marginX + j * oneFieldWidth, marginY, marginX + j * oneFieldWidth, height - marginY);
                    }
                }
            }




        }
        catch (Exception e){
        }
    }

    ComputerPlayer computerPlayer = new ComputerPlayer();

    private BoardLogic board;
    private int gamemode;

    private Canvas canvas = new Canvas();
    private GridPane mainGridPane;

    private double oneFieldWidth;
    private double oneFieldHeight;

    private double marginX;
    private double marginY;

    private Image stoneImage;
    private Image block0Image;
    private Image block1Image;
    private Image block2Image;
    private Image block3Image;
    private Image block4Image;
    private Image block5Image;
    private Image block6Image;
    private Image block7Image;
    private Image block8Image;
    private Image block9Image;
    private Image block10Image;
    private Image block11Image;

    int selectedX;
    int selectedY;

    int secondSelectedX;
    int secondSelectedY;


    boolean isSelected = false;

    boolean waiting = false;

    private Task<Boolean> checkMoveTask;

    int firstPlayerScore = 0;
    int secondPlayerScore = 0;

    int player = 1;

    int move1[];
    int move2[];
}
