package com.tmge.tetris.pieces;
import com.tmge.tetris.*;

public class TetrisTPiece extends TetrisPiece{
    @Override
    public void init(TetrisBufferBoard bufferBoard) {
        tiles = new TetrisTile[4];
        tiles[0] = bufferBoard.getBoard()[3][3]; //1
        tiles[1] = bufferBoard.getBoard()[3][4]; //2 pivot
        tiles[2] = bufferBoard.getBoard()[3][5]; //3
        tiles[3] = bufferBoard.getBoard()[2][4]; //4
        tiles[0].isInPiece = tiles[1].isInPiece = tiles[2].isInPiece = tiles[3].isInPiece = 1;
        bufferRowsCount = 2;
        pivotTile = tiles[1]; //update rotation pivot
        type = TetrisPieceType.T;
    }
    @Override
    protected void updateRotation() {
        for(TetrisTile tile:tiles){
            tile.isInPiece--;
            tile.color = null;
        }
        switch (rotationState){
            case 0:
                tiles[0] = (TetrisTile) pivotTile.left;
                tiles[2] = (TetrisTile) pivotTile.right;
                tiles[3] = (TetrisTile) pivotTile.up;
                break;
            case 1:
                tiles[0] = ((TetrisTile)pivotTile.up);
                tiles[2] = ((TetrisTile)pivotTile.down);
                tiles[3] = ((TetrisTile)pivotTile.right);
                break;
            case 2:
                tiles[0] = ((TetrisTile)pivotTile.right);
                tiles[2] = ((TetrisTile)pivotTile.left);
                tiles[3] = ((TetrisTile)pivotTile.down);
                break;
            case 3:
                tiles[0] = ((TetrisTile)pivotTile.down);
                tiles[2] = ((TetrisTile)pivotTile.up);
                tiles[3] = ((TetrisTile)pivotTile.left);
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
                return isAvailableTile(pivotTile.left) && isAvailableTile(pivotTile.right) && isAvailableTile(pivotTile.up);
            case 1:
                return isAvailableTile(pivotTile.up) && isAvailableTile(pivotTile.down) && isAvailableTile(pivotTile.right);
            case 2:
                return isAvailableTile(pivotTile.right) && isAvailableTile(pivotTile.left) && isAvailableTile(pivotTile.down);
            case 3:
                return isAvailableTile(pivotTile.down) && isAvailableTile(pivotTile.up) && isAvailableTile(pivotTile.left);
            default:
                return false;
        }
    }
    @Override
    public String toString() {
        return "T";
    }
}
