package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import ru.mipt.bit.platformer.graphic.Disposable;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.convertPointToGridPoint;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.moveRectangleAtTileCenter;


@Getter
@ToString
@EqualsAndHashCode
public class LibGdxGraphicObject implements Disposable {

    private final Texture texture;
    private final TextureRegion textureRegion;
    private final TiledMapTileLayer tileLayer;
    private final Rectangle rectangle;
    private final CollidingObject collidingObject;

    public LibGdxGraphicObject(TiledMapTileLayer tileLayer, Texture texture, CollidingObject collidingObject) {
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
        this.tileLayer = tileLayer;
        this.collidingObject = collidingObject;
        moveRectangleAtTileCenter(tileLayer, rectangle, convertPointToGridPoint(collidingObject.getCoordinates()));
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
