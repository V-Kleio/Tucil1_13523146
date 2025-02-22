package iq_puzzler_pro;

import java.util.ArrayList;
import java.util.List;
public class Solver {
    private int attempts = 0;

    public boolean findSolution(Board board, List<Piece> pieces) {
        // * Store every piece in a list
        // * for each piece also store all the possible shapes (rotated & mirrored)
        // * Making it list inside a list
        List<List<Piece>> piecesShapes = new ArrayList<>();
        for (Piece p : pieces) {
            piecesShapes.add(getUniqueOrientations(p));
        }

        long startTime = System.nanoTime();
        boolean solved = solve(board, piecesShapes, 0);
        long endTime = System.nanoTime();
        
        System.out.println("Waktu pencarian: " + ((endTime - startTime) / 1000000.0) + " ms");
        System.out.println("Banyak kasus yang ditinjau: " + attempts);

        return solved;
    }

    private List<Piece> getUniqueOrientations(Piece piece) {
        List<Piece> orientations = new ArrayList<>();

        Piece currentPiece = piece;
        Piece mirroredPiece = piece.mirror();

        // * Rotate both original and mirrored piece
        for (int i = 0; i < 4; i++) {
            // ! Don't add existing shape
            if (!containShape(orientations, currentPiece)) {
                orientations.add(currentPiece);
            }

            if (!containShape(orientations, mirroredPiece)) {
                orientations.add(mirroredPiece);
            }

            currentPiece = currentPiece.rotate();
            mirroredPiece = mirroredPiece.rotate();
        }

        return orientations;
    }

    private boolean sameShape(Piece firstPiece, Piece secondPiece) {
        // * Helper to check if two pieces are the same
        if (firstPiece.getRow() != secondPiece.getRow() || firstPiece.getCol() != secondPiece.getCol()) {
            return false;
        }

        char[][] firstShape = firstPiece.getShape();
        char[][] secondShape = secondPiece.getShape();

        for (int i = 0; i < firstPiece.getRow(); i++) {
            for (int j = 0; j < firstPiece.getCol(); j++) {
                if (firstShape[i][j] != secondShape[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean containShape(List<Piece> listPieces, Piece checkedPiece) {
        // * Helper to check if a piece shape already exist in a list of piece
        for (Piece p : listPieces) {
            if (sameShape(p, checkedPiece)) {
                return true;
            }
        }

        return false;
    }

    private boolean solve(Board board, List<List<Piece>> piecesShapes, int index) {
        // * Index >= piecesShapes, then all pieces have been placed
        if (index >= piecesShapes.size()) {
            return true;
        }

        // * Find empty slot
        int[] emptySlot = findEmptySlot(board);
        if (emptySlot == null) {
            return true; // * No empty slot
        }

        int emptyRow = emptySlot[0];
        int emptyCol = emptySlot[1];

        for (Piece p : piecesShapes.get(index)) {
            attempts++;
            if (board.placePiece(p, emptyRow, emptyCol)) {
                if (solve(board, piecesShapes, index + 1)) {
                    return true;
                }

                board.removePiece(p, emptyRow, emptyCol);
            }
        }

        return false;
    }

    private int[] findEmptySlot(Board board) {
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getCol(); j++) {
                if (board.getBoard()[i][j] == '.') {
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }
}
