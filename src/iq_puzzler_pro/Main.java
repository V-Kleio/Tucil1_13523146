package iq_puzzler_pro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = null;
        List<Piece> pieces = null;

        try (Scanner terminalInput = new Scanner(System.in)) {
            System.out.print("Masukkan nama file konfigurasi: ");
            String file = terminalInput.nextLine();

            try {
                Scanner scanner = new Scanner(new File(file));
                board = Util.readBoardFromFile(scanner);
                int pieceCount = scanner.nextInt();
                pieces = Util.readPieceFromFile(scanner, pieceCount);
                System.out.println("File successfully parsed");
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found: " + e.getMessage());
            }
        }
        Solver solver = new Solver();
        if (solver.findSolution(board, pieces)) {
            board.printBoard();
            System.out.println("Berhasil anjay tapi belum diwarnain");
        } else {
            System.out.println("Gagal anjay kayaknya gak ada solusi");
        }
    }
}