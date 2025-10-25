package dev.phlorion.chess.misc;

import dev.phlorion.chess.pieces.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BoardReader {
    public BoardReader() {}

    public Piece[][] read(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int rows = 0;
            int columns = line.length();

            while (line != null) {
                rows++;
                sb.append(line);
                line = br.readLine();
            }

            String result = sb.toString();
            return parseStringToPieceArr(result, rows, columns);

        } catch (Exception e) {
            System.err.println(e);
            return null;
        } finally {
            br.close();
        }
    }

    private Piece[][] parseStringToPieceArr(String string, int rows, int columns) {
        Piece[][] temp = new Piece[rows][columns];
        for (int i = 0; i< string.length(); i++) {
            char c = string.toCharArray()[i];
            int row = i / rows;
            int column = i % rows;

            switch(c) {
                case 'r':
                    temp[row][column] = new Rook(row, column, PieceColor.BLACK);
                    break;
                case 'n':
                    temp[row][column] = new Knight(row, column, PieceColor.BLACK);
                    break;
                case 'b':
                    temp[row][column] = new Bishop(row, column, PieceColor.BLACK);
                    break;
                case 'q':
                    temp[row][column] = new Queen(row, column, PieceColor.BLACK);
                    break;
                case 'k':
                    temp[row][column] = new King(row, column, PieceColor.BLACK);
                    break;
                case 'p':
                    temp[row][column] = new Pawn(row, column, PieceColor.BLACK);
                    break;
                case 'R':
                    temp[row][column] = new Rook(row, column, PieceColor.WHITE);
                    break;
                case 'N':
                    temp[row][column] = new Knight(row, column, PieceColor.WHITE);
                    break;
                case 'B':
                    temp[row][column] = new Bishop(row, column, PieceColor.WHITE);
                    break;
                case 'Q':
                    temp[row][column] = new Queen(row, column, PieceColor.WHITE);
                    break;
                case 'K':
                    temp[row][column] = new King(row, column, PieceColor.WHITE);
                    break;
                case 'P':
                    temp[row][column] = new Pawn(row, column, PieceColor.WHITE);
                    break;
                default:
                    break;

            }
        }

        return temp;
    }
}
