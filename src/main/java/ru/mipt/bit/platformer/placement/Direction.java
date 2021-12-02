package ru.mipt.bit.platformer.placement;

import java.util.HashMap;
import java.util.Map;

/**
 * Entity
 */
public enum Direction {
    UP(new Point(0, 1), Orientation.UP),
    DOWN( new Point(0, -1), Orientation.DOWN),
    LEFT(new Point(-1, 0), Orientation.LEFT),
    RIGHT(new Point(1, 0), Orientation.RIGHT);

    Direction(Point shift, Orientation orientation) {
        this.shift = shift;
        this.orientation = orientation;
    }

    private static Map<Orientation, Direction> orientationToDirection = getOrientationToDirection();

    private final Point shift;
    private final Orientation orientation;


    public Point getShift() {
        return shift;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public static Direction getDirectionByOrientation(Orientation orientation) {
        return orientationToDirection.get(orientation);
    }

    private static Map<Orientation, Direction> getOrientationToDirection() {
        Map<Orientation, Direction> result = new HashMap<>();
        for (Direction direction: Direction.values()) {
            result.put(direction.orientation, direction);
        }
        return result;
    }
}
