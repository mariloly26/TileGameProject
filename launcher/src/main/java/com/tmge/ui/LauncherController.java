package com.tmge.ui;

import com.tmge.tilegames.TileGame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class LauncherController {
    @FXML
    GridPane mainGridPane;

    @FXML
    private Button startBtn;

    private String[] gameList = {"Tetris", "SameGame"};

    public void loadGames(ActionEvent actionEvent) {
        mainGridPane.getChildren().remove(startBtn);
        int ii = 3;
        for (String game: gameList) {
            final Button btn = new Button(game);

            btn.setId("btn" + game);
            btn.setAlignment(Pos.CENTER);
            btn.setTextAlignment(TextAlignment.CENTER);
            btn.setPrefHeight(27);
            btn.setPrefWidth(172);

            // load game on button click
            EventHandler<ActionEvent> event = e -> {
                TileGame gameInstance = GameLoader.getInstance(game);
                Stage primaryStage = (Stage) mainGridPane.getScene().getWindow();
                try {
                    String filename = "/" + game.toLowerCase() + "/" + game.toLowerCase() + "_start.fxml";
                    URL resource = getClass().getResource(filename);
                    gameInstance.startGame(primaryStage, resource);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            };

            btn.setOnAction(event);

            mainGridPane.add(btn, 0, ii++);
            mainGridPane.setHalignment(btn, HPos.CENTER);
        }
    }
}
