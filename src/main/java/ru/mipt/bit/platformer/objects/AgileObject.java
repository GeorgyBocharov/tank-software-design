package ru.mipt.bit.platformer.objects;

import ru.mipt.bit.platformer.placement.Point;
import ru.mipt.bit.platformer.placement.Position;

public interface AgileObject {
    Position getPosition();
    Point getDestinationCoordinates();
    float getMovementProgress();
}
