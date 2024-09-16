package ru.mipt.bit.platformer.objects.states.impl;

import ru.mipt.bit.platformer.objects.logic.LogicTank;

/**
 * Application class
 */
public class ModeratelyDamagedTankState extends AbstractTankState {

    public ModeratelyDamagedTankState(LogicTank tank) {
        super(tank);
    }

    @Override
    public void recalculateProgress(float deltaTime, float speed) {
        tank.recalculateProgress(deltaTime, speed / 2);
    }
}
