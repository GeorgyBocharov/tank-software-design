package ru.mipt.bit.platformer.commands.mappers;

import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.commands.mappers.CommandMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Application level
 */
public class CommandMapperImpl implements CommandMapper {

    //several keys in sets are treated as combination
    private final Map<Set<Integer>, Command> keyToDirectionMap = new HashMap<>();

    @Override
    public Set<Set<Integer>> getMappedKeySets() {
        return keyToDirectionMap.keySet();
    }

    @Override
    public void addCommandMapping(Set<Integer> keySet, Command direction) {
        keyToDirectionMap.put(keySet, direction);
    }

    @Override
    public Command getCommandByKeySet(Set<Integer> keySet) {
        return keyToDirectionMap.get(keySet);
    }
}
