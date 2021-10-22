package ru.mipt.bit.platformer.keyboard;

import java.util.Set;

public interface KeyboardChecker {
    boolean isKeyPressed(Set<Integer> keySet);
}
