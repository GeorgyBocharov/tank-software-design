package ru.mipt.bit.platformer.commands.generators.impl;

import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.generators.CommandGenerator;
import ru.mipt.bit.platformer.commands.impl.MovementCommand;
import ru.mipt.bit.platformer.commands.impl.ShootCommand;
import ru.mipt.bit.platformer.placement.Direction;
import ru.mipt.bit.platformer.objects.logic.LogicTank;

import java.util.Random;

/**
 * Adapter interface implementation
 */
public class RandomCommandsGenerator implements CommandGenerator {

    private static final int AVAILABLE_COMMANDS_NUMBER = 5;

    private final Random random = new Random();

    @Override
    public Command generate(LogicTank actor, float deltaTime) {
        int nextValue = random.nextInt(AVAILABLE_COMMANDS_NUMBER);
        if (nextValue == AVAILABLE_COMMANDS_NUMBER - 1) {
            return new ShootCommand(actor);
        } else {
            return new MovementCommand(actor, Direction.values()[nextValue]);
        }
    }

}
