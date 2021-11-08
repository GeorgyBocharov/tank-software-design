package ru.mipt.bit.platformer.processor.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.objects.Activated;
import ru.mipt.bit.platformer.processor.GameObjectsActivator;

import java.util.List;

@RequiredArgsConstructor
public class GameObjectsActivatorImpl implements GameObjectsActivator {
    private final List<Activated> activatedList;

    @Override
    public void activateAll(float deltaTime) {
        activatedList.forEach(activated -> activated.activate(deltaTime));
    }
}
