package ru.mipt.bit.platformer.controllers.impl;

import lombok.RequiredArgsConstructor;

import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;

import ru.mipt.bit.platformer.ai.converter.RecommendationToCommandConverter;
import ru.mipt.bit.platformer.ai.creator.GameStateCreator;
import ru.mipt.bit.platformer.commands.CommandScheduler;
import ru.mipt.bit.platformer.controllers.Controller;
import ru.mipt.bit.platformer.objects.logic.LogicObstacle;
import ru.mipt.bit.platformer.objects.logic.LogicTank;

import java.util.List;

/**
 * Adapter interface implementation
 */
@RequiredArgsConstructor
public class BotAICommandsAdapter implements Controller {

    private final CommandScheduler commandScheduler;
    private final AI ai;
    private final GameStateCreator gameStateCreator;
    private final RecommendationToCommandConverter recommendationToCommandConverter;

    private final List<LogicTank> bots;
    private final List<LogicObstacle> positions;
    private final LogicTank player;

    @Override
    public void generate(float deltaTime) {
        generateRecommendations().stream()
                .map(recommendationToCommandConverter::convertToCommand)
                .forEach(commandScheduler::scheduleCommand);
    }

    private List<Recommendation> generateRecommendations() {
        GameState gameState = gameStateCreator.createGameState(player, bots, positions);
        return ai.recommend(gameState);
    }
}
