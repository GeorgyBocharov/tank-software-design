package ru.mipt.bit.platformer.ai.converter.impl;

import org.awesome.ai.state.movable.Orientation;
import ru.mipt.bit.platformer.ai.converter.OrientationConverter;

/**
 * Adapter level
 */
public class OrientationConverterImpl implements OrientationConverter {

    @Override
    public Orientation convertToOrientation(ru.mipt.bit.platformer.placement.Orientation logicObjectOrientation) {
        switch (logicObjectOrientation) {
            case UP:
                return Orientation.N;
            case DOWN:
                return Orientation.S;
            case LEFT:
                return Orientation.W;
            case RIGHT:
                return Orientation.E;
            default:
                throw new RuntimeException("Found untracked value of logic object orientation: " + logicObjectOrientation);
        }
    }
}
