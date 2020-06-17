package com.tmge.tilegames;

import com.tmge.tilegames.players.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public abstract class TileGame {

    protected TileBoard[] tileBoards;
    protected Player[] players;
    protected Mode mode;

    public abstract void startGame(Stage primaryStage, URL resource) throws IOException;
    public abstract void update();

    public void showGameOver(Stage primaryStage) {
        VBox endRoot = new VBox();
        endRoot.getChildren().add(new Label("Game Over."));
        endRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-font-size: 32px;");
        endRoot.setAlignment(Pos.CENTER);
        endRoot.setPadding(new Insets(20));

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initModality(Modality.NONE);
        popupStage.initOwner(primaryStage);
        popupStage.setScene(new Scene(endRoot, Color.TRANSPARENT));

        popupStage.show();
    }

    public TileBoard[] getTileBoards() {
        return tileBoards;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Mode getMode() {
        return mode;
    }

    public void selectMode() {

    }
}
