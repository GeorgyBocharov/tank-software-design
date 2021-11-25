package ru.mipt.bit.platformer.decorator;

import com.badlogic.gdx.graphics.g2d.Batch;
import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.level.impl.LibGdxGraphicLevel;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.graphic.GraphicObjectRenderer;
import ru.mipt.bit.platformer.objects.graphic.impl.LibGdxGraphicHealthBar;

@RequiredArgsConstructor
public class HealthBarViewDecorator implements GraphicObjectRenderer {
    private final GraphicObjectRenderer wrapper;
    private final LibGdxGraphicHealthBar graphicHealthBar;
    private final LibGdxGraphicLevel level;

    @Override
    public void render(Batch batch) {
        wrapper.render(batch);
        if (level.printHealthBars()) {
            graphicHealthBar.render(batch);
        }
    }

    @Override
    public boolean hasGameObject(GameObject gameObject) {
        return wrapper.hasGameObject(gameObject);
    }

    @Override
    public void dispose() {
        wrapper.dispose();
    }
}
