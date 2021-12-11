package ru.mipt.bit.platformer.objects;

import ru.mipt.bit.platformer.placement.Direction;

public interface Movable {
    void triggerMovement(Direction direction);
}
