package gameoflife;

import java.util.List;

public record CellOptions(
        int row,
        int col,
        boolean alive,
        Channel<Boolean> tickChannel,
        Channel<Boolean> resultChannel,
        List<Channel<Boolean>> inChannels,
        List<Channel<Boolean>> outChannels) {}
