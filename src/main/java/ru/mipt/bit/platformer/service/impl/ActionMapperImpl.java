package ru.mipt.bit.platformer.service.impl;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import ru.mipt.bit.platformer.entities.Direction;
import ru.mipt.bit.platformer.service.ActionMapper;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ActionMapperImpl implements ActionMapper {

    private Map<Direction, Set<Integer>> directionToKeyMap;

    public ActionMapperImpl() {
        directionToKeyMap = Map.of(
                Direction.UP, Set.of(Keys.W, Keys.UP),
                Direction.LEFT, Set.of(Keys.A, Keys.LEFT),
                Direction.DOWN, Set.of(Keys.S, Keys.DOWN),
                Direction.RIGHT, Set.of(Keys.D, Keys.RIGHT)
        );
    }

    @Override
    public void addDirectionMapping(Direction direction, Integer key) {
        directionToKeyMap.putIfAbsent(direction, new HashSet<>());
        directionToKeyMap.get(direction).add(key);
    }


    @Override
    public boolean isDirectionKeyPressed(Direction direction, Input input) {
        for (Integer key: directionToKeyMap.get(direction)) {
            if (input.isKeyPressed(key)) {
                return true;
            }
        }
        return false;
    }



}
