package com.tmge.tetris;

import javafx.scene.paint.Color;
import com.tmge.tilegames.Tile;

public class TetrisTile extends Tile {

    public boolean isOccupied;
    public int isInPiece;
    public Color color;

    protected TetrisTile(int row, int col) {
        super(row, col);
        isOccupied = false;
        isInPiece = 0;
        up = left = right = down = null;
        color = null;
    }

    @Override
    public String toString() {
        return (isOccupied || isInPiece != 0) ? "1" : "0";
    }
}
