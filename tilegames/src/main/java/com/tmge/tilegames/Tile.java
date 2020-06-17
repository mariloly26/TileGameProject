package com.tmge.tilegames;

public class Tile {

    //2D array
    protected int row;
    protected int col;

    //graph
    public Tile up;
    public Tile left;
    public Tile right;
    public Tile down;

    protected Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

}
