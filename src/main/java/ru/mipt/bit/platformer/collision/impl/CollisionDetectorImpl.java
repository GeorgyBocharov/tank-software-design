package ru.mipt.bit.platformer.collision.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.placement.Point;
import ru.mipt.bit.platformer.objects.Colliding;
import ru.mipt.bit.platformer.collision.CollisionDetector;
import ru.mipt.bit.platformer.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Use-case
 */
@RequiredArgsConstructor
public class CollisionDetectorImpl implements CollisionDetector {

    private final List<Colliding> collidingObjects = new ArrayList<>();
    private final int maxX;
    private final int maxY;

    @Override
    public boolean isCollisionPossible(Colliding objectToCheck) {
        if (isOutOfGameRange(objectToCheck)) return true;
        for (Colliding collidingObject : collidingObjects) {
            if (objectToCheck != collidingObject && objectToCheck.collides(collidingObject)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void registerGameObjects(List<? extends GameObject> newGameObjects) {
        collidingObjects.addAll(newGameObjects);
    }

    @Override
    public void unregisterGameObjects(List<? extends GameObject> deletedGameObjects) {
        collidingObjects.removeAll(deletedGameObjects);
    }

    private boolean isOutOfGameRange(Colliding objectToCheck) {
        for (Point point: objectToCheck.getCurrentPoints()) {
            if (isPointOutOfGameRange(point)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPointOutOfGameRange(Point position) {
        return position.getX() >= maxX || position.getY() >= maxY || position.getX() < 0 || position.getY() < 0;
    }
}
