package iq_puzzler_pro;

public class Board {
    private static final String RESET = "\u001B[0m";

    private static final String[] COLORS = {
        "\u001B[101m",
        "\u001B[102m",
        "\u001B[103m",
        "\u001B[104m",
        "\u001B[105m",
        "\u001B[106m",
        "\u001B[41m",
        "\u001B[42m",
        "\u001B[43m",
        "\u001B[44m",
        "\u001B[45m",
        "\u001B[46m",
        "\u001B[91m",
        "\u001B[92m",
        "\u001B[93m",
        "\u001B[94m",
        "\u001B[95m",
        "\u001B[96m",
        "\u001B[1;91m",
        "\u001B[1;92m",
        "\u001B[1;93m",
        "\u001B[1;94m",
        "\u001B[1;95m",
        "\u001B[1;96m",
        "\u001B[4;31m",
        "\u001B[4;32m"
    };

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

    public void removePiece(Piece piece, int startRow, int startCol) {
        char[][] shape = piece.getShape();

        for (int i = 0; i < piece.getRow(); i++) {
            for (int j = 0; j < piece.getCol(); j++) {
                if (shape[i][j] != '.') {
                    board[startRow + i][startCol + j] = '.';
                }
            }
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == '.') {
                    return false;
                }
            }
        }

        return true;
    }

    public void printBoard() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                char character = board[i][j];

                if (character == '.') {
                    System.out.print(character);
                } else {
                    int characterValue = character - 'A';
                    String color;
                    if (characterValue >= 0 && characterValue < COLORS.length) {
                        color = COLORS[characterValue];
                    } else {
                        color = "";
                    }

                    System.out.print(color + character + RESET);
                }
            }
            System.out.println();
        }
    }
}
