package ru.mipt.bit.platformer.ai.entities.creator.impl;

import lombok.RequiredArgsConstructor;
import org.awesome.ai.state.GameState;
import ru.mipt.bit.platformer.ai.entities.converter.GraphicObjectToObstacleConverter;
import ru.mipt.bit.platformer.ai.entities.converter.TankToBotConverter;
import ru.mipt.bit.platformer.ai.entities.converter.TankToPlayerConverter;
import ru.mipt.bit.platformer.ai.entities.creator.GameStateCreator;
import ru.mipt.bit.platformer.objects.LibGdxGraphicObject;
import ru.mipt.bit.platformer.objects.movable.impl.Tank;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GameStateCreatorImpl implements GameStateCreator {

    private final TankToPlayerConverter tankToPlayerConverter;
    private final TankToBotConverter tankToBotConverter;
    private final GraphicObjectToObstacleConverter gdxGraphicObjectToObstacleConverter;

    @Override
    public GameState createGameState(Tank player, List<Tank> bots, List<LibGdxGraphicObject> obstacles) {
        return GameState.builder()
                .bots(
                        bots.stream()
                                .map(tankToBotConverter::convertToBot)
                                .collect(Collectors.toList()))
                .obstacles(
                        obstacles.stream()
                                .map(gdxGraphicObjectToObstacleConverter::convertToObstacle)
                                .collect(Collectors.toList())
                )
                .player(tankToPlayerConverter.convertToPlayer(player))
                .build();
    }
}
