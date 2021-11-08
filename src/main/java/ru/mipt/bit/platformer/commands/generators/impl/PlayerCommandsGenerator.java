package ru.mipt.bit.platformer.commands.generators.impl;


import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.CommandScheduler;
import ru.mipt.bit.platformer.commands.generators.CommandsGenerator;
import ru.mipt.bit.platformer.keyboard.KeyboardChecker;
import ru.mipt.bit.platformer.commands.mappers.CommandMapper;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class PlayerCommandsGenerator implements CommandsGenerator {

    private final CommandMapper commandMapper;
    private final KeyboardChecker checker;
    private final CommandScheduler commandScheduler;


    @Override
    public void generate(float deltaTime) {
        getCommand().ifPresent(commandScheduler::scheduleCommand);
    }

    private Optional<Command> getCommand() {
        for (Set<Integer> keySet : commandMapper.getMappedKeySets()) {
            if (checker.isKeyPressed(keySet)) {
                return Optional.of(commandMapper.getCommandByKeySet(keySet));
            }
        }
        return Optional.empty();
    }

}
