package com.tmge.tilegames;

public abstract class TileBoard {
    protected Tile[][] board;

    protected int rowSize;
    protected int colSize;

    protected TileBoard(int rowNum, int colNum) {
        rowSize = rowNum;
        colSize = colNum;
    }

    public void init() {
        board = new Tile[rowSize][colSize];
        for (int i = 0; i < rowSize; ++i) {
            for (int j = 0; j < colSize; ++j) {
                //init 2d tile array
                board[i][j] = new Tile(i, j);
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

    public Tile[][] getBoard() {
        return board;
    }

    public boolean isGameOver() {
        return false;
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColSize() {
        return colSize;
    }

    public abstract void update();

    //FindMatch():void or findMatchandClear():void
    //Create abstract method to find matching and clear it on the board

}
