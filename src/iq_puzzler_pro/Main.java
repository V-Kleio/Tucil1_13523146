package iq_puzzler_pro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board;
        List<Piece> pieces;

        System.out.println("=======================================");
        System.out.println("Selamat datang di IQ Puzzler Pro Solver");
        System.out.println("=======================================");
        System.out.println();

        try (Scanner terminalInput = new Scanner(System.in)) {
            System.out.print("Masukkan nama file konfigurasi (txt file): ");
            String file = terminalInput.nextLine();

            try {
                Scanner fileScanner = new Scanner(new File(file));
                board = Util.readBoardFromFile(fileScanner);
                int pieceCount = fileScanner.nextInt();
                pieces = Util.readPieceFromFile(fileScanner, pieceCount);
                System.out.println();
                System.out.println("File berhasil diproses.");
                System.out.println();
                System.out.println("=======================================");
                System.out.println();
            } catch (FileNotFoundException e) {
                System.out.println("File " + e.getMessage() + " tidak ditemukan, tulis nama file lengkap dengan ekstensi txt dan pastikan file ada.");
                return;
            }
        }
        Solver solver = new Solver();
        if (solver.findSolution(board, pieces)) {
            board.printBoard();
            System.out.println("Solusi berhasil ditemukan!");
        } else {
            System.out.println("Tidak ada solusi yang ditemukan berdasarkan konfigurasi yang diberikan.");
        }
    }
}