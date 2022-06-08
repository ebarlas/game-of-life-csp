package gameoflife;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PatternParser {

    static boolean[][] parseFile(String path) throws IOException {
        return parse(Files.readString(Paths.get(path)));
    }

    static boolean[][] parse(String pattern) {
        String[] lines = pattern.split("\n");
        boolean[][] cells = new boolean[lines.length][lines[0].length()];
        for (int r = 0; r < lines.length; r++) {
            for (int c = 0; c < lines[r].length(); c++) {
                if (lines[r].charAt(c) == '*') {
                    cells[r][c] = true;
                }
            }
        }
        return cells;
    }

    static boolean[][] rotate(boolean[][] cells) {
        boolean[][] rotated = new boolean[cells[0].length][cells.length];
        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[r].length; c++) {
                rotated[c][r] = cells[r][c];
            }
        }
        return rotated;
    }

    static boolean[][] pad(boolean[][] cells, int left, int top, int right, int bottom) {
        boolean[][] padded = new boolean[cells.length + top + bottom][cells[0].length + left + right];
        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[r].length; c++) {
                padded[r + top][c + left] = cells[r][c];
            }
        }
        return padded;
    }

    static boolean[][] pad(boolean[][] cells, int padding) {
        return pad(cells, padding, padding, padding, padding);
    }

}
