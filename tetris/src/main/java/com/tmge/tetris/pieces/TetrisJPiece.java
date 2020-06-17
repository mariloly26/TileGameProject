package com.tmge.tetris.pieces;
import com.tmge.tetris.*;

public class TetrisJPiece extends TetrisPiece{
    @Override
    public void init(TetrisBufferBoard bufferBoard) {
        tiles = new TetrisTile[4];
        tiles[0] = bufferBoard.getBoard()[1][5];
        tiles[1] = bufferBoard.getBoard()[2][5];
        tiles[2] = bufferBoard.getBoard()[3][5];
        tiles[3] = bufferBoard.getBoard()[3][4];
        tiles[0].isInPiece = tiles[1].isInPiece = tiles[2].isInPiece = tiles[3].isInPiece = 1;
        bufferRowsCount = 3;
        pivotTile = tiles[1]; //update pivot tile
        type = TetrisPieceType.J;
    }
    @Override
    protected void updateRotation() {
        for(TetrisTile tile:tiles){
            tile.isInPiece--;
            tile.color = null;
        }
        switch (rotationState){
            case 0:
                tiles[0] = (TetrisTile) pivotTile.up;
                tiles[2] = (TetrisTile) pivotTile.down;
                tiles[3] = (TetrisTile) pivotTile.down.left;
                break;
            case 1:
                tiles[0] = ((TetrisTile)pivotTile.right);
                tiles[2] = ((TetrisTile)pivotTile.left);
                tiles[3] = ((TetrisTile)pivotTile.left.up);
                break;
            case 2:
                tiles[0] = ((TetrisTile)pivotTile.down);
                tiles[2] = ((TetrisTile)pivotTile.up);
                tiles[3] = ((TetrisTile)pivotTile.up.right);
                break;
            case 3:
                tiles[0] = ((TetrisTile)pivotTile.left);
                tiles[2] = ((TetrisTile)pivotTile.right);
                tiles[3] = ((TetrisTile)pivotTile.right.down);
                break;
            default:
                return;
        }
        pivotTile.isInPiece++; //also tiles[1]
        pivotTile.color = color;
        tiles[0].isInPiece++;
        tiles[0].color = color;
        tiles[2].isInPiece++;
        tiles[2].color = color;
        tiles[3].isInPiece++;
        tiles[3].color = color;
    }
    @Override
    protected boolean canRotate(int newRotationState) {
        switch (newRotationState){
            case 0:
                return isAvailableTile(pivotTile.up) && isAvailableTile(pivotTile.down) && isAvailableTile(pivotTile.down.left);
            case 1:
                return isAvailableTile(pivotTile.right) && isAvailableTile(pivotTile.left) && isAvailableTile(pivotTile.left.up);
            case 2:
                return isAvailableTile(pivotTile.down) && isAvailableTile(pivotTile.up) && isAvailableTile(pivotTile.up.right);
            case 3:
                return isAvailableTile(pivotTile.left) && isAvailableTile(pivotTile.right) && isAvailableTile(pivotTile.right.down);
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "J";
    }
}
