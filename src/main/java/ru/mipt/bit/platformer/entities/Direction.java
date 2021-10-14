package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(new GridPoint2(0, 1), 90f),
    DOWN( new GridPoint2(0, -1), -90f),
    LEFT(new GridPoint2(-1, 0), -180f),
    RIGHT(new GridPoint2(1, 0), 0f);

    Direction(GridPoint2 shift, float rotation) {
        this.shift = shift;
        this.rotation = rotation;
    }

    private final GridPoint2 shift;
    private final float rotation;

    public GridPoint2 getShift() {
        return shift;
    }

    public float getRotation() {
        return rotation;
    }
}
