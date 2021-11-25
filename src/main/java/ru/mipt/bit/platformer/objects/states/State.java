package ru.mipt.bit.platformer.objects.states;


public interface State {
    void shoot();
    void recalculateProgress(float deltaTime, float speed);
    void registerHarmfulCollision(float damage);
}
