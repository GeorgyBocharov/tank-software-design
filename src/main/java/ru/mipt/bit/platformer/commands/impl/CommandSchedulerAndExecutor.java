package ru.mipt.bit.platformer.commands.impl;

import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.CommandExecutor;
import ru.mipt.bit.platformer.commands.CommandScheduler;

import java.util.ArrayList;
import java.util.List;

public class CommandSchedulerAndExecutor implements CommandExecutor, CommandScheduler {

    private final List<Command> commandList = new ArrayList<>();

    @Override
    public void scheduleCommand(Command command) {
        commandList.add(command);
    }

    @Override
    public void executeAll() {
        commandList.forEach(Command::execute);
        commandList.clear();
    }
}
