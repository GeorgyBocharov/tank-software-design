package ru.mipt.bit.platformer.objects.graphic.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.graphic.GraphicObjectRenderer;
import ru.mipt.bit.platformer.objects.graphic.LibGdxBatchDrawer;
import ru.mipt.bit.platformer.objects.logic.LogicHealthBar;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.createBoundingRectangle;

public class LibGdxGraphicHealthBar implements GraphicObjectRenderer {

    private final LogicHealthBar healthBar;
    private final LibGdxBatchDrawer libGdxBatchDrawer;

    public LibGdxGraphicHealthBar(LogicHealthBar healthBar, Texture texture) {
        this.healthBar = healthBar;
        TextureRegion textureRegion = new TextureRegion(texture);
        Rectangle rectangle = createBoundingRectangle(textureRegion);
        this.libGdxBatchDrawer = new LibGdxBatchDrawerImpl(texture, textureRegion, rectangle);

    }

    @Override
    public void dispose() {
        libGdxBatchDrawer.dispose();
    }

    @Override
    public void render(Batch batch) {
        System.out.println("Rendering health bar");
    }

    @Override
    public boolean hasGameObject(GameObject gameObject) {
        return false;
    }

}
