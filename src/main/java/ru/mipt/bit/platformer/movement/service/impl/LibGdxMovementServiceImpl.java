package ru.mipt.bit.platformer.movement.service.impl;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import ru.mipt.bit.platformer.movement.service.LibGdxMovementService;
import ru.mipt.bit.platformer.graphic.impl.LibGdxGraphicObject;
import ru.mipt.bit.platformer.geometry.Point;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.convertPointToGridPoint;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.moveRectangleAtTileCenter;

@AllArgsConstructor
public class LibGdxMovementServiceImpl implements LibGdxMovementService {
    private final Interpolation interpolation;
    private final TiledMapTileLayer tileLayer;


    @Override
    public void interpolateGameObjectCoordinates(Rectangle rectangle, GridPoint2 source,
                                                                GridPoint2 destination, float movementProgress) {


        Vector2 from = moveRectangleAtTileCenter(tileLayer, rectangle, source);
        Vector2 to = moveRectangleAtTileCenter(tileLayer, rectangle, destination);

        float intermediateBottomLeftX = interpolation.apply(from.x, to.x, movementProgress);
        float intermediateBottomLeftY = interpolation.apply(from.y, to.y, movementProgress);

        rectangle
                .setX(intermediateBottomLeftX)
                .setY(intermediateBottomLeftY);

    }

}
