package ru.mipt.bit.platformer.service.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.entities.Direction;
import ru.mipt.bit.platformer.service.ActionMapper;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class ActionMapperImpl implements ActionMapper {

    private final Map<Integer, Direction> keyToDirectionMap;

    @Override
    public Set<Integer> getMappedKeys() {
        return keyToDirectionMap.keySet();
    }

    @Override
    public void addDirectionMapping(Integer key, Direction direction) {
        keyToDirectionMap.put(key, direction);
    }

    @Override
    public Direction getDirectionByKey(Integer key) {
        return keyToDirectionMap.get(key);
    }
}
