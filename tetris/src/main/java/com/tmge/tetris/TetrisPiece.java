package com.tmge.tetris;

import com.tmge.tetris.pieces.*;
import com.tmge.tilegames.Tile;
import javafx.scene.paint.Color;

public abstract class TetrisPiece {
    //protected TetrisPieceType type;
    protected TetrisTile[] tiles;
    protected int bufferRowsCount;
    protected int rotationState;
    protected TetrisTile pivotTile;
    protected TetrisPieceType type;

    protected Color color;
    static Color[] colorList = {Color.DARKRED, Color.DARKGREEN, Color.BLUE, Color.LIGHTYELLOW};

    //randomly generate piece
    static TetrisPiece initTetrisPiece(TetrisBufferBoard bufferBoard) {
        int type = (int) (Math.random() * 7);
        return initTetrisPiece(TetrisPieceType.values()[type], bufferBoard);
    }

    //generate piece from specified type
    static TetrisPiece initTetrisPiece(TetrisPieceType type, TetrisBufferBoard bufferBoard) {
        TetrisPiece piece;
        switch (type) {
            case J:
                piece = new TetrisJPiece();
                break;
            case L:
                piece = new TetrisLPiece();
                break;
            case I:
                piece = new TetrisIPiece();
                break;
            case O:
                piece = new TetrisOPiece();
                break;
            case S:
                piece = new TetrisSPiece();
                break;
            case T:
                piece = new TetrisTPiece();
                break;
            case Z:
                piece = new TetrisZPiece();
                break;
            default:
                piece = new TetrisIPiece();
                break;
        }
        piece.init(bufferBoard);
        piece.setColor(colorList[(int)(Math.random() * 4)]); //init random color
        return piece;
    }

    protected abstract void init(TetrisBufferBoard bufferBoard);



    //generic rotate
    public void rotate(boolean clockwise) {
        pivotTile = tiles[1]; //update rotation pivot because pivot changes after translate()
        int newRotation = (clockwise ? (rotationState + 1) % 4 : rotationState - 1);
        if (newRotation < 0)
            newRotation = 3;
        if (canRotate(newRotation))
            rotationState = newRotation;
        updateRotation();

    }

    //rotation helpers
    abstract protected void updateRotation();

    abstract protected boolean canRotate(int newRotationState);

    protected boolean isAvailableTile(Tile tile) {
        return tile != null && !((TetrisTile) (tile)).isOccupied;
    }

    public void translate(boolean left) {
        boolean isMovable = true;
        for (TetrisTile tile : tiles) {
            TetrisTile temp_tile;
            if (left)
                temp_tile = (TetrisTile) tile.left;
            else
                temp_tile = (TetrisTile) tile.right;

            if (temp_tile == null || temp_tile.isOccupied) {
                isMovable = false;
                break;
            }
        }
        if (isMovable) {
            for (int i = 0; i < 4; ++i) {
                tiles[i].isInPiece--;
                if (left) {
                    ((TetrisTile) tiles[i].left).isInPiece++;
                    tiles[i] = (TetrisTile) tiles[i].left;
                } else {
                    ((TetrisTile) tiles[i].right).isInPiece++;
                    tiles[i] = (TetrisTile) tiles[i].right;
                }
                tiles[i].color = color;
            }
        }
    }

    //if false, piece has dropped on the bottom or another piece
    public boolean drop() {
        boolean pieceLanded = false;
        for (TetrisTile tile : tiles) {
            if (tile.down == null || ((TetrisTile) (tile.down)).isOccupied) {
                pieceLanded = true;
                break;
            }
        }

        for (int i = 0; i < 4; ++i) {
            if (pieceLanded) {
                tiles[i].isOccupied = true;
                tiles[i].isInPiece = 0;
                tiles[i].color = color;
            } else {
                //reset previous tile
                tiles[i].isInPiece--;
                if( tiles[i].isInPiece == 0)
                    tiles[i].color = null;

                //set up new tile
                tiles[i] = (TetrisTile) tiles[i].down;
                tiles[i].isInPiece++;
                tiles[i].color = color;

                //after dropping down, decrement buffer rows
                if (bufferRowsCount > 0)
                    bufferRowsCount--;
            }
        }
        return !pieceLanded;
    }

    //switch to holding piece
    public void destory() {
        for (TetrisTile tile : tiles) {
            tile.isInPiece = 0;
            tile.isOccupied = false;
        }
    }

    public boolean inBufferBoard() {
        return bufferRowsCount > 0;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color newColor){
        color = newColor;
        for(TetrisTile tile:tiles){
            tile.color = newColor;
        }
    }
}
