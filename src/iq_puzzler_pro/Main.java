package iq_puzzler_pro;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Akhirnya ngerjain stima!!!");
        Board board = null;

        try {
            Scanner scanner = new Scanner(new File("tes.txt"));
            board = Util.readBoardFromFile("tes.txt", scanner);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found: " + e.getMessage());
        }

        if (board != null) {
            board.printBoard();
        }
    }
}