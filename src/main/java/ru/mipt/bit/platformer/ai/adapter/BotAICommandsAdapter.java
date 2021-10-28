package ru.mipt.bit.platformer.ai.adapter;

import ru.mipt.bit.platformer.commands.Command;

import java.util.List;

public interface BotAICommandsAdapter {
    List<Command> generateBotCommands();
}
