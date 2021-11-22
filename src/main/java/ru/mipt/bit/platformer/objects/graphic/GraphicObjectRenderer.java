package ru.mipt.bit.platformer.objects.graphic;


import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.objects.GameObject;

public interface GraphicObjectRenderer extends Disposable {
    void render(Batch batch);
    boolean hasGameObject(GameObject gameObject);
}
