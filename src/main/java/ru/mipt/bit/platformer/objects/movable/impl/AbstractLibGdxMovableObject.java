package ru.mipt.bit.platformer.objects.movable.impl;

import com.badlogic.gdx.math.GridPoint2;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import ru.mipt.bit.platformer.geometry.Direction;
import ru.mipt.bit.platformer.objects.LibGdxGraphicObject;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.movement.service.LibGdxMovementService;
import ru.mipt.bit.platformer.objects.Orientation;
import ru.mipt.bit.platformer.objects.movable.Movable;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.convertPointToGridPoint;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.sumPoints;

@Getter
@EqualsAndHashCode
public abstract class AbstractLibGdxMovableObject implements Movable {

    protected static final float PROGRESS_MAX = 1F;
    protected static final float PROGRESS_MIN = 0F;

    protected float movementProgress = PROGRESS_MAX;
    protected LibGdxGraphicObject graphicObject;
    protected GridPoint2 destinationCoordinates;
    private final LibGdxMovementService movementService;

    public AbstractLibGdxMovableObject(LibGdxMovementService movementService, LibGdxGraphicObject graphicObject) {
        this.movementService = movementService;
        this.graphicObject = graphicObject;
        destinationCoordinates = convertPointToGridPoint(graphicObject.getLogicObject().getCoordinates());
    }

    @Override
    public void triggerMovement(Direction direction) {
        if (isMovementFinished()) {
            movementProgress = PROGRESS_MIN;
            destinationCoordinates.x += direction.getShift().x;
            destinationCoordinates.y += direction.getShift().y;
        }
    }

    @Override
    public void setRotation(Orientation orientation) {
        if (isMovementFinished()) {
            graphicObject.getLogicObject().setOrientation(orientation);
        }
    }

    @Override
    public Point getCoordinatesAfterShift(Direction direction) {
        Point coordinates = graphicObject.getLogicObject().getCoordinates();
        GridPoint2 positionAfterShift = sumPoints(convertPointToGridPoint(coordinates), direction.getShift());
        return new Point(positionAfterShift.x, positionAfterShift.y);
    }

    protected boolean isMovementFinished() {
        return isEqual(movementProgress, PROGRESS_MAX);
    }

    protected void interpolateMovement() {
        graphicObject = movementService
                .interpolateGameObjectCoordinates(graphicObject, movementProgress, destinationCoordinates);
    }

    @Override
    public boolean isCollisionPossible(Point othersCoordinates) {
        return graphicObject.isCollisionPossible(othersCoordinates) ||
                othersCoordinates.isEqualToCoordinates(destinationCoordinates.x, destinationCoordinates.y);
    }
}
