package ru.mipt.bit.platformer.publisher;

import ru.mipt.bit.platformer.objects.GameObject;

import java.util.List;

public interface Subscriber {
    void registerGameObjects(List<? extends GameObject> newGameObjects);
    void unregisterGameObjects(List<? extends GameObject> deletedGameObjects);
}
