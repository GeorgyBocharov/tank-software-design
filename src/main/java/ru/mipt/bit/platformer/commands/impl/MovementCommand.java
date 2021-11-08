package ru.mipt.bit.platformer.commands.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.geometry.Direction;
import ru.mipt.bit.platformer.objects.Movable;
import ru.mipt.bit.platformer.collision.CollisionDetectionManager;

@RequiredArgsConstructor
public class MovementCommand implements Command {

    private final CollisionDetectionManager collisionDetectionManager;
    private final Movable movable;
    private final Direction direction;

    @Override
    public void execute() {
        boolean isCollisionPossible = collisionDetectionManager
                .isCollisionPossible(movable.getCoordinatesAfterShift(direction), movable);

        movable.triggerMovement(direction, isCollisionPossible);
    }
}
