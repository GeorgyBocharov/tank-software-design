package ru.mipt.bit.platformer.movement;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import ru.mipt.bit.platformer.entities.LibGdxGraphicObject;
import ru.mipt.bit.platformer.geometry.Point;

import static ru.mipt.bit.platformer.util.GdxGameUtils.convertPointToGridPoint;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

@AllArgsConstructor
public class LibGdxMovementServiceImpl implements LibGdxMovementService {
    private final Interpolation interpolation;


    @Override
    public LibGdxGraphicObject interpolateGameObjectCoordinates(LibGdxGraphicObject graphicObject,
                                                                float movementProgress, GridPoint2 destination) {

        Rectangle rectangle = graphicObject.getRectangle();
        TiledMapTileLayer tileLayer = graphicObject.getTileLayer();


        Point source = graphicObject.getLogicObject().getCoordinates();
        Vector2 from = moveRectangleAtTileCenter(tileLayer, rectangle, convertPointToGridPoint(source));
        Vector2 to = moveRectangleAtTileCenter(tileLayer, rectangle, destination);

        float intermediateBottomLeftX = interpolation.apply(from.x, to.x, movementProgress);
        float intermediateBottomLeftY = interpolation.apply(from.y, to.y, movementProgress);

        rectangle
                .setX(intermediateBottomLeftX)
                .setY(intermediateBottomLeftY);

        return graphicObject;
    }

}
