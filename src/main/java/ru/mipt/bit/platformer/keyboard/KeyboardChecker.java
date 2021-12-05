package ru.mipt.bit.platformer.keyboard;

import java.util.Set;

/**
 * Port between application and adapter levels
 */
public interface KeyboardChecker {
    boolean isKeyPressed(Set<Integer> keySet);
}
