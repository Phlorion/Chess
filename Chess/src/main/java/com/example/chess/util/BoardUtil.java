package com.example.chess.util;

import com.example.chess.board.Board;

public class BoardUtil {

    public BoardUtil() {}

    /**
     * Converts an index like 0, 1, 2 to a, b, c, in respect
     * @param n An index n
     * @return The assigned value
     */
    public static String boardNumToString(int n) {
        String result = "";
        switch (n) {
            case 0:
                result = "a";
                break;
            case 1:
                result = "b";
                break;
            case 2:
                result = "c";
                break;
            case 3:
                result = "d";
                break;
            case 4:
                result = "e";
                break;
            case 5:
                result = "f";
                break;
            case 6:
                result = "g";
                break;
            case 7:
                result = "h";
                break;
            default:
                break;
        }

        return result;
    }

    public static String boardIndexToString(int i, int j) {
        String result = "";
        result += BoardUtil.boardNumToString(j);
        result += (Board.NUM_TILES_PER_COL - i);
        return result;
    }
}
