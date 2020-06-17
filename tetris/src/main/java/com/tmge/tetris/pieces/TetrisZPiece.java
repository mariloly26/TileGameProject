package com.tmge.tetris.pieces;
import com.tmge.tetris.*;

public class TetrisZPiece extends TetrisPiece {
    @Override
    public void init(TetrisBufferBoard bufferBoard) {
        tiles = new TetrisTile[4];
        rotationState = 0;
        tiles[0] = bufferBoard.getBoard()[2][3]; //1
        tiles[1] = bufferBoard.getBoard()[2][4]; //2
        tiles[2] = bufferBoard.getBoard()[3][4]; //3
        tiles[3] = bufferBoard.getBoard()[3][5]; //4
        tiles[0].isInPiece = tiles[1].isInPiece = tiles[2].isInPiece = tiles[3].isInPiece = 1;
        bufferRowsCount = 2;
        pivotTile = tiles[1]; //update rotation pivot
        type = TetrisPieceType.Z;
    }
    @Override
    protected void updateRotation(){
        for(TetrisTile tile:tiles){
            tile.isInPiece--;
            tile.color = null;
        }
        switch (rotationState){
            case 0:
                tiles[0] = (TetrisTile) pivotTile.left;
                tiles[2] = (TetrisTile) pivotTile.down;
                tiles[3] = (TetrisTile) pivotTile.down.right;
                break;
            case 1:
                tiles[0] = ((TetrisTile)pivotTile.up);
                tiles[2] = ((TetrisTile)pivotTile.left);
                tiles[3] = ((TetrisTile)pivotTile.left.down);
                break;
            case 2:
                tiles[0] = ((TetrisTile)pivotTile.right);
                tiles[2] = ((TetrisTile)pivotTile.up);
                tiles[3] = ((TetrisTile)pivotTile.up.left);
                break;
            case 3:
                tiles[0] = ((TetrisTile)pivotTile.down);
                tiles[2] = ((TetrisTile)pivotTile.right);
                tiles[3] = ((TetrisTile)pivotTile.right.up);
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
    protected boolean canRotate(int newRotationState){
        switch (newRotationState){
            case 0:
                return isAvailableTile(pivotTile.left) && isAvailableTile(pivotTile.down) && isAvailableTile(pivotTile.down.right);
            case 1:
                return isAvailableTile(pivotTile.up) && isAvailableTile(pivotTile.left) && isAvailableTile(pivotTile.left.down);
            case 2:
                return isAvailableTile(pivotTile.right) && isAvailableTile(pivotTile.up) && isAvailableTile(pivotTile.up.left);
            case 3:
                return isAvailableTile(pivotTile.down) && isAvailableTile(pivotTile.right) && isAvailableTile(pivotTile.right.up);
            default:
                return false;
        }
    }
    @Override
    public String toString() {
        return "Z";
    }
}
