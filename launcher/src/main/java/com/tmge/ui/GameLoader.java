package com.tmge.ui;

import com.tmge.tetris.Tetris;
import com.tmge.samegame.SameGame;
import com.tmge.tilegames.TileGame;

public class GameLoader {

    private static TileGame gameInstance;
    private String gameString;

    private GameLoader(String gameString) {
        this.gameString = gameString;
    }

    public static TileGame getInstance(String gameString) {
        switch (gameString.toLowerCase()) {
            case "tetris":
                if (gameInstance == null) {
                    gameInstance = new Tetris();
                }
                break;
            case "samegame":
                if (gameInstance == null) {
                    gameInstance = new SameGame();
                }
                break;
        }
        return gameInstance;
    }

}
