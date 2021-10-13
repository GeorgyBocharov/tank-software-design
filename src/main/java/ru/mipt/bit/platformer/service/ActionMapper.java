package ru.mipt.bit.platformer.service;

import ru.mipt.bit.platformer.entities.Direction;

import java.util.Set;

public interface ActionMapper {
    void addDirectionMapping(Integer key, Direction direction);
    Set<Integer> getMappedKeys();
    Direction getDirectionByKey(Integer key);
}
