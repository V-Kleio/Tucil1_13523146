package iq_puzzler_pro;

public class Board {
    private final char[][] board;
    private final int row;
    private final int col;

    public Board(int row, int col) {
        this.row = row;
        this.col = col;
        this.board = new char[row][col];

        // * Board is empty (filled with dots)
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = '.';
            }
        }
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

    public boolean placePiece(Piece piece, int startRow, int startCol) {
        char[][] shape = piece.getShape();
        
        // * Check if the piece can be placed in startRow and startCol coordinates
        for (int i = 0; i < piece.getRow(); i++) {
            for (int j = 0; j < piece.getCol(); j++) {
                if (shape[i][j] != '.') {
                    int boardRow = startRow + i;
                    int boardCol = startCol + j;
                    if (boardRow >= row || boardCol >= col || board[boardRow][boardCol] != '.') {
                        // ! Check out of bounds and overlapped
                        return false;
                    }
                }
            }
        }

        // * piece can be placed
        for (int i = 0; i < piece.getRow(); i++) {
            for (int j = 0; j < piece.getCol(); j++) {
                if (shape[i][j] != '.') {
                    board[startRow + i][startCol + j] = shape[i][j];
                }
            }
        }

        return true;
    }

    public void removePiece() {

    }

    public boolean isBoardFull() {
        return false;
    }

    public void printBoard() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
}
