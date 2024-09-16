package ru.mipt.bit.platformer.decorator;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.level.impl.LibGdxGraphicLevel;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.graphic.GraphicObjectRenderer;
import ru.mipt.bit.platformer.objects.graphic.impl.LibGdxGraphicHealthBar;

/**
 * Application level
 */
@RequiredArgsConstructor
public class HealthBarViewDecorator implements GraphicObjectRenderer {
    private final GraphicObjectRenderer wrapper;
    private final LibGdxGraphicHealthBar graphicHealthBar;
    private final LibGdxGraphicLevel level;

    @Override
    public void render() {
        wrapper.render();
        graphicHealthBar.render();
    }

    @Override
    public void renderShape() {
        wrapper.renderShape();
        if (level.printHealthBars()) {
            graphicHealthBar.renderShape();
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
