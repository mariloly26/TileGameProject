package com.tmge.samegame;

import com.tmge.tilegames.TileGame;
import com.tmge.tilegames.Mode;
import com.tmge.tilegames.players.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SameGame extends TileGame {
//    protected SameGameBoard tileBoards[];

    public SameGame() {
    }

    public SameGame(Mode mode) {

    }

    @Override
    public void startGame(Stage primaryStage, URL resource) throws IOException {
        Parent root = FXMLLoader.load(resource);
        primaryStage.setTitle("SameGame");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public void startSinglePlayer(Stage primaryStage, URL resource) throws IOException {
        tileBoards = new SameGameBoard[1];
        tileBoards[0] = new SameGameBoard(12, 20);
        tileBoards[0].init();
        players = new Player[1];
        players[0] = new Player(0);

        Parent root = FXMLLoader.load(resource);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    @Override
	public void update() {
		// do nothing
	}
}