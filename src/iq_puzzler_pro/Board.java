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

    public Board(char[][] customBoard) {
        this.row = customBoard.length;
        this.col = customBoard[0].length;
        this.board = customBoard;
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

    public int[] placePiece(Piece piece, int boardRow, int boardCol) {
        char[][] shape = piece.getShape();
        int pieceRows = piece.getRow();
        int pieceCols = piece.getCol();

        for (int charRow = 0; charRow < pieceRows; charRow++) {
            for (int charCol = 0; charCol < pieceCols; charCol++) {
                // * Find coordinates of char in the piece
                if (shape[charRow][charCol] == '.') {
                    continue;
                }

                // * Find the offset
                // * Offset is the board coordinates that align with the piece [0,0]
                int offsetRow = boardRow - charRow;
                int offsetCol = boardCol - charCol;
                boolean canPlace = true;

                // * Iterate through the board starting from the offset coordinates
                // * Compare it with the piece from [0,0] to check for validity
                // * Valid -> within the board and not overlapped (dots represent empty slot)
                for (int i = 0; i < pieceRows && canPlace; i++) {
                    for (int j = 0; j < pieceCols; j++) {
                        if (shape[i][j] != '.') {
                            int offsetRowPointer = offsetRow + i;
                            int offsetColPointer = offsetCol + j;
                            if (offsetRowPointer < 0 || offsetRowPointer >= this.row || offsetColPointer < 0 || offsetColPointer >= this.col || board[offsetRowPointer][offsetColPointer] != '.') {
                                canPlace = false;
                                break;
                            }
                        }
                    }
                }

                // * Iterate again to replace the board with the piece
                if (canPlace) {
                    for (int i = 0; i < pieceRows; i++) {
                        for (int j = 0; j < pieceCols; j++) {
                            if (shape[i][j] != '.') {
                                board[offsetRow + i][offsetCol + j] = shape[i][j];
                            }
                        }
                    }
                    return new int[]{offsetRow, offsetCol}; // ! Offset is also needed for removing the piece
                }
            }
        }

        return null; // ! No valid placement
    }

    public void removePiece(Piece piece, int[] offset) {
        if (offset == null) {
            return;
        }

        int offsetRow = offset[0];
        int offsetCol = offset[1];
        char[][] shape = piece.getShape();
        int pieceRows = piece.getRow();
        int pieceCols = piece.getCol();

        // * We already know the offset so just replace the alphabet back to dots
        for (int i = 0; i < pieceRows; i++) {
            for (int j = 0; j < pieceCols; j++) {
                if (shape[i][j] != '.') {
                    board[offsetRow + i][offsetCol + j] = '.';
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

                int characterValue = character - 'A';
                String color;
                if (characterValue >= 0 && characterValue < COLORS.length) {
                    color = COLORS[characterValue];
                } else {
                    color = "";
                }

                System.out.print(color + character + RESET);
            }
            System.out.println();
        }
    }
}
