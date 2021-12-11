package ru.mipt.bit.platformer.commands.mappers;

import ru.mipt.bit.platformer.commands.Command;

import java.util.Set;

public interface CommandMapper {
    void addCommandMapping(Set<Integer> keySet, Command command);
    Set<Set<Integer>> getMappedKeySets();
    Command getCommandByKeySet(Set<Integer> keySet);
}
