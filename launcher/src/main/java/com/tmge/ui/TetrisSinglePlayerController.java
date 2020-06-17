package com.tmge.ui;

import javafx.stage.Stage;
import com.tmge.tetris.Tetris;
import com.tmge.tetris.TetrisBoard;
import com.tmge.tetris.TetrisTile;
import com.tmge.tilegames.TileBoard;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class TetrisSinglePlayerController implements Initializable {
    private static final int STARTTIME = 0;
    private static final int TIME_INTERVAL = 100; // milliseconds
    private Timeline timeline;
    private final IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
    private final IntegerProperty timeMillis = new SimpleIntegerProperty(STARTTIME);
    private Tetris gameInstance = (Tetris) GameLoader.getInstance("tetris");

    @FXML
    GridPane tetrisSinglePlayerPane;

    @FXML
    Label scoreLabel;

    @FXML
    Label speedLabel;

    @FXML
    Label timerLabel;

    @FXML
    SplitPane tetrisGamePane;

    @FXML
    Group tetrisGamePaneLeft;

    @FXML
    AnchorPane tetrisGamePaneRight;

    @FXML
    Group holdingArea;

    private void render() {
        TileBoard tileBoard = gameInstance.getTileBoards()[0];
        TetrisTile[][] board = (TetrisTile[][]) gameInstance.getTileBoards()[0].getBoard();
        GridPane tetrisBoardPane = new GridPane();
        for (int i = 0; i < tileBoard.getRowSize(); ++i) {
            for (int j = 0; j < tileBoard.getColSize(); ++j) {
                Rectangle rect = new Rectangle();
                rect.setWidth(20);
                rect.setHeight(20);
                rect.setStroke(Color.DARKGREY);
                if (board[i][j].isOccupied || board[i][j].isInPiece != 0) rect.setFill(board[i][j].color);
                else rect.setFill(Color.BLACK);
                GridPane.setRowIndex(rect, i);
                GridPane.setColumnIndex(rect, j);
                tetrisBoardPane.getChildren().addAll(rect);
            }
        }
        tetrisGamePaneLeft.getChildren().clear();
        tetrisGamePaneLeft.getChildren().add(tetrisBoardPane);

    }

    private void updateGameStats(int milliseconds, TetrisBoard tileBoard) {
        // increment seconds
        timeMillis.set(milliseconds + TIME_INTERVAL);
        timeSeconds.set(milliseconds / 1000);

        speedLabel.setText(String.format("Speed: %d", tileBoard.getSpeed()));
        scoreLabel.setText(String.format("Score: %d", tileBoard.getScores()));
    }

    private void updateState(int milliseconds) {
        TetrisBoard tileBoard = (TetrisBoard) gameInstance.getTileBoards()[0];
        updateGameStats(milliseconds, tileBoard);

        int divisor = 1000 - tileBoard.getSpeed() * 100;
        divisor = (divisor > 0) ? divisor : 1;
        if (milliseconds % divisor == 0) {
            gameInstance.update();

            if (((TetrisBoard) gameInstance.getTileBoards()[0]).isGameOver()) {
                Stage primaryStage = (Stage) tetrisSinglePlayerPane.getScene().getWindow();
                timeline.stop();
                tetrisSinglePlayerPane.setDisable(true);
                gameInstance.showGameOver(primaryStage);
            }
            render();
        }
        tetrisSinglePlayerPane.requestFocus();
    }

    private void drawHoldingArea() {
        TetrisTile[][] board = ((TetrisBoard) gameInstance.getTileBoards()[0]).getHoldingArea();
        GridPane holdingAreaGridPane = new GridPane();
        for (int i = 0; i < board.length; ++i) {
            for (int j = 2; j < 7; ++j) {
                Rectangle rect = new Rectangle();
                rect.setWidth(20);
                rect.setHeight(20);
                rect.setStroke(Color.DARKGREY);
//                if (board[i][j].isOccupied || board[i][j].isInPiece != 0) rect.setFill(Color.WHITESMOKE);
                if (board[i][j].isOccupied || board[i][j].isInPiece != 0) rect.setFill(board[i][j].color);
                else rect.setFill(Color.BLACK);
                GridPane.setRowIndex(rect, i);
                GridPane.setColumnIndex(rect, j);
                holdingAreaGridPane.getChildren().addAll(rect);
            }
        }
        holdingArea.getChildren().clear();
        holdingArea.getChildren().add(holdingAreaGridPane);
    }

    @FXML
    private void handleOnKeyPressed(KeyEvent event) {
        TileBoard tileBoard = gameInstance.getTileBoards()[0];
        TetrisBoard tetrisBoard = (TetrisBoard) tileBoard;
        if (event.getCode().equals(KeyCode.UP)) {
            tetrisBoard.rotate(true);
        } else if (event.getCode().equals(KeyCode.DOWN)) {
            tetrisBoard.rotate(false);
        } else if (event.getCode().equals(KeyCode.LEFT)) {
            tetrisBoard.move(true);
        } else if (event.getCode().equals(KeyCode.RIGHT)) {
            tetrisBoard.move(false);
        } else if (event.getCode().equals(KeyCode.SPACE)) {
            gameInstance.update();
        } else if (event.getCode().equals(KeyCode.SHIFT)) {
            tetrisBoard.switchHolding();
            drawHoldingArea();
        }
        render();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        primaryStage = (Stage) timerLabel.getScene().getWindow();
        timeline = new Timeline(new KeyFrame(Duration.millis(TIME_INTERVAL), evt -> updateState(timeMillis.get())));
        timeline.setCycleCount(Animation.INDEFINITE); // repeat over and over again
        timeMillis.set(STARTTIME);
        timeSeconds.set(STARTTIME);

        timeline.play();

        timerLabel.textProperty().bind(
                Bindings.createStringBinding(() -> String.format("%02d:%02d", timeSeconds.intValue() / 60, timeSeconds.intValue() % 60), timeSeconds)
        );

        drawHoldingArea();
    }
}
