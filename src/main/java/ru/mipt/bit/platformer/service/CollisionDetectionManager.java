package ru.mipt.bit.platformer.service;

import ru.mipt.bit.platformer.geometry.Point;


public interface CollisionDetectionManager {
    void addColliding(Colliding colliding);
    boolean isCollisionPossible(Point position, Colliding objectToCheck);
}
