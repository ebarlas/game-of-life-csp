package gameoflife;

import java.util.function.Consumer;

public class ConsoleOutput implements Consumer<boolean[][]> {

    @Override
    public void accept(boolean[][] cells) {
        for (int r = 0; r < cells.length; r++) {
            for (int c = 0; c < cells[r].length; c++) {
                if (cells[r][c]) {
                    System.out.print("X ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println("");
        }
    }
}
