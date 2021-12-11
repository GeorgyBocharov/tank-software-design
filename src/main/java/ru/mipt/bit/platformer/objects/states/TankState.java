package ru.mipt.bit.platformer.objects.states;


public interface TankState {
    void shoot();
    void recalculateProgress(float deltaTime, float speed);
    void hurtTank(float damage);
    void healTank(float extraHP);
}
