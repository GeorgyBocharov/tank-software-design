package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.publisher.Publisher;

import java.util.List;

public interface LogicLevel extends Publisher {
    void activateAll(float deltaTime);
    void addPlayer(GameObject player);
    void addGameObjects(List<? extends GameObject> gameObjects);
    void deleteGameObjects(List<? extends GameObject> gameObjects);
    boolean isGameOver();
}
