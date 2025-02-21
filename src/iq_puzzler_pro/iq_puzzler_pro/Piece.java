package iq_puzzler_pro;

public class Piece {
    private final char[][] shape;

    public Piece(char[][] shape) {
        this.shape = shape;
    }

    public char[][] getShape() {
        return shape;
    }

    public Piece rotate() {
        return this;
    }

    public Piece mirror() {
        return this;
    }
}