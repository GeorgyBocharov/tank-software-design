package ru.mipt.bit.platformer.commands.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.objects.logic.LogicTank;

/**
 * Entity
 */
@RequiredArgsConstructor
public class ShootCommand implements Command {

    private final LogicTank tank;

    @Override
    public void execute() {
        tank.shoot();
    }
}
