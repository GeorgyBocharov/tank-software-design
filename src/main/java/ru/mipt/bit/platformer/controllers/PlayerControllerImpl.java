package ru.mipt.bit.platformer.controllers;


import lombok.RequiredArgsConstructor;

import ru.mipt.bit.platformer.entities.Direction;
import ru.mipt.bit.platformer.keyboard.KeyboardChecker;
import ru.mipt.bit.platformer.movables.Movable;
import ru.mipt.bit.platformer.service.ActionMapper;
import ru.mipt.bit.platformer.service.CollisionDetectionManager;


@RequiredArgsConstructor
public class PlayerControllerImpl implements PlayerController {

    private final ActionMapper actionMapper;
    private final KeyboardChecker checker;
    private final Movable player;
    private final CollisionDetectionManager collisionDetectionManager;

    @Override
    public void movePlayer(float deltaTime) {
        for (int key: actionMapper.getMappedKeys()) {
            if (checker.isKeyPressed(key)) {
                Direction direction = actionMapper.getDirectionByKey(key);
                player.setRotation(direction.getRotation());
                if (!collisionDetectionManager.isCollisionPossible(player.getCoordinatesAfterShift(direction), player)) {
                    player.triggerMovement(direction);
                }
            }
        }
        player.move(deltaTime);
    }

}
