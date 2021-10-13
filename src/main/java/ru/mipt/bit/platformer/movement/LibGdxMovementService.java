package ru.mipt.bit.platformer.movement;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entities.LibGdxGraphicObject;

public interface LibGdxMovementService {
    LibGdxGraphicObject interpolateGameObjectCoordinates(LibGdxGraphicObject graphicObject,
                                                         float movementProgress, GridPoint2 destination);
}
