package ru.mipt.bit.platformer.collision;

import ru.mipt.bit.platformer.geometry.Point;


public interface CollisionDetectionManager {
    boolean isCollisionPossible(Point position, Colliding objectToCheck);
}
