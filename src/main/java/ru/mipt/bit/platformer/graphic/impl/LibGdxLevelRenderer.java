package ru.mipt.bit.platformer.graphic.impl;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.graphic.GraphicObjectRenderer;
import ru.mipt.bit.platformer.graphic.LevelRender;
import ru.mipt.bit.platformer.graphic.Disposable;
import ru.mipt.bit.platformer.objects.Activated;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class LibGdxLevelRenderer implements LevelRender {

    private final Batch batch;
    private final MapRenderer levelRenderer;
    private final List<GraphicObjectRenderer> renderers;

    @Override
    public void renderAll() {
        levelRenderer.render();
        batch.begin();
        renderers.forEach(renderer -> renderer.render(batch));
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        renderers.forEach(Disposable::dispose);
    }

    @Override
    public void updateWithNew(Activated newActivated) {

    }

    @Override
    public void updateWithDeleted(Activated deletedActivated) {
        for (GraphicObjectRenderer renderer: renderers) {
            if (renderer.hasActivated(deletedActivated)) {
                renderers.remove(renderer);
                break;
            }
        }
    }
}
