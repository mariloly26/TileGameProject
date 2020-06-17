package com.tmge.tetris.pieces;
import com.tmge.tetris.*;

public class TetrisOPiece extends TetrisPiece {
    @Override
    public void init(TetrisBufferBoard bufferBoard) {
        tiles = new TetrisTile[4];
        tiles[0] = bufferBoard.getBoard()[2][4];
        tiles[1] = bufferBoard.getBoard()[2][5];
        tiles[2] = bufferBoard.getBoard()[3][4];
        tiles[3] = bufferBoard.getBoard()[3][5];
        tiles[0].isInPiece = tiles[1].isInPiece = tiles[2].isInPiece = tiles[3].isInPiece = 1;
        bufferRowsCount = 2;
        pivotTile = tiles[1]; //update rotation pivot
        type = TetrisPieceType.O;
    }
    @Override
    protected void updateRotation() {}
    @Override
    protected boolean canRotate(int newRotationState) {
        return false;
    }
    @Override
    public String toString() {
        return "O";
    }
}
