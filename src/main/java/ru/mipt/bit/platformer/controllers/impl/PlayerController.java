package ru.mipt.bit.platformer.controllers.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.CommandScheduler;
import ru.mipt.bit.platformer.controllers.Controller;
import ru.mipt.bit.platformer.keyboard.KeyboardChecker;
import ru.mipt.bit.platformer.commands.mappers.CommandMapper;

import java.util.HashSet;
import java.util.Set;

/**
 * Application level
 */
@RequiredArgsConstructor
public class PlayerController implements Controller {

    private final CommandMapper commandMapper;
    private final KeyboardChecker checker;
    private final CommandScheduler commandScheduler;


    @Override
    public void generate(float deltaTime) {
        getCommand().forEach(commandScheduler::scheduleCommand);
    }

    private Set<Command> getCommand() {
        Set<Command> commands = new HashSet<>();
        for (Set<Integer> keySet : commandMapper.getMappedKeySets()) {
            if (checker.isKeyPressed(keySet)) {
                commands.add(commandMapper.getCommandByKeySet(keySet));
            }
        }
        return commands;
    }

}
