package ru.mipt.bit.platformer.commands.generators.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.ai.adapter.BotAICommandsAdapter;
import ru.mipt.bit.platformer.commands.CommandScheduler;
import ru.mipt.bit.platformer.commands.generators.CommandsGenerator;

@RequiredArgsConstructor
public class BotAICommandGenerator implements CommandsGenerator {

    private final CommandScheduler commandScheduler;
    private final BotAICommandsAdapter aiCommandsAdapter;

    @Override
    public void generate(float deltaTime) {
        aiCommandsAdapter.generateBotCommands().forEach(commandScheduler::scheduleCommand);
    }

}
