package iq_puzzler_pro;

public class Main {
    public static void main(String[] args) {
        System.out.println("Akhirnya ngerjain stima!!!");
        Board board = new Board(5, 5);
        Piece piece = new Piece(new char[][]{{'A','A','A'},{'.','A','.'}});

        board.placePiece(piece, 0, 0);
        board.printBoard();
        System.out.println("-----");
        board.removePiece(piece, 0, 0);
        board.printBoard();
    }
}