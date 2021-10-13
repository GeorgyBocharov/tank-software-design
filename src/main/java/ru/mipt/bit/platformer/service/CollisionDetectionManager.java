package ru.mipt.bit.platformer.service;

import ru.mipt.bit.platformer.geometry.Point;


public interface CollisionDetectionManager {
    boolean isCollisionPossible(Point position, Colliding objectToCheck);
}
