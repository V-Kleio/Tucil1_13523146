package iq_puzzler_pro;

import java.util.List;
import java.util.ArrayList;
public class Solver {
    private int attempts = 0;

    public boolean solve(Board board, List<Piece> pieces) {
        // * Store every piece in a list
        // * for each piece also store all the possible shapes (rotated & mirrored)
        // * Making it list inside a list
        List<List<Piece>> piecesShapes = new ArrayList<>();
        for (Piece p : pieces) {
            piecesShapes.add(getUniqueOrientations(p));
        }
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
}
