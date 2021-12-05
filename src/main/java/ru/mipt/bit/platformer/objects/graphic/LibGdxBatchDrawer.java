package ru.mipt.bit.platformer.objects.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Adapter level
 */
public interface LibGdxBatchDrawer extends Disposable {
    void drawRectangle(Batch batch, float rotation);
}
