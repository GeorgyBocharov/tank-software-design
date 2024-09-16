package ru.mipt.bit.platformer.commands.generators;

import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.objects.logic.LogicTank;

/**
 * Adapter interface
 */
public interface CommandGenerator {
    Command generate(LogicTank actor, float deltaTime);
}
