package ru.mipt.bit.platformer.processor;

import ru.mipt.bit.platformer.publisher.Publisher;

public interface GameObjectsActivator extends Publisher {
    void activateAll(float deltaTime);
}
