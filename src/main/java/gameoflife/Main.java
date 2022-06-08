package gameoflife;

import java.io.IOException;
import java.util.function.Consumer;

public class Main {

    record Args(
            String patternFile,
            int maxWindowWidth,
            int maxWindowHeight,
            int periodMilliseconds,
            int leftPadding,
            int topPadding,
            int rightPadding,
            int bottomPadding,
            boolean rotate,
            boolean logRate) {
        static Args parse(String[] args) {
            return new Args(
                    args.length > 0 ? args[0] : "patterns/gosper_glider_gun.txt",
                    args.length > 1 ? Integer.parseInt(args[1]) : 640,
                    args.length > 2 ? Integer.parseInt(args[2]) : 480,
                    args.length > 3 ? Integer.parseInt(args[3]) : 20,
                    args.length > 4 ? Integer.parseInt(args[4]) : 10,
                    args.length > 5 ? Integer.parseInt(args[5]) : 10,
                    args.length > 6 ? Integer.parseInt(args[6]) : 40,
                    args.length > 7 ? Integer.parseInt(args[7]) : 40,
                    args.length > 8 ? Boolean.parseBoolean(args[8]) : false,
                    args.length > 9 ? Boolean.parseBoolean(args[9]) : false);
        }
    }

    public static void main(String[] args) throws IOException {
        Args a = Args.parse(args);

        boolean[][] original = PatternParser.parseFile(a.patternFile);
        boolean[][] rotated = a.rotate ? PatternParser.rotate(original) : original;
        boolean[][] pattern = PatternParser.pad(rotated, a.leftPadding, a.topPadding, a.rightPadding, a.bottomPadding);

        Channel<boolean[][]> gridChannel = new Channel<>(); // channel carries aggregated liveness matrices
        Dimensions dimensions = new Dimensions(pattern.length, pattern[0].length);
        GameOfLife game = new GameOfLife(dimensions, pattern, a.periodMilliseconds, gridChannel);
        game.start();

        double scale = calculateScale(dimensions.rows(), dimensions.cols(), a.maxWindowWidth, a.maxWindowHeight);
        var width = (int) (scale * dimensions.cols());
        var height = (int) (scale * dimensions.rows());
        System.out.printf("rows=%d, columns=%d, width=%d, height=%d\n", dimensions.rows(), dimensions.cols(), width, height);
        Consumer<boolean[][]> consumer = new WindowOutput(width, height, a.logRate);

        while (true) {
            consumer.accept(gridChannel.take());
        }
    }

    private static double calculateScale(int rows, int cols, int maxWindowWidth, int maxWindowHeight) {
        double aspect = (double) maxWindowWidth / maxWindowHeight;
        double actual = (double) cols / rows;
        return actual < aspect
                ? (double) maxWindowHeight / rows
                : (double) maxWindowWidth / cols;
    }

}
