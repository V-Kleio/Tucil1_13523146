package iq_puzzler_pro;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {
    public static Board readBoardFromFile(String filename, Scanner scanner) throws FileNotFoundException {
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        return new Board(row, col);
    }

    public static Piece readPieceFromFile(String filename, Scanner scanner) throws FileNotFoundException {
        String line = "";
        while (scanner.hasNextLine()) {
            line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                break;
            }
        }

        if (line.isEmpty()) {
            return null;
        }

        List<String> shapeLines = new ArrayList<>();
        shapeLines.add(line);

        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if (nextLine.trim().isEmpty()) {
                break;
            }
            shapeLines.add(nextLine);
        }

        int pieceRow = shapeLines.size();
        int pieceCol = shapeLines.get(0).length();
        char[][] shape = new char[pieceRow][pieceCol];
        for (int i = 0; i < pieceRow; i++) {
            shape[i] = shapeLines.get(i).toCharArray();
        }

        return new Piece(shape);
    }
}
