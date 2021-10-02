package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.entities.AgileGraphicObject;

import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class TileMovement {

    private final TiledMapTileLayer tileLayer;
    private final Interpolation interpolation;

    public TileMovement(TiledMapTileLayer tileLayer, Interpolation interpolation) {
        this.tileLayer = tileLayer;
        this.interpolation = interpolation;
    }

    public void interpolateAgileObjectCoordinates(AgileGraphicObject agileObject) {
        Rectangle rectangle = agileObject.getRectangle();
        float progress = agileObject.getMovementProgress();

        Vector2 from = moveRectangleAtTileCenter(tileLayer, rectangle, agileObject.getCoordinates());
        Vector2 to = moveRectangleAtTileCenter(tileLayer, rectangle, agileObject.getDestinationCoordinates());

        float intermediateBottomLeftX = interpolation.apply(from.x, to.x, progress);
        float intermediateBottomLeftY = interpolation.apply(from.y, to.y, progress);

        rectangle
                .setX(intermediateBottomLeftX)
                .setY(intermediateBottomLeftY);
    }
}
