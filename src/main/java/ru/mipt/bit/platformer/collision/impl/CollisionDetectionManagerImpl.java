package ru.mipt.bit.platformer.collision.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.collision.Colliding;
import ru.mipt.bit.platformer.collision.CollisionDetectionManager;

import java.util.List;

@RequiredArgsConstructor
public class CollisionDetectionManagerImpl implements CollisionDetectionManager {

    private final List<Colliding> collidingObjects;
    private final int maxX;
    private final int maxY;

    @Override
    public boolean isCollisionPossible(Point position, Colliding objectToCheck) {
        if (position.getX() >= maxX || position.getY() >= maxY || position.getX() < 0 || position.getY() < 0) {
            return true;
        }
        for (Colliding collidingObject : collidingObjects) {
            if (collidingObject.isCollisionPossible(position) && collidingObject != objectToCheck) {
                return true;
            }
        }
        return false;
    }

}
