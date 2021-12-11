package ru.mipt.bit.platformer.objects.states.impl;

import ru.mipt.bit.platformer.objects.logic.LogicTank;

public class SeverelyDamagedTankState extends AbstractTankState {

    public SeverelyDamagedTankState(LogicTank tank) {
        super(tank);
    }

    @Override
    public void shoot() {

    }

    @Override
    public void recalculateProgress(float deltaTime, float speed) {
        tank.recalculateProgress(deltaTime, speed / 3);
    }
}
