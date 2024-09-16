package ru.mipt.bit.platformer.objects.graphic;


import ru.mipt.bit.platformer.objects.GameObject;

/**
 * Port between application and adapter levels
 */
public interface GraphicObjectRenderer extends Disposable {
    void render();
    boolean hasGameObject(GameObject gameObject);
    default void renderShape() {}
}
