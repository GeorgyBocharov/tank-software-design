package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import lombok.EqualsAndHashCode;

import lombok.Getter;
import ru.mipt.bit.platformer.geometry.Direction;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.movement.service.LibGdxMovementService;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.convertPointToGridPoint;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.sumPoints;

@EqualsAndHashCode
@Getter
public class Tank implements Movable {

    protected static final float PROGRESS_MAX = 1F;
    protected static final float PROGRESS_MIN = 0F;

    private float movementProgress = PROGRESS_MAX;
    private final float speed;
    private final LibGdxMovementService movementService;

    private LibGdxGraphicObject libGdxGraphicObject;
    private final CollidingObject collidingObject;

    private final GridPoint2 destinationCoordinates;

    public Tank(float speed, LibGdxMovementService movementService, LibGdxGraphicObject libGdxGraphicObject, CollidingObject collidingObject) {
        this.speed = speed;
        this.movementService = movementService;
        this.libGdxGraphicObject = libGdxGraphicObject;
        this.collidingObject = collidingObject;
        destinationCoordinates = convertPointToGridPoint(collidingObject.getCoordinates());
    }

    @Override
    public void activate(float deltaTime) {
        interpolateMovement();
        movementProgress = clamp(movementProgress + deltaTime / speed, PROGRESS_MIN, PROGRESS_MAX);
        Point logicObjectCoordinates = collidingObject.getCoordinates();
        if (isMovementFinished() && !logicObjectCoordinates.isEqualToCoordinates(destinationCoordinates.x, destinationCoordinates.y)) {
            collidingObject.setCoordinates(new Point(destinationCoordinates.x, destinationCoordinates.y));
        }
    }

    @Override
    public void triggerMovement(Direction direction, boolean isCollisionPossible) {
        if (!isMovementFinished()) {
            return;
        }

        collidingObject.setOrientation(direction.getOrientation());
        if (!isCollisionPossible) {
            movementProgress = PROGRESS_MIN;
            destinationCoordinates.x += direction.getShift().x;
            destinationCoordinates.y += direction.getShift().y;
        }
    }

    @Override
    public Point getCoordinatesAfterShift(Direction direction) {
        Point coordinates = collidingObject.getCoordinates();
        GridPoint2 positionAfterShift = sumPoints(convertPointToGridPoint(coordinates), direction.getShift());
        return new Point(positionAfterShift.x, positionAfterShift.y);
    }

    @Override
    public boolean isCollisionPossible(Point othersCoordinates) {
        return collidingObject.isCollisionPossible(othersCoordinates) ||
                othersCoordinates.isEqualToCoordinates(destinationCoordinates.x, destinationCoordinates.y);
    }

    private boolean isMovementFinished() {
        return isEqual(movementProgress, PROGRESS_MAX);
    }

    private void interpolateMovement() {
        libGdxGraphicObject = movementService
                .interpolateGameObjectCoordinates(libGdxGraphicObject, movementProgress, destinationCoordinates);
    }
}
