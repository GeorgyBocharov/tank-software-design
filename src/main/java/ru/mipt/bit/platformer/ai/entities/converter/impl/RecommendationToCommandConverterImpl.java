package ru.mipt.bit.platformer.ai.entities.converter.impl;

import lombok.RequiredArgsConstructor;
import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import ru.mipt.bit.platformer.ai.entities.converter.RecommendationToCommandConverter;
import ru.mipt.bit.platformer.collision.CollisionDetectionManager;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.impl.MovementCommand;
import ru.mipt.bit.platformer.geometry.Direction;
import ru.mipt.bit.platformer.objects.movable.impl.Tank;

@RequiredArgsConstructor
public class RecommendationToCommandConverterImpl implements RecommendationToCommandConverter {

    private final CollisionDetectionManager collisionDetectionManager;

    @Override
    public Command convertToCommand(Recommendation recommendation) {
        Tank bot = (Tank) recommendation.getActor().getSource();
        Action action = recommendation.getAction();
        switch (action) {
            case MoveNorth: {
                return new MovementCommand(collisionDetectionManager, bot, Direction.UP);
            }
            case MoveSouth: {
                return new MovementCommand(collisionDetectionManager, bot, Direction.DOWN);
            }
            case MoveWest: {
                return new MovementCommand(collisionDetectionManager, bot, Direction.LEFT);
            }
            case MoveEast: {
                return new MovementCommand(collisionDetectionManager, bot, Direction.RIGHT);
            }
            case Shoot: {
                return () -> System.out.printf(
                        "Bot %d with orientation %s is shooting\n",
                        bot.hashCode(),
                        bot.getGraphicObject().getLogicObject().getOrientation()
                );
            }
            default:
                throw new RuntimeException("Unexpected action " + action);
        }
    }
}
