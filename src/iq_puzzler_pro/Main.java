package iq_puzzler_pro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Akhirnya ngerjain stima!!!");
        Board board = null;
        List<Piece> pieces = null;

        try {
            Scanner scanner = new Scanner(new File("tes.txt"));
            board = Util.readBoardFromFile(scanner);
            int pieceCount = scanner.nextInt();
            pieces = Util.readPieceFromFile(scanner, pieceCount);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + e.getMessage());
        }

        if (pieces != null) {
            board.placePiece(pieces.get(0), 0, 0);
            board.placePiece(pieces.get(1), 0, 2);
            board.placePiece(pieces.get(2), 0, 4);
            board.placePiece(pieces.get(3), 0, 6);
            board.placePiece(pieces.get(4), 0, 8);
            board.printBoard();
        }
    }
}