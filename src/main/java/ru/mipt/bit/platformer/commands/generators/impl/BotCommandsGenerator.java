package ru.mipt.bit.platformer.commands.generators.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.commands.CommandScheduler;
import ru.mipt.bit.platformer.commands.impl.MovementCommand;
import ru.mipt.bit.platformer.commands.generators.CommandsGenerator;
import ru.mipt.bit.platformer.geometry.Direction;
import ru.mipt.bit.platformer.objects.movable.Movable;
import ru.mipt.bit.platformer.collision.CollisionDetectionManager;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class BotCommandsGenerator implements CommandsGenerator {

    private final List<? extends Movable> bots;
    private final CollisionDetectionManager collisionDetectionManager;
    private final CommandScheduler commandScheduler;
    private final Random random = new Random();

    @Override
    public void generate(float deltaTime) {
        for (Movable bot : bots) {
            Direction direction = getRandomDirection();
            commandScheduler.scheduleCommand(new MovementCommand(collisionDetectionManager, bot, direction));
        }
    }

    private Direction getRandomDirection() {
        return Direction.values()[random.nextInt(Direction.values().length)];
    }

}
