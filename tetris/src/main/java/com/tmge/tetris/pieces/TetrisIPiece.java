package com.tmge.tetris.pieces;
import com.tmge.tetris.*;

public class TetrisIPiece extends TetrisPiece {
    int previousRotationState;
    @Override
    public void rotate(boolean clockwise) {
        previousRotationState = rotationState;
        super.rotate(clockwise);
    }
    @Override
    public void init(TetrisBufferBoard bufferBoard) {
        tiles = new TetrisTile[4];
        tiles[0] = bufferBoard.getBoard()[0][4];
        tiles[1] = bufferBoard.getBoard()[1][4];
        tiles[2] = bufferBoard.getBoard()[2][4];
        tiles[3] = bufferBoard.getBoard()[3][4];
        tiles[0].isInPiece = tiles[1].isInPiece = tiles[2].isInPiece = tiles[3].isInPiece = 1;
        bufferRowsCount = 4;
        rotationState = 0;
        previousRotationState = 0;
        pivotTile = tiles[1]; //update pivot tile
        type = TetrisPieceType.I;
    }
    @Override
    protected void updateRotation() {
        //clear current tiles
        for(TetrisTile tile:tiles){
            tile.isInPiece--;
            tile.color = null;
        }
        tiles[1] = pivotTile;

        switch (rotationState){
            case 0:
                tiles[0] = (TetrisTile) pivotTile.up;
                tiles[2] = (TetrisTile) pivotTile.down;
                tiles[3] = (TetrisTile) pivotTile.down.down;
                break;
            case 1:
                tiles[0] = ((TetrisTile)pivotTile.right);
                tiles[2] = ((TetrisTile)pivotTile.left);
                tiles[3] = ((TetrisTile)pivotTile.left.left);
                break;
            case 2:
                tiles[0] = ((TetrisTile)pivotTile.down);
                tiles[2] = ((TetrisTile)pivotTile.up);
                tiles[3] = ((TetrisTile)pivotTile.up.up);
                break;
            case 3:
                tiles[0] = ((TetrisTile)pivotTile.left);
                tiles[2] = ((TetrisTile)pivotTile.right);
                tiles[3] = ((TetrisTile)pivotTile.right.right);
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
        //if canRotate, update pivotTile here
        TetrisTile newPivot;
        switch (newRotationState){
            case 0:
                newPivot = (rotationState == 3? (TetrisTile)pivotTile.up : (TetrisTile)pivotTile.left);
                if(newPivot == null) return false;
                if(isAvailableTile(newPivot.up) && isAvailableTile(newPivot.down) && isAvailableTile(newPivot.down.down)){
                    pivotTile = newPivot;
                    return true;
                }
                return false;
            case 1:
                newPivot = (rotationState == 0? (TetrisTile)pivotTile.right : (TetrisTile)pivotTile.up);
                if(newPivot == null) return false;
                if(isAvailableTile(newPivot.right) && isAvailableTile(newPivot.left) && isAvailableTile(newPivot.left.left)){
                    pivotTile = newPivot;
                    return true;
                }
                return false;
            case 2:
                newPivot = (rotationState == 1? (TetrisTile)pivotTile.down : (TetrisTile)pivotTile.right);
                if(newPivot == null) return false;
                if(isAvailableTile(newPivot.down) && isAvailableTile(newPivot.up) && isAvailableTile(newPivot.up.up)){
                    pivotTile = newPivot;
                    return true;
                }
                return false;
            case 3:
                newPivot = (rotationState == 2? (TetrisTile)pivotTile.left : (TetrisTile)pivotTile.down);
                if(newPivot == null) return false;
                if(isAvailableTile(newPivot.left) && isAvailableTile(newPivot.right) && isAvailableTile(newPivot.right.right)){
                    pivotTile = newPivot;
                    return true;
                }
                return false;
            default:
                return false;
        }

    }

    @Override
    public String toString() {
        return "I";
    }
}
