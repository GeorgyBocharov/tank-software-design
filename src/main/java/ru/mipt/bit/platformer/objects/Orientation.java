package ru.mipt.bit.platformer.objects;

import lombok.Getter;

@Getter
public enum Orientation {
    UP(90f),
    DOWN(-90f),
    LEFT(-180f),
    RIGHT(0f);

    private final float value;

    Orientation(float value) {
        this.value = value;
    }

}
