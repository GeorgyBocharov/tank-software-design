package ru.mipt.bit.platformer.ai.converter.impl;

import lombok.RequiredArgsConstructor;
import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import ru.mipt.bit.platformer.ai.converter.RecommendationToCommandConverter;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.impl.MovementCommand;
import ru.mipt.bit.platformer.commands.impl.ShootCommand;
import ru.mipt.bit.platformer.placement.Direction;
import ru.mipt.bit.platformer.objects.logic.LogicTank;

/**
 * Adapter
 */
@RequiredArgsConstructor
public class RecommendationToCommandConverterImpl implements RecommendationToCommandConverter {

    @Override
    public Command convertToCommand(Recommendation recommendation) {
        LogicTank bot = (LogicTank) recommendation.getActor().getSource();
        Action action = recommendation.getAction();
        switch (action) {
            case MoveNorth: {
                return new MovementCommand(bot, Direction.UP);
            }
            case MoveSouth: {
                return new MovementCommand(bot, Direction.DOWN);
            }
            case MoveWest: {
                return new MovementCommand(bot, Direction.LEFT);
            }
            case MoveEast: {
                return new MovementCommand(bot, Direction.RIGHT);
            }
            case Shoot: {
                return new ShootCommand(bot);
            }
            default:
                throw new RuntimeException("Unexpected action " + action);
        }
    }
}
