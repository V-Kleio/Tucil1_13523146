package iq_puzzler_pro;

public class Board {
    private final char[][] board;
    private final int row;
    private final int col;

    public Board(int row, int col) {
        this.row = row;
        this.col = col;
        this.board = new char[row][col];
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char[][] getBoard() {
        return board;
    }

    public void placePiece() {

    }

    public void removePiece() {

    }

    public boolean isBoardFull() {
        return false;
    }
}
