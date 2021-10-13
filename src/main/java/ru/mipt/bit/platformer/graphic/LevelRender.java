package ru.mipt.bit.platformer.graphic;

import ru.mipt.bit.platformer.service.Disposable;

public interface LevelRender extends Disposable {
    void renderAll();
    void addRenderer(GraphicObjectRenderer graphicObjectRenderer);
}
