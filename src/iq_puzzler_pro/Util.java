package iq_puzzler_pro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {
    public static Board readBoardFromFile(Scanner scanner) {
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        return new Board(row, col);
    }

    public static List<Piece> readPieceFromFile(Scanner scanner, int pieceCount) {
        List<String> allLines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            line = line.replaceAll("\\s+$", "");
            if (!line.isEmpty()) {
                allLines.add(line);
            }
        }

        List<Piece> pieces = new ArrayList<>();
        int i = 0;
        
        while (i < allLines.size() && pieces.size() < pieceCount) {
            List<String> shapeLines = new ArrayList<>();
            char letter = allLines.get(i).trim().charAt(0);
            shapeLines.add(allLines.get(i));
            i++;

            while (i < allLines.size() && allLines.get(i).trim().charAt(0) == letter) {
                shapeLines.add(allLines.get(i));
                i++;
            }

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
                        shape[j][k] = (currentLine.charAt(k) == ' ') ? '.' : currentLine.charAt(k);
                    } else {
                        shape[j][k] = '.';
                    }
                }
            }
            pieces.add(new Piece(shape));
        }

        return pieces;
    }
}
