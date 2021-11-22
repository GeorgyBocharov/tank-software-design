package ru.mipt.bit.platformer.ai.creator;

import org.awesome.ai.state.GameState;
import ru.mipt.bit.platformer.objects.logic.LogicObstacle;
import ru.mipt.bit.platformer.objects.logic.LogicTank;

import java.util.List;

public interface GameStateCreator {
    GameState createGameState(LogicTank player, List<LogicTank> bots, List<LogicObstacle> obstacles);
}
