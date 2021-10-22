package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.collision.Colliding;
import ru.mipt.bit.platformer.graphic.Disposable;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.convertPointToGridPoint;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.moveRectangleAtTileCenter;


@Getter
@ToString
@EqualsAndHashCode
public class LibGdxGraphicObject implements Disposable, Colliding, Activated {

    private final Texture texture;
    private final TextureRegion textureRegion;
    private final TiledMapTileLayer tileLayer;
    private final Rectangle rectangle;
    private final LogicObject logicObject;

    public LibGdxGraphicObject(TiledMapTileLayer tileLayer, Texture texture, LogicObject logicObject) {
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.logicObject = logicObject;
        this.tileLayer = tileLayer;
        moveRectangleAtTileCenter(tileLayer, rectangle, convertPointToGridPoint(logicObject.getCoordinates()));
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public boolean isCollisionPossible(Point othersCoordinates) {
        return logicObject.isCollisionPossible(othersCoordinates);
    }

    @Override
    public void activate(float deltaTime) {}
}
