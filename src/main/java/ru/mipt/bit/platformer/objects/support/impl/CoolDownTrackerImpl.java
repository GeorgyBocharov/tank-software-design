package ru.mipt.bit.platformer.objects.support.impl;

import ru.mipt.bit.platformer.objects.support.CoolDownTracker;

public class CoolDownTrackerImpl implements CoolDownTracker {

    private float coolDownTracker = 0;

    @Override
    public boolean isReady() {
        return coolDownTracker == 0;
    }

    @Override
    public void updateTracker(float deltaTime) {
        if (coolDownTracker == 0) {
            return;
        }
        coolDownTracker = coolDownTracker > deltaTime ? coolDownTracker - deltaTime : 0;
    }

    @Override
    public void resetTracker(float coolDownTime) {
        coolDownTracker = coolDownTime;
    }
}
