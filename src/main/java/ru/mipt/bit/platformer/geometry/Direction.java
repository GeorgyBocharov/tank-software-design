package ru.mipt.bit.platformer.geometry;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.objects.Orientation;

public enum Direction {
    UP(new GridPoint2(0, 1), Orientation.UP),
    DOWN( new GridPoint2(0, -1), Orientation.DOWN),
    LEFT(new GridPoint2(-1, 0), Orientation.LEFT),
    RIGHT(new GridPoint2(1, 0), Orientation.RIGHT);

    Direction(GridPoint2 shift, Orientation orientation) {
        this.shift = shift;
        this.orientation = orientation;
    }

    private final GridPoint2 shift;
    private final Orientation orientation;

    public GridPoint2 getShift() {
        return shift;
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
