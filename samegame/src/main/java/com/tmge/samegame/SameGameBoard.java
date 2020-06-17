package com.tmge.samegame;

import com.tmge.tilegames.TileBoard;

import java.util.Random;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class SameGameBoard extends TileBoard {

    protected SameGameTile[][] board;
    protected HashMap<String, Character> tileMap = new HashMap<>(); // map rowCol and color
    protected List<Character> colorList = Arrays.asList('R', 'B', 'G', 'Y');
    public int current_score = 0;
    public boolean gameOver = false;
    public int bubble_num;
    public int total;

    SameGameBoard(int rowNum, int colNum) {
        super(rowNum, colNum);
    }

    @Override
    public SameGameTile[][] getBoard() {
        return board;
    }

    public void init() {
        board = new SameGameTile[rowSize][colSize];

        Random r = new Random();
        List<Character> l = colorList;
        for (int i = 0; i < rowSize; ++i) {
            for (int j = 0; j < colSize; ++j) {
                char c = l.get(r.nextInt(l.size()));
                board[i][j] = new SameGameTile(i, j, c);
                tileMap.put(buildTileMapHashKey(i, j), c);
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
        // TODO Auto-generated method stub

    }

    // score for each move
    public int calculateScore(int current_score) {
        if (bubble_num > 2) current_score = (bubble_num - 2) * (bubble_num - 2);
        else current_score = 0;
//        System.out.println("Score: " + current_score);
        return current_score;
    }

    public int add_total(int score) {
        total = total + score;
//        System.out.println("Total: " + total);
        return total;
    }

    public int findMatchClear(SameGameMove move) {
        if (move.row < 0 || move.row >= rowSize || move.col < 0 || move.col >= colSize) {
            //System.out.println("Tile is not on the board");
            return -1;
        }
        char clicked_color = tileMap.get(buildTileMapHashKey(move.row, move.col));
        if (clicked_color == '_') {
            //System.out.println("No tile here");
            return -1;
        }
        bubble_num = 1;

        if ((move.row - 1 >= 0 && board[move.row - 1][move.col].color == clicked_color) || (move.row + 1 < rowSize && board[move.row + 1][move.col].color == clicked_color) ||
                (move.col - 1 >= 0 && board[move.row][move.col - 1].color == clicked_color) || (move.col + 1 < colSize && board[move.row][move.col + 1].color == clicked_color)) {

            board[move.row][move.col].setColor('_');
            tileMap.replace(buildTileMapHashKey(move.row, move.col), '_');
            // ...call recursive helper
            if (move.row == 0 && move.col == 0) {
                bubble_num = bubble_num //+ findMatchClear_helper(row - 1, col, clicked_color)
                        //+ findMatchClear_helper(row, col - 1, clicked_color)
                        + findMatchClear_helper(move.row + 1, move.col, clicked_color)
                        + findMatchClear_helper(move.row, move.col + 1, clicked_color);
            }
            if (move.row == 0 && move.col == colSize - 1) {
                bubble_num = bubble_num //+ findMatchClear_helper(row - 1, col, clicked_color)
                        + findMatchClear_helper(move.row, move.col - 1, clicked_color)
                        + findMatchClear_helper(move.row + 1, move.col, clicked_color);
                //+ findMatchClear_helper(row, col + 1, clicked_color);
            }
            if (move.row == rowSize - 1 && move.col == 0) {
                bubble_num = bubble_num + findMatchClear_helper(move.row - 1, move.col, clicked_color)
                        //+ findMatchClear_helper(row, col - 1, clicked_color)
                        //+ findMatchClear_helper(row + 1, col, clicked_color)
                        + findMatchClear_helper(move.row, move.col + 1, clicked_color);
            }
            if (move.row == rowSize - 1 && move.col == colSize - 1) {
                bubble_num = bubble_num + findMatchClear_helper(move.row - 1, move.col, clicked_color)
                        + findMatchClear_helper(move.row, move.col - 1, clicked_color);
                //+ findMatchClear_helper(row + 1, col, clicked_color)
                //+ findMatchClear_helper(row, col + 1, clicked_color);
            }
            if (move.row == rowSize - 1) {
                bubble_num = bubble_num + findMatchClear_helper(move.row - 1, move.col, clicked_color)
                        + findMatchClear_helper(move.row, move.col - 1, clicked_color)
                        //+ findMatchClear_helper(row + 1, col, clicked_color)
                        + findMatchClear_helper(move.row, move.col + 1, clicked_color);
            }
            if (move.row == 0) {
                bubble_num = bubble_num + findMatchClear_helper(move.row, move.col - 1, clicked_color)
                        //+ findMatchClear_helper(row + 1, col, clicked_color)
                        + findMatchClear_helper(move.row, move.col + 1, clicked_color);
            }
            if (move.col == colSize - 1) {
                bubble_num = bubble_num + findMatchClear_helper(move.row - 1, move.col, clicked_color)
                        + findMatchClear_helper(move.row, move.col - 1, clicked_color)
                        + findMatchClear_helper(move.row + 1, move.col, clicked_color);
                //+ findMatchClear_helper(row, col + 1, clicked_color);
            }
            if (move.col == 0) {
                bubble_num = bubble_num + findMatchClear_helper(move.row - 1, move.col, clicked_color)
                        //+ findMatchClear_helper(row, col - 1, clicked_color)
                        + findMatchClear_helper(move.row + 1, move.col, clicked_color)
                        + findMatchClear_helper(move.row, move.col + 1, clicked_color);
            }
            bubble_num = bubble_num + findMatchClear_helper(move.row - 1, move.col, clicked_color)
                    + findMatchClear_helper(move.row + 1, move.col, clicked_color)
                    + findMatchClear_helper(move.row, move.col - 1, clicked_color)
                    + findMatchClear_helper(move.row, move.col + 1, clicked_color);
        }
//        System.out.println("Bubble_num: " + bubble_num);
        return bubble_num;


    }

    int findMatchClear_helper(int row, int col, char clicked_color) {
        if (row < 0 || row >= rowSize || col < 0 || col >= colSize) {
            //System.out.println("Tile is not on the board_helper");
            return 0;
        }
        if (board[row][col].color != clicked_color) {
            //System.out.println("No match here");
            return 0;
        }
        bubble_num = 1;
        board[row][col].setColor('_');
        // update tileMap
        tileMap.replace(buildTileMapHashKey(row, col), '_');
        if (row == 0 && col == 0) {
            bubble_num = bubble_num //+ findMatchClear_helper(row - 1, col, clicked_color)
                    //+ findMatchClear_helper(row, col - 1, clicked_color)
                    + findMatchClear_helper(row + 1, col, clicked_color)
                    + findMatchClear_helper(row, col + 1, clicked_color);
        }
        if (row == 0 && col == colSize - 1) {
            bubble_num = bubble_num //+ findMatchClear_helper(row - 1, col, clicked_color)
                    + findMatchClear_helper(row, col - 1, clicked_color)
                    + findMatchClear_helper(row + 1, col, clicked_color);
            //+ findMatchClear_helper(row, col + 1, clicked_color);
        }
        if (row == rowSize - 1 && col == 0) {
            bubble_num = bubble_num + findMatchClear_helper(row - 1, col, clicked_color)
                    //+ findMatchClear_helper(row, col - 1, clicked_color)
                    //+ findMatchClear_helper(row + 1, col, clicked_color)
                    + findMatchClear_helper(row, col + 1, clicked_color);
        }
        if (row == rowSize - 1 && col == colSize - 1) {
            bubble_num = bubble_num + findMatchClear_helper(row - 1, col, clicked_color)
                    + findMatchClear_helper(row, col - 1, clicked_color);
            //+ findMatchClear_helper(row + 1, col, clicked_color)
            //+ findMatchClear_helper(row, col + 1, clicked_color);
        }
        if (row == rowSize - 1) {
            bubble_num = bubble_num + findMatchClear_helper(row - 1, col, clicked_color)
                    + findMatchClear_helper(row, col - 1, clicked_color)
                    //+ findMatchClear_helper(row + 1, col, clicked_color)
                    + findMatchClear_helper(row, col + 1, clicked_color);
        }
        if (row == 0) {
            bubble_num = bubble_num + findMatchClear_helper(row, col - 1, clicked_color)
                    //+ findMatchClear_helper(row + 1, col, clicked_color)
                    + findMatchClear_helper(row, col + 1, clicked_color);
        }
        if (col == colSize - 1) {
            bubble_num = bubble_num + findMatchClear_helper(row - 1, col, clicked_color)
                    + findMatchClear_helper(row, col - 1, clicked_color)
                    + findMatchClear_helper(row + 1, col, clicked_color);
            //+ findMatchClear_helper(row, col + 1, clicked_color);
        }
        if (col == 0) {
            bubble_num = bubble_num + findMatchClear_helper(row - 1, col, clicked_color)
                    //+ findMatchClear_helper(row, col - 1, clicked_color)
                    + findMatchClear_helper(row + 1, col, clicked_color)
                    + findMatchClear_helper(row, col + 1, clicked_color);
        }
        bubble_num = bubble_num + findMatchClear_helper(row - 1, col, clicked_color)
                + findMatchClear_helper(row + 1, col, clicked_color)
                + findMatchClear_helper(row, col - 1, clicked_color)
                + findMatchClear_helper(row, col + 1, clicked_color);
        return bubble_num;
    }

    public void dropDown() {
        for (int col = 0; col < colSize; col++) {
            int emptyRow = rowSize - 1;
            int notEmptyRow = emptyRow;
            while (notEmptyRow >= 0 && emptyRow >= 0) {
                while (emptyRow >= 0 &&
                        board[emptyRow][col].color != '_')
                    emptyRow--;
                if (emptyRow >= 0) {
                    notEmptyRow = emptyRow - 1;
                    while (notEmptyRow >= 0 &&
                            board[notEmptyRow][col].color == '_')
                        notEmptyRow--;
                    if (notEmptyRow >= 0) {
                        board[emptyRow][col].setColor(board[notEmptyRow][col].color);
                        // update map, change current tile to the one above
                        tileMap.replace(buildTileMapHashKey(emptyRow, col), board[notEmptyRow][col].color);
                        board[notEmptyRow][col].setColor('_');
                        // update tileMap -> change tile to none
                        tileMap.replace(buildTileMapHashKey(notEmptyRow, col), '_');
                    }
                }
            }
        }
        int emptyCol = 0;
        int notEmptyCol = emptyCol;
        while (emptyCol < colSize && notEmptyCol < colSize) {
            while (emptyCol < colSize && board[rowSize - 1][emptyCol].color != '_')
                emptyCol++;
            if (emptyCol < colSize) {
                notEmptyCol = emptyCol + 1;
                while (notEmptyCol < colSize && board[rowSize - 1][notEmptyCol].color == '_')
                    notEmptyCol++;
                if (notEmptyCol < colSize) {
                    for (int row = 0; row < rowSize; row++) {
                        board[row][emptyCol].setColor(board[row][notEmptyCol].color);
                        tileMap.replace(buildTileMapHashKey(row, emptyCol), board[row][notEmptyCol].color);
                        board[row][notEmptyCol].setColor('_');
                        tileMap.replace(buildTileMapHashKey(row, notEmptyCol), '_');

                    }
                }
            }
        }
    }

    @Override
    public boolean isGameOver() {
        for (int col = 0; col < colSize; col++) {
            for (int row = rowSize - 1; row >= 0; row--) {
                if (board[row][col].color == '_')
                    break;
                else {
                    if (row - 1 >= 0 && board[row - 1][col].color == board[row][col].color)
                        return false;
                    else if (col + 1 < colSize && board[row][col + 1].color == board[row][col].color)
                        return false;
                }
            }
        }
//        System.out.println("Game over!");
        return true;
    }

    public String buildTileMapHashKey(int row, int col) {
        return "r" + row + "c" + col;
    }
}