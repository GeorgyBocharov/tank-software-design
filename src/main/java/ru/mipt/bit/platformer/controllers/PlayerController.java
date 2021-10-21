package ru.mipt.bit.platformer.controllers;


import ru.mipt.bit.platformer.commands.CommandScheduler;
import ru.mipt.bit.platformer.entities.Direction;
import ru.mipt.bit.platformer.keyboard.KeyboardChecker;
import ru.mipt.bit.platformer.movables.Movable;
import ru.mipt.bit.platformer.service.ActionMapper;
import ru.mipt.bit.platformer.service.CollisionDetectionManager;

import java.util.Optional;


public class PlayerController extends AbstractController {

    private final ActionMapper actionMapper;
    private final KeyboardChecker checker;
    private final Movable player;

    public PlayerController(ActionMapper actionMapper, KeyboardChecker checker, Movable player, CollisionDetectionManager collisionDetectionManager, CommandScheduler commandScheduler) {
        super(collisionDetectionManager, commandScheduler);
        this.actionMapper = actionMapper;
        this.checker = checker;
        this.player = player;
    }

    @Override
    public void movePlayer(float deltaTime) {
        getDirection().ifPresent(direction -> scheduleMovementCommand(player, direction));
        player.move(deltaTime);
    }

    private Optional<Direction> getDirection() {
        Direction direction = null;
        for (int key: actionMapper.getMappedKeys()) {
            if (checker.isKeyPressed(key)) {
                direction = actionMapper.getDirectionByKey(key);
                break;
            }
        }
        return Optional.ofNullable(direction);
    }

}
