package ru.mipt.bit.platformer.objects.graphic.impl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.Vulnerable;
import ru.mipt.bit.platformer.objects.graphic.GraphicObjectRenderer;

/**
 * Adapter level
 */
public class LibGdxGraphicHealthBar implements GraphicObjectRenderer {

    private static final float MAX_WIDTH = 50;
    private static final float HEIGHT = 10;

    private final Vulnerable vulnerableObject;
    private final Rectangle rectangle;
    private final float healthBarWidth;
    private final float healthBarHeight;
    private final ShapeRenderer shapeRenderer;

    private float width;

    public LibGdxGraphicHealthBar(ShapeRenderer shapeRenderer, Vulnerable vulnerableObject, Rectangle rectangle, Texture texture) {
        this.shapeRenderer = shapeRenderer;
        this.vulnerableObject = vulnerableObject;
        this.rectangle = rectangle;

        this.healthBarWidth = texture.getWidth() * 0.25f;
        this.healthBarHeight = texture.getHeight() * 0.75f;
    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {
    }

    @Override
    public void renderShape() {
        shapeRenderer.setColor(Color.RED);
        recalculateWidth();
        shapeRenderer.rect(rectangle.x + healthBarWidth, rectangle.y + healthBarHeight, width, HEIGHT);
    }

    private void recalculateWidth() {
        width =  MAX_WIDTH * vulnerableObject.getHP() / vulnerableObject.getMaxHP();
    }

    @Override
    public boolean hasGameObject(GameObject gameObject) {
        return false;
    }

}
