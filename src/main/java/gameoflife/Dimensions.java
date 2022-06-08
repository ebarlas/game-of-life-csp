package gameoflife;

import java.util.function.BiConsumer;

public record Dimensions(int rows, int cols) {
    void forEachRowCol(BiConsumer<Integer, Integer> consumer) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                consumer.accept(r, c);
            }
        }
    }

    void forEachNeighbor(int row, int col, BiConsumer<Integer, Integer> consumer) {
        for (int r = row - 1; r <= row + 1; r++) { // [row-1, row+1]
            for (int c = col - 1; c <= col + 1; c++) { // [col-1, col+1]
                if (r >= 0 && r < rows && c >= 0 && c < cols) { // exclude out of bounds
                    if (r != row || c != col) { // exclude self
                        consumer.accept(r, c);
                    }
                }
            }
        }
    }
}
