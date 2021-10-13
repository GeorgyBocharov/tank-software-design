package ru.mipt.bit.platformer.service.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.service.Colliding;

import java.util.List;

@RequiredArgsConstructor
public class CollisionDetectionManagerImpl implements ru.mipt.bit.platformer.service.CollisionDetectionManager {

    private final List<Colliding> collidingObjects;

    @Override
    public boolean isCollisionPossible(Point position, Colliding objectToCheck) {
        for (Colliding collidingObject : collidingObjects) {
            if (collidingObject.isCollisionPossible(position) && !collidingObject.equals(objectToCheck)) {
                return true;
            }
        }
        return false;
    }

}
