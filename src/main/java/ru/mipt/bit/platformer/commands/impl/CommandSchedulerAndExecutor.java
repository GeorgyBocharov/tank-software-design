package ru.mipt.bit.platformer.commands.impl;

import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.CommandExecutor;
import ru.mipt.bit.platformer.commands.CommandScheduler;

import java.util.ArrayList;
import java.util.List;

public class CommandSchedulerAndExecutor implements CommandExecutor, CommandScheduler {

    private static final int COMMAND_STACK_SIZE = 20;

    private final List<Command> commandList = new ArrayList<>();

    @Override
    public boolean scheduleCommand(Command command) {
        if (commandList.size() < COMMAND_STACK_SIZE) {
            commandList.add(command);
            return true;
        }
        return false;
    }

    @Override
    public void executeAll() {
        commandList.forEach(Command::execute);
        commandList.clear();
    }
}
