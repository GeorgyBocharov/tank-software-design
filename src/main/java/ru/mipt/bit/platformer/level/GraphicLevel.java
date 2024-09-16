package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.objects.graphic.Disposable;
import ru.mipt.bit.platformer.publisher.Subscriber;

/**
 * Port between application and adapter levels
 */
public interface GraphicLevel extends Disposable, Subscriber {
    void renderAll();
}
