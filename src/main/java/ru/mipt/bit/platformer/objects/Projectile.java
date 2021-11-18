package ru.mipt.bit.platformer.objects;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.collision.Colliding;
import ru.mipt.bit.platformer.geometry.Direction;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.graphic.impl.LibGdxGraphicObject;
import ru.mipt.bit.platformer.movement.service.LibGdxMovementService;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.utils.GdxGameUtils.convertPointToGridPoint;

public class Projectile implements Colliding, Activated {


    protected static final float PROGRESS_MAX = 1F;
    protected static final float PROGRESS_MIN = 0F;

    private final float damage;
    private final float speed;

    private boolean hitSpotted = false;

    private float movementProgress = PROGRESS_MAX;
    private final LibGdxMovementService movementService;

    private LibGdxGraphicObject libGdxGraphicObject;
    private final CollidingObject collidingObject;

    private final GridPoint2 destinationCoordinates;


    public Projectile(float speed, float damage, Direction direction,
                      LibGdxMovementService movementService,
                      LibGdxGraphicObject libGdxGraphicObject, CollidingObject collidingObject) {

        setDestinationCoordinates(direction);
        this.speed = speed;
        this.damage = damage;
        this.movementService = movementService;
        this.libGdxGraphicObject = libGdxGraphicObject;
        this.collidingObject = collidingObject;
        destinationCoordinates = convertPointToGridPoint(collidingObject.getCoordinates());
    }


    @Override
    public void activate(float deltaTime) {
        if (hitSpotted) {
            return;
        }

        interpolateMovement();
        movementProgress = clamp(movementProgress + deltaTime / speed, PROGRESS_MIN, PROGRESS_MAX);
        Point logicObjectCoordinates = collidingObject.getCoordinates();
        if (isMovementFinished() && !logicObjectCoordinates.isEqualToCoordinates(destinationCoordinates.x, destinationCoordinates.y)) {
            collidingObject.setCoordinates(new Point(destinationCoordinates.x, destinationCoordinates.y));
        }
    }


    @Override
    public LibGdxGraphicObject getGraphicObject() {
        return libGdxGraphicObject;
    }

    @Override
    public boolean isCollisionPossible(Point othersCoordinates) {
        return collidingObject.isCollisionPossible(othersCoordinates) ||
                othersCoordinates.isEqualToCoordinates(destinationCoordinates.x, destinationCoordinates.y);
    }

    private boolean isMovementFinished() {
        return isEqual(movementProgress, PROGRESS_MAX);
    }

    private void setDestinationCoordinates(Direction direction) {
        destinationCoordinates.x = direction.getShift().x * 100;
        destinationCoordinates.y = direction.getShift().y * 100;
    }

    private void interpolateMovement() {
        libGdxGraphicObject = movementService
                .interpolateGameObjectCoordinates(libGdxGraphicObject, movementProgress, destinationCoordinates);
    }

}
