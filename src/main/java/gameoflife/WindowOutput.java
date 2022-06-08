package gameoflife;

import java.awt.Color;
import java.awt.Graphics;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowOutput implements Consumer<boolean[][]> {

    private final int width;
    private final int height;
    private final boolean logRate;
    private final Canvas canvas;
    private final RateLogger rateLogger;

    private volatile boolean[][] cells;

    public WindowOutput(int width, int height, boolean logRate) {
        this.width = width;
        this.height = height;
        this.logRate = logRate;
        canvas = new Canvas();
        this.rateLogger = new RateLogger();
        JFrame frame = new JFrame("Conway's Game of Life");
        frame.add(canvas);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void accept(boolean[][] cells) {
        this.cells = cells;
        canvas.repaint();
        if (logRate) {
            rateLogger.tick();
        }
    }

    class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (cells == null)
                return;
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            g.setColor(Color.LIGHT_GRAY);
            int cellWidth = width / cells[0].length;
            for (int x = 0; x <= width; x += cellWidth) {
                g.drawLine(x, 0, x, height);
            }
            int cellHeight = height / cells.length;
            for (int y = 0; y <= height; y += cellHeight) {
                g.drawLine(0, y, width, y);
            }
            g.setColor(Color.BLACK);
            for (int r = 0; r < cells.length; r++) {
                for (int c = 0; c < cells[r].length; c++) {
                    if (cells[r][c]) {
                        g.fillRect(c * cellWidth, r * cellHeight, cellWidth, cellHeight);
                    }
                }
            }
        }
    }

    static class RateLogger {
        long timeSeconds;
        int counter;

        void tick() {
            long now = System.currentTimeMillis() / 1_000;
            if (now != timeSeconds) {
                System.out.println(counter);
                timeSeconds = now;
                counter = 1;
            } else {
                counter++;
            }
        }
    }

}
