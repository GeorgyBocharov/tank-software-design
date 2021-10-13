package ru.mipt.bit.platformer.service;

import ru.mipt.bit.platformer.geometry.Point;

public interface Colliding {
    boolean isCollisionPossible(Point othersCoordinates);
}
