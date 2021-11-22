package ru.mipt.bit.platformer.objects.graphic.object.placement.wrapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LogicObjectNumberWrapper {
    private final int tankNumber;
    private final int treeNumber;
    private final int expectedTankNumber;
    private final int expectedTreeNumber;
}
