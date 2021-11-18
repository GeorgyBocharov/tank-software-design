package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import lombok.EqualsAndHashCode;

import lombok.Getter;
import ru.mipt.bit.platformer.geometry.Direction;
import ru.mipt.bit.platformer.geometry.Point;

import static ru.mipt.bit.platformer.utils.GdxGameUtils.convertPointToGridPoint;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.sumPoints;

@EqualsAndHashCode
@Getter
public class Tank implements Movable {

    private static final float PROGRESS_MAX = 1F;
    private static final float PROGRESS_MIN = 0F;
    private static final float FLOAT_ROUNDING_ERROR = 0.000001f;

    private float movementProgress = PROGRESS_MAX;
    private final float speed;

    private final CollidingObject collidingObject;
    private final ProgressService progressService;

    private final Point destinationCoordinates;

    public Tank(float speed, CollidingObject collidingObject, ProgressService progressService) {
        this.speed = speed;
        this.collidingObject = collidingObject;
        this.destinationCoordinates = collidingObject.getCoordinates();
        this.progressService = progressService;
    }

    public void shoot() {

    }

    @Override
    public void activate(float deltaTime) {
        movementProgress = progressService.calculateProgress(movementProgress + deltaTime / speed, PROGRESS_MIN, PROGRESS_MAX);
        Point logicObjectCoordinates = collidingObject.getCoordinates();
        if (isMovementFinished() &&
                !logicObjectCoordinates.isEqualToCoordinates(destinationCoordinates.getX(), destinationCoordinates.getY())) {
            collidingObject.setCoordinates(destinationCoordinates);
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
            destinationCoordinates.add(direction.getShift().x, direction.getShift().y);
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
                othersCoordinates.equals(destinationCoordinates);
    }

    private boolean isMovementFinished() {
        return Math.abs(movementProgress - PROGRESS_MAX) < FLOAT_ROUNDING_ERROR;
    }


}
