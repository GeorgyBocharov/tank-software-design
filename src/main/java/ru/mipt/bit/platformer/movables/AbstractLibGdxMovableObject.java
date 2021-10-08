package ru.mipt.bit.platformer.movables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import lombok.Getter;
import ru.mipt.bit.platformer.entities.Direction;
import ru.mipt.bit.platformer.entities.LibGdxGraphicObject;
import ru.mipt.bit.platformer.entities.LogicObject;
import ru.mipt.bit.platformer.movement.LibGdxMovementService;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.sumPoints;

@Getter
public abstract class AbstractLibGdxMovableObject implements Movable {

    protected static final float PROGRESS_MAX = 1F;
    protected static final float PROGRESS_MIN = 0F;

    protected float movementProgress = PROGRESS_MAX;
    protected LibGdxGraphicObject graphicObject;
    protected GridPoint2 destinationCoordinates;
    private final LibGdxMovementService movementService;

    public AbstractLibGdxMovableObject(TiledMapTileLayer tileLayer, LibGdxMovementService movementService, Texture texture,
                                       GridPoint2 startCoordinates, float rotation) {
        this.movementService = movementService;
        LogicObject logicObject = new LogicObject(rotation, startCoordinates);
        graphicObject = new LibGdxGraphicObject(tileLayer, texture, logicObject);
        destinationCoordinates = new GridPoint2(startCoordinates);
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
    public GridPoint2 getCoordinatesAfterShift(Direction direction) {
        return sumPoints(graphicObject.getLogicObject().getCoordinates(), direction.getShift());
    }

    protected boolean isMovementFinished() {
        return isEqual(movementProgress, PROGRESS_MAX);
    }

    protected void interpolateMovement() {
        graphicObject = movementService
                .interpolateGameObjectCoordinates(graphicObject, movementProgress, destinationCoordinates);
    }

}
