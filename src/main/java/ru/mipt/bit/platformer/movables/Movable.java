package ru.mipt.bit.platformer.movables;

import ru.mipt.bit.platformer.entities.Direction;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.service.Colliding;

public interface Movable extends Colliding {
    void move(float deltaTime);
    void setRotation(float rotation);
    void triggerMovement(Direction direction);
    Point getCoordinatesAfterShift(Direction direction);
}
