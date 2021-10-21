package ru.mipt.bit.platformer.controllers;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.CommandScheduler;
import ru.mipt.bit.platformer.commands.impl.MovementCommand;
import ru.mipt.bit.platformer.entities.Direction;
import ru.mipt.bit.platformer.movables.Movable;
import ru.mipt.bit.platformer.service.CollisionDetectionManager;

@RequiredArgsConstructor
public abstract class AbstractController implements TankController {

    private final CollisionDetectionManager collisionDetectionManager;
    private final CommandScheduler commandScheduler;

    protected void scheduleMovementCommand(Movable movable, Direction direction) {
        Command command = new MovementCommand(collisionDetectionManager, movable, direction);
        if (!commandScheduler.scheduleCommand(command)) {
            System.out.println("Failed to schedule command");
        }
    }
}
