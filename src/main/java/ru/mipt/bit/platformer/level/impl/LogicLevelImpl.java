package ru.mipt.bit.platformer.level.impl;

import ru.mipt.bit.platformer.level.LogicLevel;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.publisher.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Use-case class
 * Describes logic
 */
public class LogicLevelImpl implements LogicLevel {
    private final List<GameObject> activeGameObjects = new ArrayList<>();
    private final List<GameObject> inactiveGameObjects = new ArrayList<>();
    private final List<Subscriber> subscribers = new ArrayList<>();

    private GameObject player;
    private boolean isGameOver = false;

    @Override
    public void activateAll(float deltaTime) {
        removeInactiveGameObjects();
        activeGameObjects.forEach(gameObject -> gameObject.activate(deltaTime));
    }

    @Override
    public void addGameObjects(List<? extends GameObject> gameObjects) {
        activeGameObjects.addAll(gameObjects);
        subscribers.forEach(subscriber -> subscriber.registerGameObjects(gameObjects));
    }

    @Override
    public void addPlayer(GameObject player) {
        this.player = player;
        activeGameObjects.add(player);
        subscribers.forEach(subscriber -> subscriber.registerGameObjects(List.of(player)));
    }

    @Override
    public void deleteGameObjects(List<? extends GameObject> gameObjects) {
        inactiveGameObjects.addAll(gameObjects);
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
    public boolean isGameOver() {
        return isGameOver;
    }

    private void removeInactiveGameObjects() {
        if (!inactiveGameObjects.isEmpty()) {
            activeGameObjects.removeAll(inactiveGameObjects);
        }
        if (!activeGameObjects.contains(player)) {
            gameOver();
        } else {
            subscribers.forEach(subscriber -> subscriber.unregisterGameObjects(inactiveGameObjects));
        }
        inactiveGameObjects.clear();
    }

    private void gameOver() {
        subscribers.forEach(subscriber -> subscriber.unregisterGameObjects(activeGameObjects));
        activeGameObjects.clear();
        isGameOver = true;
    }
}
