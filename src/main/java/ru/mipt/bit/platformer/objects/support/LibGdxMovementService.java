package ru.mipt.bit.platformer.objects.support;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public interface LibGdxMovementService {
    void interpolateGameObjectCoordinates(Rectangle rectangle, GridPoint2 source,
                                          GridPoint2 destination, float movementProgress);
}
