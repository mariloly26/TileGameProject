package com.tmge.ui;

import com.tmge.samegame.SameGame;
import com.tmge.tilegames.TileGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SameGameStartController {

    @FXML
    GridPane sameGameStartPane;

    public void startModeSelect(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) sameGameStartPane.getScene().getWindow();

        // load possible modes here but single player for now
        TileGame tileGame = GameLoader.getInstance("samegame");
        SameGame sameGame = (SameGame) tileGame;

        URL resource = getClass().getResource("/samegame/samegame_singleplayer.fxml");
        sameGame.startSinglePlayer(primaryStage, resource);
    }

}
