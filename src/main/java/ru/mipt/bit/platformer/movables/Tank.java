package ru.mipt.bit.platformer.movables;

import lombok.EqualsAndHashCode;

import ru.mipt.bit.platformer.entities.LibGdxGraphicObject;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.movement.LibGdxMovementService;

import static com.badlogic.gdx.math.MathUtils.clamp;

@EqualsAndHashCode(callSuper = true)
public class Tank extends AbstractLibGdxMovableObject {

    private final float speed;

    public Tank(float speed, LibGdxMovementService movementService, LibGdxGraphicObject graphicObject) {
        super(movementService, graphicObject);
        this.speed = speed;
    }

    @Override
    public void move(float deltaTime) {
        interpolateMovement();
        movementProgress = clamp(movementProgress + deltaTime / speed, PROGRESS_MIN, PROGRESS_MAX);
        Point logicObjectCoordinates = graphicObject.getLogicObject().getCoordinates();
        if (isMovementFinished() && !logicObjectCoordinates.isEqualToCoordinates(destinationCoordinates.x, destinationCoordinates.y)) {
            System.out.println("Moving tank");
            graphicObject
                    .getLogicObject()
                    .setCoordinates(new Point(destinationCoordinates.x, destinationCoordinates.y));
        }
    }
}
