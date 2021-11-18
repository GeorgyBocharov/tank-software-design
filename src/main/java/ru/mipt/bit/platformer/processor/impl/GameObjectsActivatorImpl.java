package ru.mipt.bit.platformer.processor.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.objects.Activated;
import ru.mipt.bit.platformer.processor.GameObjectsActivator;
import ru.mipt.bit.platformer.publisher.Subscriber;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GameObjectsActivatorImpl implements GameObjectsActivator {
    private final List<Activated> activatedList;
    private final List<Subscriber> subscribers = new ArrayList<>();

    @Override
    public void activateAll(float deltaTime) {
        activatedList.forEach(activated -> activated.activate(deltaTime));
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers() {
        subscribers.forEach(Subscriber::update);
    }
}
