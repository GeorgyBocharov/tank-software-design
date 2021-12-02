package ru.mipt.bit.platformer.ai.creator.impl;

import lombok.RequiredArgsConstructor;
import org.awesome.ai.state.GameState;
import ru.mipt.bit.platformer.ai.converter.TankToPlayerConverter;
import ru.mipt.bit.platformer.ai.converter.ObstacleConverter;
import ru.mipt.bit.platformer.ai.converter.TankToBotConverter;
import ru.mipt.bit.platformer.ai.creator.GameStateCreator;
import ru.mipt.bit.platformer.objects.logic.LogicObstacle;
import ru.mipt.bit.platformer.objects.logic.LogicTank;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter
 */
@RequiredArgsConstructor
public class GameStateCreatorImpl implements GameStateCreator {

    private final TankToPlayerConverter tankToPlayerConverter;
    private final TankToBotConverter tankToBotConverter;
    private final ObstacleConverter gdxObstacleConverter;

    @Override
    public GameState createGameState(LogicTank player, List<LogicTank> bots, List<LogicObstacle> obstacles) {
        return GameState.builder()
                .bots(
                        bots.stream()
                                .map(tankToBotConverter::convertToBot)
                                .collect(Collectors.toList()))
                .obstacles(
                        obstacles.stream()
                                .map(gdxObstacleConverter::convertToObstacle)
                                .collect(Collectors.toList())
                )
                .player(tankToPlayerConverter.convertToPlayer(player))
                .build();
    }
}
