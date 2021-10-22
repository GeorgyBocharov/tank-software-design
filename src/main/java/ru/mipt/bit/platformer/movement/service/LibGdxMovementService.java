package ru.mipt.bit.platformer.movement.service;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.LibGdxGraphicObject;

public interface LibGdxMovementService {
    LibGdxGraphicObject interpolateGameObjectCoordinates(LibGdxGraphicObject graphicObject,
                                                         float movementProgress, GridPoint2 destination);
}
