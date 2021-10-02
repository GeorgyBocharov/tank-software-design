package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.isEqual;

public class AgileGraphicObject extends GraphicObject {

    private static final float PROGRESS_MAX = 1F;
    private static final float PROGRESS_MIN = 0F;

    private final GridPoint2 destinationCoordinates;
    private float movementProgress = PROGRESS_MAX;


    public AgileGraphicObject(Texture texture, GridPoint2 coordinates, float rotation) {
        super(texture, coordinates, rotation);
        this.destinationCoordinates = new GridPoint2(coordinates);
    }

    public boolean isMovementFinished() {
        return isEqual(movementProgress, PROGRESS_MAX);
    }

    public void move(float deltaTime, float speed) {
        movementProgress = clamp(movementProgress + deltaTime / speed, PROGRESS_MIN, PROGRESS_MAX);
        if (isMovementFinished()) {
            coordinates.set(destinationCoordinates);
        }
    }

    public void triggerMovement(Direction direction) {
        movementProgress = PROGRESS_MIN;
        destinationCoordinates.x += direction.getShift().x;
        destinationCoordinates.y += direction.getShift().y;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

}
