package com.tmge.ui;

import com.tmge.tetris.Tetris;
import com.tmge.tilegames.TileGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class TetrisStartController {

    @FXML
    GridPane tetrisStartPane;

    public void startModeSelect(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) tetrisStartPane.getScene().getWindow();

        // load possible modes here but single player for now
        TileGame tileGame = GameLoader.getInstance("tetris");
        Tetris tetris = (Tetris) tileGame;

        URL resource = getClass().getResource("/tetris/tetris_singleplayer.fxml");
        tetris.startSinglePlayer(primaryStage, resource);
    }

}
