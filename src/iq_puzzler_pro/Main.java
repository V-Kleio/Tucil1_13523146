package iq_puzzler_pro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    @SuppressWarnings("ConvertToTryWithResources")
    // ! Suppress so that the IDE doesnt tell me to put the input scanner inside a try block
    public static void main(String[] args) {
        Board board;
        List<Piece> pieces;
        Scanner input = new Scanner(System.in);

        System.out.println("=======================================");
        System.out.println("Selamat datang di IQ Puzzler Pro Solver");
        System.out.println("=======================================");
        System.out.println();

        System.out.print("Masukkan nama file konfigurasi (txt file): ");
        String file = input.nextLine().trim();

        try {
            Scanner fileScanner = new Scanner(new File(file));
            if (!Util.validateConfigFile(fileScanner)) {
                System.out.println("\u001B[1;91mKonfigurasi file tidak valid.");
                input.close();
                return;
            }

            fileScanner.close();
            fileScanner = new Scanner(new File(file));

            int row = fileScanner.nextInt();
            int col = fileScanner.nextInt();
            int pieceCount = fileScanner.nextInt();
            fileScanner.nextLine(); // * go to next line after the reading of N M P
            board = Util.readBoardFromFile(fileScanner, row, col);
            pieces = Util.readPieceFromFile(fileScanner, pieceCount);
            System.out.println();
            System.out.println("File berhasil diproses.");
            System.out.println();
            System.out.println("=======================================");
            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("\u001B[1;91mFile tidak ditemukan, tulis nama file lengkap dengan ekstensi txt dan pastikan file ada.\n" + e.getMessage());
            input.close();
            return;
        }

        if (pieces == null) {
            input.close();
            return;
        }
    
        Solver solver = new Solver();
        boolean solved = solver.findSolution(board, pieces);
        if (solved) {
            board.printBoard();
            System.out.println("Solusi berhasil ditemukan!");
        } else {
            System.out.println("Tidak ada solusi yang ditemukan berdasarkan konfigurasi yang diberikan.");
        }

        System.out.println();
        System.out.print("Apakah anda ingin menyimpan solusi? (ya/tidak) ");
        String save = input.nextLine().trim();
        while (!("ya".equalsIgnoreCase(save) || "tidak".equalsIgnoreCase(save))) {
            System.out.println("\u001B[1;91mPerintah invalid\u001B[0m");
            System.out.print("Apakah anda ingin menyimpan solusi? (ya/tidak) ");
            save = input.nextLine().trim();
        }
        if ("ya".equalsIgnoreCase(save)) {
            System.out.print("Masukkan nama file (tanpa ekstensi file): ");
            save = input.nextLine().trim();
            Util.saveResult(save, solver, solved, board);
        }

        if (solved) {
            System.out.println();
            System.out.print("Apakah anda ingin menyimpan solusi sebagai gambar? (ya/tidak) ");
            save = input.nextLine().trim();
            while (!("ya".equalsIgnoreCase(save) || "tidak".equalsIgnoreCase(save))) {
                System.out.println("\u001B[1;91mPerintah invalid\u001B[0m");
                System.out.print("Apakah anda ingin menyimpan solusi sebagai gambar? (ya/tidak) ");
                save = input.nextLine().trim();
            }
            if ("ya".equalsIgnoreCase(save)) {
                System.out.print("Masukkan nama file (tanpa ekstensi file): ");
                save = input.nextLine().trim();
                Util.saveSolutionAsImage(board, save);
            }
        }

        input.close();
    }
}