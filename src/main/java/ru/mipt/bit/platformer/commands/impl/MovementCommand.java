package ru.mipt.bit.platformer.commands.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.placement.Direction;
import ru.mipt.bit.platformer.objects.Movable;

/**
 * Entity
 */
@RequiredArgsConstructor
public class MovementCommand implements Command {

    private final Movable movable;
    private final Direction direction;

    @Override
    public void execute() {
        movable.triggerMovement(direction);
    }
}
