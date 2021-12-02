package ru.mipt.bit.platformer.objects.graphic.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.objects.graphic.LibGdxBatchDrawer;
import ru.mipt.bit.platformer.objects.graphic.GraphicObjectRenderer;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.logic.LogicObstacle;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.convertPointToGridPoint;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.moveRectangleAtTileCenter;

public class LibGdxGraphicObstacle implements GraphicObjectRenderer {

    private final LogicObstacle obstacle;
    private final LibGdxBatchDrawer libGdxBatchDrawer;
    private final Batch batch;

    public LibGdxGraphicObstacle(LogicObstacle obstacle, TiledMapTileLayer tileLayer, Batch batch, Texture texture) {
        this.obstacle = obstacle;
        this.batch = batch;
        TextureRegion textureRegion = new TextureRegion(texture);
        Rectangle rectangle = createBoundingRectangle(textureRegion);
        this.libGdxBatchDrawer = new LibGdxBatchDrawerImpl(texture, textureRegion, rectangle);

        moveRectangleAtTileCenter(tileLayer, rectangle, convertPointToGridPoint(obstacle.getPosition().getCoordinates()));
    }

    @Override
    public void dispose() {
        libGdxBatchDrawer.dispose();
    }

    @Override
    public void render() {
        float rotation = obstacle.getPosition().getOrientation().getValue();
        libGdxBatchDrawer.drawRectangle(batch, rotation);
    }

    @Override
    public boolean hasGameObject(GameObject gameObject) {
        return obstacle == gameObject;
    }
}
