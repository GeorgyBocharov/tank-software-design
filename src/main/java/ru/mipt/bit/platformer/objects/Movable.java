package ru.mipt.bit.platformer.objects;

import ru.mipt.bit.platformer.geometry.Direction;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.collision.Colliding;
import ru.mipt.bit.platformer.objects.Activated;
import ru.mipt.bit.platformer.objects.Orientation;

public interface Movable extends Colliding, Activated {
    void triggerMovement(Direction direction, boolean isCollisionPossible);
    Point getCoordinatesAfterShift(Direction direction);
}
