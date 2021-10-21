package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.commands.CommandScheduler;
import ru.mipt.bit.platformer.entities.Direction;
import ru.mipt.bit.platformer.movables.Movable;
import ru.mipt.bit.platformer.service.CollisionDetectionManager;

import java.util.List;
import java.util.Random;

public class BotController extends AbstractController {

    private final List<? extends Movable> bots;
    private final Random random = new Random();


    public BotController(List<? extends Movable> bots, CollisionDetectionManager collisionDetectionManager, CommandScheduler commandScheduler) {
        super(collisionDetectionManager, commandScheduler);
        this.bots = bots;
    }

    @Override
    public void movePlayer(float deltaTime) {
        for (Movable bot : bots) {
            Direction direction = getRandomDirection();
            scheduleMovementCommand(bot, direction);
            bot.move(deltaTime);
        }
    }

    private Direction getRandomDirection() {
        return Direction.values()[random.nextInt(Direction.values().length)];
    }

}
