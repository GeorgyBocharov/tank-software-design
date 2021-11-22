package ru.mipt.bit.platformer.objects;


public interface GameObject extends Colliding {
    default void activate(float deltaTime) {}
}
