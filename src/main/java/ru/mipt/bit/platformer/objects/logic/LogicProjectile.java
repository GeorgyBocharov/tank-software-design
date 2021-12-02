package ru.mipt.bit.platformer.objects.logic;

import ru.mipt.bit.platformer.collision.CollisionDetector;
import ru.mipt.bit.platformer.objects.AgileObject;
import ru.mipt.bit.platformer.placement.Direction;
import ru.mipt.bit.platformer.placement.Point;
import ru.mipt.bit.platformer.level.LogicLevel;
import ru.mipt.bit.platformer.objects.Colliding;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.placement.Position;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.isEqual;

/**
 * Entity class
 * Describes projectile
 */
public class LogicProjectile implements GameObject, AgileObject {

    private static final float PROGRESS_MAX = 1F;
    private static final float PROGRESS_MIN = 0F;

    private final float damage;
    private final float speed;

    private float movementProgress = PROGRESS_MIN;
    private final Position position;
    private final CollisionDetector collisionDetector;
    private final LogicLevel logicLevel;
    private final Direction direction;

    private final Point destinationCoordinates;


    public LogicProjectile(float speed, float damage,
                           Direction direction, Position position,
                           LogicLevel logicLevel, CollisionDetector collisionDetector) {

        this.speed = speed;
        this.damage = damage;
        this.direction = direction;
        this.position = position;
        this.collisionDetector = collisionDetector;
        this.logicLevel = logicLevel;

        destinationCoordinates = Point.sum(position.getCoordinates(), direction.getShift());
        logicLevel.addGameObjects(List.of(this));
    }


    @Override
    public void activate(float deltaTime) {
        checkCollisions();
        movementProgress = clamp(movementProgress + deltaTime * speed, PROGRESS_MIN, PROGRESS_MAX);

        Point logicObjectCoordinates = position.getCoordinates();
        if (isMovementFinished() && !logicObjectCoordinates.equals(destinationCoordinates)) {
            position.setCoordinates(new Point(destinationCoordinates));
            destinationCoordinates.add(direction.getShift());
            movementProgress = PROGRESS_MIN;
        }
    }

    @Override
    public boolean collides(Colliding colliding) {
        boolean collisionOccurred = GameObject.super.collides(colliding);
        if (collisionOccurred) {
            colliding.registerHarmfulCollision(damage);
        }
        return collisionOccurred;
    }

    @Override
    public List<Point> getCurrentPoints() {
        return List.of(position.getCoordinates());
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Point getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    private boolean isMovementFinished() {
        return isEqual(movementProgress, PROGRESS_MAX);
    }

    private void checkCollisions() {
        if (collisionDetector.isCollisionPossible(this)) {
            logicLevel.deleteGameObjects(List.of(this));
        }
    }

}
