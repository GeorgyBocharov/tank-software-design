package ru.mipt.bit.platformer.commands.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.entities.Direction;
import ru.mipt.bit.platformer.movables.Movable;
import ru.mipt.bit.platformer.service.CollisionDetectionManager;

@RequiredArgsConstructor
public class MovementCommand implements Command {

    private final CollisionDetectionManager collisionDetectionManager;
    private final Movable movable;
    private final Direction direction;

    @Override
    public void execute() {
        movable.setRotation(direction.getRotation());
        if (!collisionDetectionManager.isCollisionPossible(movable.getCoordinatesAfterShift(direction), movable)) {
            movable.triggerMovement(direction);
        }
    }
}
