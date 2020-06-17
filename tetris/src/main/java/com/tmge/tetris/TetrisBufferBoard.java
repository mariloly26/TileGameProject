package com.tmge.tetris;

import com.tmge.tilegames.TileBoard;

public class TetrisBufferBoard extends TileBoard {

    TetrisBufferBoard(int rowNum, int colNum) {
        super(rowNum, colNum);
    }

    @Override
    public void init() {
        //init board with tetris tiles
        board = new TetrisTile[rowSize][colSize];
        for (int i = 0; i < rowSize; ++i) {
            for (int j = 0; j < colSize; ++j) {
                //init 2d tile array
                board[i][j] = new TetrisTile(i, j);
                //init tile nodes
                if (i > 0) {
                    board[i][j].up = board[i - 1][j];
                    board[i - 1][j].down = board[i][j];
                }
                if (j > 0) {
                    board[i][j].left = board[i][j - 1];
                    board[i][j - 1].right = board[i][j];
                }
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public TetrisTile[][] getBoard() {
        return (TetrisTile[][]) board;
    }


}
