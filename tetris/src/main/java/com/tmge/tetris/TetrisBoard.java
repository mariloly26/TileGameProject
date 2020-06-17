package com.tmge.tetris;
//standard tetris board 20x10

import java.util.*;

import javafx.scene.paint.Color;
import com.tmge.tilegames.Tile;
import com.tmge.tilegames.TileBoard;

public class TetrisBoard extends TileBoard {
    TetrisBufferBoard bufferBoard;
    TetrisBufferBoard holdingArea;
    TetrisPieceType holding;
    Color holdingColor;
    TetrisPiece currentPiece;
    LinkedList<TetrisPieceType> nextPieces;
    boolean gameOver;
    boolean canSwitch;
    int scores;
    int scorePerLine;
    int totalLinesCleared;
    int speed;

    TetrisBoard(int row_num, int col_num) {
        super(row_num, col_num);
    }

    @Override
    public void init() {
        //init board with tetris tiles
        board = new TetrisTile[rowSize][colSize];
        for (int i = 0; i < rowSize; ++i) {
            for (int j = 0; j < colSize; ++j) {
                //init 2d tile array
                board[i][j] = new TetrisTile(i, j);
                ((TetrisTile) board[i][j]).isOccupied = false;
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
        //init buffer board
        bufferBoard = new TetrisBufferBoard(4, 10);
        bufferBoard.init();

        //init holding area
        holdingArea = new TetrisBufferBoard(6, 10);
        holdingArea.init();

        //connect last row of buffer board with the first row of tetris board
        for (int i = 0; i < 10; ++i) {
            bufferBoard.getBoard()[3][i].down = board[0][i];
            board[0][i].up = bufferBoard.getBoard()[3][i];
        }
        speed = 1;
        scorePerLine = 10;
        totalLinesCleared = 0;
        nextPieces = new LinkedList<TetrisPieceType>();
        holding = null;

        //init next 4 pieces for preview
        for (int i = 0; i < 4; ++i) {
            int type = (int) (Math.random() * 7);
            nextPieces.add(TetrisPieceType.values()[type]);
        }
        generateNextPiece();
        //game start
        gameOver = false;
    }

    @Override
    public TetrisTile[][] getBoard() {
        return (TetrisTile[][]) board;
    }

    @Override
    public void update() {

        if (gameOver) return;

        //failed to drop
        if (!currentPiece.drop()) {
            //piece inside buffer board failed to drop, then game is over
            if (currentPiece.inBufferBoard()) {
                gameOver = true;
                return;
            }
            //piece inside main board failed to drop, continue
            clearLine();
            generateNextPiece();
        }

//        System.out.println("Next pieces:");
//        for (TetrisPieceType type : nextPieces) {
//            System.out.print(type);
//        }
//        System.out.println();
    }

    public void move(boolean left) {
        currentPiece.translate(left);
    }

    public void rotate(boolean clockwise) {
        currentPiece.rotate(clockwise);
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    public int getScores() {
        return scores;
    }

    public int getSpeed() {
        return speed;
    }

    public LinkedList<TetrisPieceType> getNextPieces() {
        return nextPieces;
    }

    public TetrisPieceType getHolding() {
        return holding;
    }

    public void switchHolding() {
        if (canSwitch) {
            currentPiece.destory();
            if (holding != null) {
                TetrisPieceType tempType = holding;
                Color tempColor = holdingColor;
                holding = currentPiece.type;
                holdingColor = currentPiece.color;
                currentPiece = TetrisPiece.initTetrisPiece(tempType, bufferBoard);
                currentPiece.setColor(tempColor);
            } else {
                holding = currentPiece.type;
                holdingColor = currentPiece.color;
                generateNextPiece();
            }
            canSwitch = false;
        }
    }

    public TetrisTile[][] getHoldingArea() {
        if (holding != null) {
            holdingArea.init();
            TetrisPiece holdingPiece = TetrisPiece.initTetrisPiece(holding, holdingArea);
            holdingPiece.setColor(holdingColor);
            holdingPiece.drop();
        }
        return holdingArea.getBoard();
    }

    //deprecated
    private void generateRandomPiece() {
        currentPiece = TetrisPiece.initTetrisPiece(bufferBoard);
    }

    private void generateNextPiece() {
        int type = (int) (Math.random() * 7);
        nextPieces.addLast(TetrisPieceType.values()[type]);
        currentPiece = TetrisPiece.initTetrisPiece(nextPieces.removeFirst(), bufferBoard);
        canSwitch = true;
    }

    private void updateSpeed() {
        if (totalLinesCleared % 12 == 0) {
            speed += 1;
//            scorePerLine *= 1.2;
        }
    }

    private void updateScores(int linesCleared) {
        for (int i = 1; i <= linesCleared; ++i) {
            totalLinesCleared++;
            scores += i * scorePerLine * speed;
        }
    }

    private void clearLine() {
        ArrayList<Integer> clearedRows = new ArrayList<>();
        //only 4 rows need to be cleared
        for (Tile tile : currentPiece.getTiles()) {
            int row = tile.getRow();
            boolean toClear = true;
            for (int i = 0; i < colSize; ++i) {
                //line is not full
                if (!((TetrisTile) (board[row][i])).isOccupied) {
                    toClear = false;
                    break;
                }
            }
            if (toClear) {
                //clear
                for (int i = 0; i < colSize; ++i) {
                    ((TetrisTile) (board[row][i])).isOccupied = false;
                }
                clearedRows.add(row);
            }
        }

        //clear row from bottom to top, largest row number first
        clearedRows.sort(Collections.reverseOrder());

        //recursively clear or drop lines above
        if (!clearedRows.isEmpty()) {
            recurClear(clearedRows.get(0), 1, clearedRows);

            //add scores
            updateScores(clearedRows.size());
            //scores updated inside
            updateSpeed();
        }
//        System.out.println("Speed "+ speed);
//        System.out.println("Scores "+ scores);
//        System.out.println("Score per line "+ scorePerLine);
    }

    //helpers
    private void recurClear(int row, int offset, ArrayList<Integer> clearedRows) {
        //reach top
        if (row < 0) return;

        //no tiles to inherit
        if(row - offset < 0){
            for (int i = 0; i < colSize; ++i) {
                getBoard()[row][i].isOccupied = false;
                getBoard()[row][i].color = null;
            }
            recurClear(row - 1, offset, clearedRows);
        }
        else {
            //increase offset by the number of cleared rows above
            int newOffset = offset;
            int _row = row;
            while (clearedRows.contains(_row - 1)) {
                clearedRows.remove(Integer.valueOf(_row - 1));
                newOffset++;
                _row--;
            }

            //swap tiles
            for (int i = 0; i < colSize; ++i) {
                getBoard()[row][i].isOccupied = getBoard()[row - newOffset][i].isOccupied;
                getBoard()[row][i].color = getBoard()[row - newOffset][i].color;
            }
            recurClear(row - 1, newOffset, clearedRows);
        }
    }
}
