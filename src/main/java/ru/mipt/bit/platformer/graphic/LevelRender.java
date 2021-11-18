package ru.mipt.bit.platformer.graphic;

import ru.mipt.bit.platformer.publisher.Subscriber;

public interface LevelRender extends Disposable, Subscriber {
    void renderAll();
}
