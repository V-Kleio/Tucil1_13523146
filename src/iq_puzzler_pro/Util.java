package iq_puzzler_pro;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Util {
    // * Color generated randomly using https://mokole.com/palette.html
    private static final Color[] COLORS = {
        new Color(211, 211, 211),
        new Color(47, 79, 79),
        new Color(139, 69, 19),
        new Color(34, 139, 24),
        new Color(128, 128, 0),
        new Color(72, 61, 139),
        new Color(0, 0, 128),
        new Color(154, 205, 50),
        new Color(102, 205, 170),
        new Color(255, 0, 0),
        new Color(255, 165, 0),
        new Color(255, 255, 0),
        new Color(124, 252, 0),
        new Color(0, 250, 154),
        new Color(138, 43, 226),
        new Color(139, 0, 139),
        new Color(220, 20, 60),
        new Color(0, 191, 255),
        new Color(0, 0, 255),
        new Color(255, 127, 80),
        new Color(255, 0, 255),
        new Color(30, 144, 255),
        new Color(219, 112, 147),
        new Color(240, 230, 140),
        new Color(255, 20, 147),
        new Color(238, 130, 238)
    };

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

    public static void saveSolutionAsImage(Board board, String filename) {
        int size = 40;
        int row = board.getRow();
        int col = board.getCol();
        int width = col * size;
        int height = row * size;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphic = image.createGraphics();

        graphic.setColor(Color.WHITE);
        graphic.fillRect(0, 0, width, height);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int x = j * size;
                int y = i * size;

                // * Square outline
                graphic.setColor(Color.LIGHT_GRAY);
                graphic.drawRect(x, y, size, size);

                char character = board.getBoard()[i][j];
                int characterValue = character - 'A';
                if (characterValue >= 0 && characterValue < COLORS.length) {
                    graphic.setColor(COLORS[characterValue]);
                } else {
                    graphic.setColor(Color.BLACK);
                }

                graphic.fillRect(x, y, size, size);
            }
        }

        graphic.dispose();

        try {
            ImageIO.write(image, "png", new File("test/image/" + filename + ".png"));
            System.err.println("Berhasil menyimpan solusi sebagai gambar: " + filename + ".png");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan solusi sebagai gambar.\n" + e.getMessage());
        }
    }
}
