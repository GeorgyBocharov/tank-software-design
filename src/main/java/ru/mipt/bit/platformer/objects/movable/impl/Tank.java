package ru.mipt.bit.platformer.objects.movable.impl;

import lombok.EqualsAndHashCode;

import ru.mipt.bit.platformer.objects.LibGdxGraphicObject;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.movement.service.LibGdxMovementService;

import static com.badlogic.gdx.math.MathUtils.clamp;

@EqualsAndHashCode(callSuper = true)
public class Tank extends AbstractLibGdxMovableObject {

    private final float speed;

    public Tank(float speed, LibGdxMovementService movementService, LibGdxGraphicObject graphicObject) {
        super(movementService, graphicObject);
        this.speed = speed;
    }

    @Override
    public void activate(float deltaTime) {
        interpolateMovement();
        movementProgress = clamp(movementProgress + deltaTime / speed, PROGRESS_MIN, PROGRESS_MAX);
        Point logicObjectCoordinates = graphicObject.getLogicObject().getCoordinates();
        if (isMovementFinished() && !logicObjectCoordinates.isEqualToCoordinates(destinationCoordinates.x, destinationCoordinates.y)) {
            graphicObject
                    .getLogicObject()
                    .setCoordinates(new Point(destinationCoordinates.x, destinationCoordinates.y));
        }
    }
}
