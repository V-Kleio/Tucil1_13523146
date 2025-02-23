package iq_puzzler_pro;

public class Piece {
    private final char[][] shape;
    private final int row;
    private final int col;

    public Piece(char[][] shape) {
        this.shape = shape;
        this.row = shape.length;
        this.col = shape[0].length;
    }

    public char[][] getShape() {
        return shape;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Piece rotate() {
        // * This will rotate the piece ccw
        char[][] rotatedPiece = new char[col][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                rotatedPiece[col - 1 - j][i] = shape[i][j];
            }
        }

        return new Piece(rotatedPiece);
    }

    public Piece mirror() {
        char[][] mirroredPiece = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                mirroredPiece[i][col - 1 - j] = shape[i][j];
            }
        }

        return new Piece(mirroredPiece);
    }
}