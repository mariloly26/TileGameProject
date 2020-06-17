package com.tmge.ui;

import com.tmge.samegame.SameGame;
import com.tmge.samegame.SameGameTile;
import com.tmge.samegame.SameGameBoard;
import com.tmge.samegame.SameGameMove;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import com.tmge.ui.GameLoader;
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
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SameGameSinglePlayerController implements Initializable {
    private static final int STARTTIME = 0;
    private static final int TIME_INTERVAL = 1000; // milliseconds
    private Timeline timeline;
    private final IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
    private final IntegerProperty timeMillis = new SimpleIntegerProperty(STARTTIME);
    private SameGame gameInstance = (SameGame) GameLoader.getInstance("samegame");

    @FXML
    GridPane sameGameSinglePlayerPane;

    @FXML
    Label scoreLabel;

    @FXML
    Label timerLabel;

    @FXML
    SplitPane sameGamePane;

    @FXML
    Group sameGamePaneTop;

    @FXML
    Group sameGamePaneBottom;

    private void render() {
        SameGameBoard tileBoard = (SameGameBoard) gameInstance.getTileBoards()[0];
        SameGameTile[][] board = (SameGameTile[][]) gameInstance.getTileBoards()[0].getBoard();
        GridPane sameGameBoardPane = new GridPane();
        for (int i = 0; i < tileBoard.getRowSize(); ++i) {
            for (int j = 0; j < tileBoard.getColSize(); ++j) {
                Circle circle = new Circle();
                circle.setRadius(15);
                if (!board[i][j].isEmpty()) circle.setFill(board[i][j].getColorFill());
                else circle.setFill(Color.DARKGREY);
                GridPane.setRowIndex(circle, i);
                GridPane.setColumnIndex(circle, j);
                sameGameBoardPane.getChildren().add(circle);
                int finalJ = j;
                int finalI = i;
                circle.setOnMouseClicked(event -> handleClick(finalI, finalJ));
                sameGameBoardPane.setBackground(new Background(new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
        sameGamePaneTop.getChildren().clear();
        sameGamePaneTop.getChildren().add(sameGameBoardPane);
        scoreLabel.setText(String.format("Score: %d", tileBoard.total));
    }

    private void handleClick(int row, int col) {
        SameGameBoard tBoard = (SameGameBoard) gameInstance.getTileBoards()[0];
        SameGameMove move = new SameGameMove(row, col);
        tBoard.findMatchClear(move);
        tBoard.dropDown();
        tBoard.calculateScore(tBoard.current_score);
        tBoard.add_total(tBoard.calculateScore(tBoard.current_score));
        if (tBoard.isGameOver()) {
            Stage primaryStage = (Stage) sameGameSinglePlayerPane.getScene().getWindow();
            timeline.stop();
            sameGameSinglePlayerPane.setDisable(true);
            gameInstance.showGameOver(primaryStage);
        }
        render();
    }

    private void updateGameStats() {
        // increment seconds
        int milliseconds = timeMillis.get();
        timeMillis.set(milliseconds + TIME_INTERVAL);
        timeSeconds.set(timeMillis.get() / 1000);
    }

    private void updateState(int timeMillis) {
        updateGameStats();
//        sameGameSinglePlayerPane.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline(new KeyFrame(Duration.millis(TIME_INTERVAL), evt -> updateState(timeMillis.get())));
        timeline.setCycleCount(Animation.INDEFINITE); // repeat over and over again
        timeMillis.set(STARTTIME);
        timeSeconds.set(STARTTIME);

        timeline.play();

        timerLabel.textProperty().bind(
                Bindings.createStringBinding(() -> String.format("%02d:%02d", timeSeconds.intValue() / 60, timeSeconds.intValue() % 60), timeSeconds)
        );

        render();
    }
}
