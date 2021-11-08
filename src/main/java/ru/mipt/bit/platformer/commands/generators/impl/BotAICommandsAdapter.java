package ru.mipt.bit.platformer.commands.generators.impl;

import lombok.RequiredArgsConstructor;

import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;

import ru.mipt.bit.platformer.ai.converter.RecommendationToCommandConverter;
import ru.mipt.bit.platformer.ai.creator.GameStateCreator;
import ru.mipt.bit.platformer.commands.CommandScheduler;
import ru.mipt.bit.platformer.commands.generators.CommandsGenerator;
import ru.mipt.bit.platformer.objects.CollidingObject;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.List;

@RequiredArgsConstructor
public class BotAICommandsAdapter implements CommandsGenerator {

    private final CommandScheduler commandScheduler;
    private final AI ai;
    private final GameStateCreator gameStateCreator;
    private final RecommendationToCommandConverter recommendationToCommandConverter;

    private final List<Tank> bots;
    private final List<CollidingObject> collidingObjects;
    private final Tank player;

    @Override
    public void generate(float deltaTime) {
        generateRecommendations().stream()
                .map(recommendationToCommandConverter::convertToCommand)
                .forEach(commandScheduler::scheduleCommand);
    }

    private List<Recommendation> generateRecommendations() {
        GameState gameState = gameStateCreator.createGameState(player, bots, collidingObjects);
        return ai.recommend(gameState);
    }
}
