package ru.mipt.bit.platformer.processor.impl;

import ru.mipt.bit.platformer.objects.Activated;
import ru.mipt.bit.platformer.processor.GameObjectsActivator;

import java.util.ArrayList;
import java.util.List;

public class GameObjectsActivatorImpl implements GameObjectsActivator {
    private final List<Activated> activatedList = new ArrayList<>();

    public void addActivated(Activated activated) {
        activatedList.add(activated);
    }

    @Override
    public void activateAll(float deltaTime) {
        activatedList.forEach(activated -> activated.activate(deltaTime));
    }
}
