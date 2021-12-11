package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.objects.graphic.Disposable;
import ru.mipt.bit.platformer.publisher.Subscriber;

public interface GraphicLevel extends Disposable, Subscriber {
    void renderAll();
}
