package ru.mipt.bit.platformer.geometry;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Point {
    private final int x;
    private final int y;

    public boolean isEqualToCoordinates(int x, int y) {
        return this.x == x && this.y == y;
    }
}
