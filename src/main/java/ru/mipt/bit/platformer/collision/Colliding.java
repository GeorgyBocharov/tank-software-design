package ru.mipt.bit.platformer.collision;

import ru.mipt.bit.platformer.geometry.Point;

public interface Colliding {
    boolean isCollisionPossible(Point othersCoordinates);
}
