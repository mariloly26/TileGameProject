package com.tmge.tetris;

import com.tmge.tilegames.Mode;
import com.tmge.tilegames.TileBoard;
import com.tmge.tilegames.TileGame;
import com.tmge.tilegames.players.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Tetris extends TileGame {
    public Tetris() {
    }

    public Tetris(Mode mode) {
        //single player
        if (mode == Mode.Single) {
            tileBoards = new TetrisBoard[1];
            tileBoards[0] = new TetrisBoard(20, 10);
            tileBoards[0].init();
            players = new Player[1];
            players[0] = new Player(0);
        }
        //multi-player, 2
        else if (mode == Mode.Multi) {
            tileBoards = new TetrisBoard[2];
            players = new Player[2];
            for (int i = 0; i < 2; ++i) {
                tileBoards[i] = new TetrisBoard(20, 10);
                tileBoards[i].init();
                players[i] = new Player(i);
            }
        }
    }

    @Override
    public void startGame(Stage primaryStage, URL resource) throws IOException {
        Parent root = FXMLLoader.load(resource);
        primaryStage.setTitle("Tetris");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public void startSinglePlayer(Stage primaryStage, URL resource) throws IOException {
        tileBoards = new TetrisBoard[1];
        tileBoards[0] = new TetrisBoard(20, 10);
        tileBoards[0].init();
        players = new Player[1];
        players[0] = new Player(0);

        Parent root = FXMLLoader.load(resource);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    @Override
    public void update() {
        for (TileBoard board : tileBoards) {
            board.update();
        }
    }

}
