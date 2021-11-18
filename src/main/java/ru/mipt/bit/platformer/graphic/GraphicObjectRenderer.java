package ru.mipt.bit.platformer.graphic;


import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.objects.Activated;

public interface GraphicObjectRenderer extends Disposable {
    void render(Batch batch);
    boolean hasActivated(Activated activated);
}
