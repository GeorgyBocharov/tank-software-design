package ru.mipt.bit.platformer.objects.support;

public interface CoolDownTracker {

    boolean isReady();

    void updateTracker(float deltaTime);

    void resetTracker(float coolDownTime);
}
