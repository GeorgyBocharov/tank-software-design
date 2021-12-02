package ru.mipt.bit.platformer.controllers.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.commands.CommandScheduler;
import ru.mipt.bit.platformer.commands.generators.CommandGenerator;
import ru.mipt.bit.platformer.controllers.Controller;
import ru.mipt.bit.platformer.objects.logic.LogicTank;

import java.util.List;

/**
 * Adapter interface implementation
 */
@RequiredArgsConstructor
public class BotController implements Controller {

    private final List<LogicTank> bots;
    private final CommandScheduler commandScheduler;
    private final CommandGenerator commandGenerator;

    @Override
    public void generate(float deltaTime) {
        for (LogicTank bot : bots) {
            commandScheduler.scheduleCommand(commandGenerator.generate(bot, deltaTime));
        }
    }

}
