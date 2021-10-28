package ru.mipt.bit.platformer.ai.adapter.impl;

import lombok.RequiredArgsConstructor;

import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;

import ru.mipt.bit.platformer.ai.adapter.BotAICommandsAdapter;
import ru.mipt.bit.platformer.ai.entities.converter.RecommendationToCommandConverter;
import ru.mipt.bit.platformer.ai.entities.creator.GameStateCreator;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.objects.LibGdxGraphicObject;
import ru.mipt.bit.platformer.objects.movable.impl.Tank;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BotAICommandsAdapterImpl implements BotAICommandsAdapter {

    private final AI ai;
    private final GameStateCreator gameStateCreator;
    private final RecommendationToCommandConverter recommendationToCommandConverter;

    private final List<Tank> bots;
    private final List<LibGdxGraphicObject> obstacles;
    private final Tank player;

    @Override
    public List<Command> generateBotCommands() {
        return generateRecommendations().stream()
                .map(recommendationToCommandConverter::convertToCommand)
                .collect(Collectors.toList());
    }

    private List<Recommendation> generateRecommendations() {
        GameState gameState = gameStateCreator.createGameState(player, bots, obstacles);
        return ai.recommend(gameState);
    }

}
