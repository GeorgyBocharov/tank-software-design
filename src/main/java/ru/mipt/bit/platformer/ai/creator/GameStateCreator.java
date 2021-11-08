package ru.mipt.bit.platformer.ai.creator;

import org.awesome.ai.state.GameState;
import ru.mipt.bit.platformer.objects.CollidingObject;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.List;

public interface GameStateCreator {
    GameState createGameState(Tank player, List<Tank> bots, List<CollidingObject> obstacles);
}
