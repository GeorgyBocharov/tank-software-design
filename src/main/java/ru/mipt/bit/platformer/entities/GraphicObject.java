package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class GraphicObject {

    protected TextureRegion graphics;
    protected Rectangle rectangle;
    protected GridPoint2 coordinates;
    protected float rotation;

    public GraphicObject(Texture texture, GridPoint2 coordinates, float rotation) {
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
        this.coordinates = coordinates;
        this.rotation = rotation;
    }

    public GraphicObject(Texture texture, GridPoint2 coordinates) {
        this(texture, coordinates, 0f);
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
