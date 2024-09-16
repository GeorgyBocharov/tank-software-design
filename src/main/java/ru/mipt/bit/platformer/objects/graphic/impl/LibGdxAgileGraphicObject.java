package ru.mipt.bit.platformer.objects.graphic.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.AgileObject;
import ru.mipt.bit.platformer.objects.graphic.LibGdxBatchDrawer;
import ru.mipt.bit.platformer.placement.Point;
import ru.mipt.bit.platformer.objects.graphic.GraphicObjectRenderer;
import ru.mipt.bit.platformer.objects.support.LibGdxMovementService;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.utils.GdxGameUtils;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.convertPointToGridPoint;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.moveRectangleAtTileCenter;

/**
 * Adapter level
 */
public class LibGdxAgileGraphicObject implements GraphicObjectRenderer {
    private final AgileObject agileObject;

    private final LibGdxMovementService libGdxMovementService;
    private final LibGdxBatchDrawer libGdxBatchDrawer;
    private final Rectangle rectangle;
    private final Batch batch;

    public LibGdxAgileGraphicObject(AgileObject agileObject, LibGdxMovementService libGdxMovementService,
                                    TiledMapTileLayer tileLayer, Batch batch, Texture texture) {

        this.agileObject = agileObject;
        this.libGdxMovementService = libGdxMovementService;
        this.batch = batch;

        TextureRegion textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.libGdxBatchDrawer = new LibGdxBatchDrawerImpl(texture, textureRegion, rectangle);

        moveRectangleAtTileCenter(tileLayer, rectangle, convertPointToGridPoint(agileObject.getPosition().getCoordinates()));
    }

    @Override
    public void dispose() {
        libGdxBatchDrawer.dispose();
    }

    @Override
    public void render() {
        moveTankRectangle();
        float rotation = agileObject.getPosition().getOrientation().getValue();
        libGdxBatchDrawer.drawRectangle(batch, rotation);
    }

    @Override
    public boolean hasGameObject(GameObject gameObject) {
        return agileObject == gameObject;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    private void moveTankRectangle() {
        Point source = agileObject.getPosition().getCoordinates();
        Point dest = agileObject.getDestinationCoordinates();

        libGdxMovementService
                .interpolateGameObjectCoordinates(
                        rectangle,
                        GdxGameUtils.convertPointToGridPoint(source),
                        GdxGameUtils.convertPointToGridPoint(dest),
                        agileObject.getMovementProgress()
                );
    }
}
