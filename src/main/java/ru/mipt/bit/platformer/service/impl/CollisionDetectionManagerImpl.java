package ru.mipt.bit.platformer.service.impl;

import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.service.Colliding;
import ru.mipt.bit.platformer.service.CollisionDetectionManager;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetectionManagerImpl implements CollisionDetectionManager {

    private final List<Colliding> collidingObjects = new ArrayList<>();

    @Override
    public void addColliding(Colliding colliding) {
        collidingObjects.add(colliding);
    }

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
