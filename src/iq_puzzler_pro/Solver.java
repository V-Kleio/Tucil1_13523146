package iq_puzzler_pro;

import java.util.ArrayList;
import java.util.List;
public class Solver {
    private long attempts = 0;
    private double time = 0.0;

    public long getAttempts() {
        return attempts;
    }

    public double getTime() {
        return time;
    }

    public boolean findSolution(Board board, List<Piece> pieces) {
        // * Store every piece in a list
        // * for each piece also store all the possible shapes (rotated & mirrored)
        // * Making it list inside a list
        List<List<Piece>> piecesShapes = new ArrayList<>();
        for (Piece p : pieces) {
            piecesShapes.add(getUniqueOrientations(p));
        }

        long startTime = System.nanoTime();
        boolean solved = solve(board, piecesShapes);
        long endTime = System.nanoTime();

        time = (endTime - startTime) / 1000000.0;
        
        System.out.println("Waktu pencarian: " + time + " ms");
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

    private boolean solve(Board board, List<List<Piece>> piecesShapes) {
        // * If pieceShapes is empty, then there is no more piece to place 
        if (piecesShapes.isEmpty()) {
            return (findEmptySlot(board) == null);
        }

        // * Find empty slot
        int[] emptySlot = findEmptySlot(board);
        if (emptySlot == null) {
            return false;
        }

        int emptyRow = emptySlot[0];
        int emptyCol = emptySlot[1];

        for (int i = 0; i < piecesShapes.size(); i++) {
            List<Piece> orientations = piecesShapes.get(i);

            for (Piece p : orientations) {
                attempts++;
                if (board.placePiece(p, emptyRow, emptyCol)) {
                    List<List<Piece>> remaining = new ArrayList<>(piecesShapes);
                    remaining.remove(i);

                    if (solve(board, remaining)) {
                        return true;
                    }
                    
                    board.removePiece(p, emptyRow, emptyCol);
                }

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
