package ru.mipt.bit.platformer.movement.service;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.graphic.impl.LibGdxGraphicObject;

public interface LibGdxMovementService {
    void interpolateGameObjectCoordinates(Rectangle rectangle, GridPoint2 source,
                                          GridPoint2 destination, float movementProgress);
}
