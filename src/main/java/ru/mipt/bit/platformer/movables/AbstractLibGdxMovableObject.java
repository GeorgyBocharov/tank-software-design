package ru.mipt.bit.platformer.movables;

import com.badlogic.gdx.math.GridPoint2;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import ru.mipt.bit.platformer.entities.Direction;
import ru.mipt.bit.platformer.entities.LibGdxGraphicObject;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.movement.LibGdxMovementService;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.convertPointToGridPoint;
import static ru.mipt.bit.platformer.util.GdxGameUtils.sumPoints;

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
    public void setRotation(float rotation) {
        if (isMovementFinished()) {
            graphicObject.getLogicObject().setRotation(rotation);
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
        return graphicObject.isCollisionPossible(othersCoordinates);
    }
}
