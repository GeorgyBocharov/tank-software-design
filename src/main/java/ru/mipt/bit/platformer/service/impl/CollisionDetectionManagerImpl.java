package ru.mipt.bit.platformer.service.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.service.Colliding;
import ru.mipt.bit.platformer.service.CollisionDetectionManager;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CollisionDetectionManagerImpl implements CollisionDetectionManager {

    private final List<Colliding> collidingObjects = new ArrayList<>();
    private final int maxX;
    private final int maxY;

    @Override
    public void addColliding(Colliding colliding) {
        collidingObjects.add(colliding);
    }

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
