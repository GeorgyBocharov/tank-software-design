package ru.mipt.bit.platformer.keyboard.impl;

import com.badlogic.gdx.Input;
import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.keyboard.KeyboardChecker;

import java.util.Set;

/**
 * Adapter level
 */
@RequiredArgsConstructor
public class LibGdxKeyboardChecker implements KeyboardChecker {

    private final Input gdxInput;

    @Override
    public boolean isKeyPressed(Set<Integer> keySet) {
        for (Integer key : keySet) {
            if (!gdxInput.isKeyPressed(key)) {
                return false;
            }
        }
        return true;
    }
}
