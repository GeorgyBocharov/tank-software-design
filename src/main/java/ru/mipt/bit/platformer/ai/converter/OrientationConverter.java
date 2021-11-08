package ru.mipt.bit.platformer.ai.converter;

import org.awesome.ai.state.movable.Orientation;

public interface OrientationConverter {
    Orientation convertToOrientation(ru.mipt.bit.platformer.objects.Orientation logicObjectOrientation);
}
