package ru.mipt.bit.platformer.commands.generators;

import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.objects.logic.LogicTank;

public interface CommandGenerator {
    Command generate(LogicTank actor, float deltaTime);
}
