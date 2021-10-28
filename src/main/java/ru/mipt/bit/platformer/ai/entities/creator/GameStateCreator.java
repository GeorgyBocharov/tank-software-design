package ru.mipt.bit.platformer.ai.entities.creator;

import org.awesome.ai.state.GameState;
import ru.mipt.bit.platformer.objects.LibGdxGraphicObject;
import ru.mipt.bit.platformer.objects.movable.impl.Tank;

import java.util.List;

public interface GameStateCreator {
    GameState createGameState(Tank player, List<Tank> bots, List<LibGdxGraphicObject> obstacles);
}
