package ru.mipt.bit.platformer.service;

import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.entities.Direction;

public interface ActionMapper {
    void addDirectionMapping(Direction direction, Integer key);

    boolean isDirectionKeyPressed(Direction direction, Input input);
}
