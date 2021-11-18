package ru.mipt.bit.platformer.geometry;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Point {
    private int x;
    private int y;

    public boolean isEqualToCoordinates(int x, int y) {
        return this.x == x && this.y == y;
    }

    public void add(int dx, int dy) {
        x += dx;
        y += dy;
    }
}
