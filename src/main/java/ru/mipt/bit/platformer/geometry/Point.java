package ru.mipt.bit.platformer.geometry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Point {
    private final int x;
    private final int y;
}
