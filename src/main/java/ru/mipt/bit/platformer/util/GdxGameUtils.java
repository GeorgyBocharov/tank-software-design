package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.entities.Direction;
import ru.mipt.bit.platformer.entities.GraphicObject;

import java.util.NoSuchElementException;

public final class GdxGameUtils {

    private GdxGameUtils() {
    }

    public static MapRenderer createSingleLayerMapRenderer(TiledMap tiledMap, Batch batch) {
        TiledMapTileLayer tileLayer = getSingleLayer(tiledMap);
        float viewWidth = tileLayer.getWidth() * tileLayer.getTileWidth();
        float viewHeight = tileLayer.getHeight() * tileLayer.getTileHeight();

        OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);
        mapRenderer.getViewBounds().set(0f, 0f, viewWidth, viewHeight);

        return mapRenderer;
    }

    public static <L extends MapLayer> L getSingleLayer(Map map) {
        MapLayers layers = map.getLayers();
        switch (layers.size()) {
            case 0:
                throw new NoSuchElementException("Map has no layers");
            case 1:
                @SuppressWarnings("unchecked")
                L layer = (L) layers.iterator().next();
                return layer;
            default:
                throw new IllegalArgumentException("Map has more than one layer");
        }
    }

    public static Vector2 moveRectangleAtTileCenter(TiledMapTileLayer tileLayer, Rectangle rectangle, GridPoint2 tileCoordinates) {
        Vector2 tileCenter = calculateTileCenter(tileLayer, tileCoordinates);
        rectangle.setCenter(tileCenter);
        return new Vector2(rectangle.x, rectangle.y);
    }

    public static GridPoint2 sumPoints(GridPoint2 first, GridPoint2 second) {
        return new GridPoint2(first).add(second);
    }

    public static void drawGraphicObjectsUnscaled(Batch batch, GraphicObject... graphicObjects) {
        for (GraphicObject graphicObject : graphicObjects) {
            TextureRegion region = graphicObject.getGraphics();
            Rectangle rectangle = graphicObject.getRectangle();
            int regionWidth = region.getRegionWidth();
            int regionHeight = region.getRegionHeight();
            float regionOriginX = regionWidth / 2f;
            float regionOriginY = regionHeight / 2f;
            batch.draw(
                    region, rectangle.x, rectangle.y, regionOriginX, regionOriginY,
                    regionWidth, regionHeight, 1f, 1f, graphicObject.getRotation()
            );
        }
    }

    public static Rectangle createBoundingRectangle(TextureRegion region) {
        return new Rectangle()
                .setWidth(region.getRegionWidth())
                .setHeight(region.getRegionHeight());
    }

    public static boolean collisionImpossible(GraphicObject first, GraphicObject second, Direction direction) {
        return !first.getCoordinates().equals(sumPoints(second.getCoordinates(), direction.getShift()));
    }

    private static Vector2 calculateTileCenter(TiledMapTileLayer tileLayer, GridPoint2 tileCoordinates) {
        int tileWidth = tileLayer.getTileWidth();
        int tileHeight = tileLayer.getTileHeight();
        int tileBottomLeftCornerX = tileCoordinates.x * tileWidth;
        int tileBottomLeftCornerY = tileCoordinates.y * tileHeight;

        return new Rectangle()
                .setX(tileBottomLeftCornerX)
                .setY(tileBottomLeftCornerY)
                .setWidth(tileWidth)
                .setHeight(tileHeight)
                .getCenter(new Vector2());
    }
}
