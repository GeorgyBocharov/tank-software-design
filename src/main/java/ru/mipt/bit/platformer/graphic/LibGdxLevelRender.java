package ru.mipt.bit.platformer.graphic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.service.Disposable;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class LibGdxLevelRender implements LevelRender {

    private final Batch batch;
    private final MapRenderer levelRenderer;

    List<GraphicObjectRenderer> renderers = new ArrayList<>();

    public void addRenderer(GraphicObjectRenderer graphicObjectRenderer) {
        renderers.add(graphicObjectRenderer);
    }

    @Override
    public void renderAll() {
        levelRenderer.render();
        batch.begin();
        renderers.forEach(GraphicObjectRenderer::render);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        renderers.forEach(Disposable::dispose);
    }
}
