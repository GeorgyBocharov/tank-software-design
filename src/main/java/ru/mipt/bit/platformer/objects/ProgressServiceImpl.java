package ru.mipt.bit.platformer.objects;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class ProgressServiceImpl implements ProgressService {

    @Override
    public float calculateProgress(float value, float min, float max) {
        return clamp(value, min, max);
    }
}
