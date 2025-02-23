package iq_puzzler_pro;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {
    public static Board readBoardFromFile(Scanner scanner, int row, int col) {
        String boardType = "";
        if(scanner.hasNextLine()) {
            boardType = scanner.nextLine().trim();
        }

        if("CUSTOM".equalsIgnoreCase(boardType)) {
            char[][] customLayout = new char[row][col];
            for (int i = 0; i < row; i++) {
                String line = scanner.nextLine().trim();
                for (int j = 0; j < col; j++) {
                    char character = line.charAt(j);
                    if (character == 'X') {
                        customLayout[i][j] = '.';
                    } else {
                        customLayout[i][j] = ' ';
                    }
                }
            }

            System.out.println("Membuat tipe board custom.");
            return new Board(customLayout);
        } else if ("PYRAMID".equalsIgnoreCase(boardType)) {
            System.out.println("Fitur pyramid tidak diimplementasikan.");
            System.out.println("Membuat tipe board default.");
            return new Board(row, col);
        } else {
            System.out.println("Membuat tipe board default.");
            return new Board(row, col);
        }

    }

    public static List<Piece> readPieceFromFile(Scanner scanner, int pieceCount) {
        List<String> allLines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            line = line.replaceAll("\\s+$", ""); // ! preserve leading whitespaces and remove trailing whitespaces
            if (!line.isEmpty()) {
                allLines.add(line);
            }
        }

        List<Piece> pieces = new ArrayList<>();
        int i = 0;
        
        while (i < allLines.size() && pieces.size() < pieceCount) {
            List<String> shapeLines = new ArrayList<>();
            char letter = allLines.get(i).trim().charAt(0); // * Find the alphabet representing a piece
            shapeLines.add(allLines.get(i));
            i++;

            while (i < allLines.size() && allLines.get(i).trim().charAt(0) == letter) {
                shapeLines.add(allLines.get(i));
                i++;
            }

            // * In lines with the same alphabet, find the line with the most characters (most column)
            int pieceRow = shapeLines.size();
            int pieceCol = 0;
            for (String line : shapeLines) {
                if (line.length() > pieceCol) {
                    pieceCol = line.length();
                }
            }

            char[][] shape = new char[pieceRow][pieceCol];
            for (int j = 0; j < pieceRow; j++) {
                String currentLine = shapeLines.get(j);
                for (int k = 0; k < pieceCol; k++) {
                    if (k < currentLine.length()) {
                        shape[j][k] = (currentLine.charAt(k) == ' ') ? '.' : currentLine.charAt(k); // ! Turn the leading whitespaces into dots
                    } else {
                        shape[j][k] = '.';
                    }
                }
            }
            pieces.add(new Piece(shape));
        }

        if (pieces.size() != pieceCount) {
            System.out.println("\u001B[1;91mBanyak blok puzzle tidak valid.");
            return null;
        }

        return pieces;
    }

    public static void saveResult(String filename, Solver solver, boolean solved, Board board) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("test/" + filename + ".txt"))) {
            writer.println("Waktu pencarian: " + solver.getTime() + " ms");
            writer.println("Banyak kasus yang ditinjau: " + solver.getAttempts() + " kasus");
            writer.println();

            if (solved) {
                writer.println("Solusi:");
                for (int i = 0; i < board.getRow(); i++) {
                    for (int j = 0; j < board.getCol(); j++) {
                        writer.print(board.getBoard()[i][j]);
                    }
                    writer.println();
                }
            } else {
                writer.println("Tidak ada solusi yang ditemukan.");
            }
        } catch (IOException e) {
            System.out.println("\u001B[1;91mTerjadi kesalahan saat menyimpan file: " + e.getMessage());
        }
    }

    public static boolean validateConfigFile(Scanner scanner) {
        try {
            // ! Check for dimension and piece count
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            int pieceCount = scanner.nextInt();

            if (row <= 0 || col <= 0 || pieceCount <= 0 || pieceCount > 26) {
                System.out.println("\u001B[1;91mDimensi atau banyak blok puzzle tidak valid.");
                return false;
            }
            scanner.nextLine();

            // ! Check for board type
            if (!scanner.hasNextLine()) {
                System.out.println("\u001B[1;91mJenis Kasus tidak ditemukan.");
                return false;
            }
            String boardType = scanner.nextLine().trim();
            if ("CUSTOM".equalsIgnoreCase(boardType)) {
                // ! Check for custom board configuration
                for (int i = 0; i < row; i++) {
                    if (!scanner.hasNextLine()) {
                        System.out.println("\u001B[1;91mJumlah baris tidak sesuai dengan dimensi.");
                        return false;
                    }
                    String line = scanner.nextLine().trim();
                    if (line.length() < col) {
                        System.out.println("\u001B[1;91mJumlah kolom tidak sesuai dengan dimensi.");
                        return false;
                    }
                }
            } else if ("DEFAULT".equalsIgnoreCase(boardType)) {
                System.out.print("");
            } else if ("PYRAMID".equalsIgnoreCase(boardType)) {
                System.out.println("Jenis kasus pyramid tidak diimplementasikan. Board menjadi default.");
            } else {
                System.out.println("\u001B[1;91mJenis kasus tidak dikenali");
                return false;
            }

            if (!scanner.hasNextLine()) {
                System.out.println("\u001B[1;91mKonfigurasi blok puzzle tidak ditemukan.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("\u001B[1;91mFormat file tidak valid.");
            return false;
        }
        return true;
    }
}
