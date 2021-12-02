package ru.mipt.bit.platformer.placement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Entity
 */
@Getter
@ToString
@EqualsAndHashCode
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point other) {
        this.x = other.getX();
        this.y = other.getY();
    }

    public void add(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void add(Point point) {
        x += point.getX();
        y += point.getY();
    }

    public static Point sum(Point first, Point second) {
        return new Point(first.x + second.x, first.y + second.y);
    }
}
