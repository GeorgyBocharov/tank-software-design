package ru.mipt.bit.platformer.objects.logic;


import ru.mipt.bit.platformer.collision.CollisionDetector;
import ru.mipt.bit.platformer.objects.AgileObject;
import ru.mipt.bit.platformer.placement.Direction;
import ru.mipt.bit.platformer.placement.Point;
import ru.mipt.bit.platformer.level.LogicLevel;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.objects.Movable;
import ru.mipt.bit.platformer.objects.support.CoolDownTracker;
import ru.mipt.bit.platformer.placement.Position;

import java.util.Collections;
import java.util.List;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class LogicTank implements Movable, GameObject, AgileObject {

    private static final float PROGRESS_MAX = 1F;
    private static final float PROGRESS_MIN = 0F;
    private static final float FLOAT_ROUNDING_ERROR = 0.000001f;

    private float movementProgress = PROGRESS_MAX;
    private final float speed;
    private final float projectileDamage;
    private final float projectileSpeed;
    private final float coolDown;
    private float hp;

    private final Position position;
    private final CollisionDetector collisionDetector;
    private final LogicLevel logicLevel;
    private final CoolDownTracker coolDownTracker;
    private final Point destinationCoordinates;


    public LogicTank(float speed, float hp, float coolDown,
                     float projectileDamage, float projectileSpeed,
                     CoolDownTracker coolDownTracker, Position position,
                     LogicLevel logicLevel, CollisionDetector collisionDetector) {

        this.speed = speed;
        this.hp = hp;
        this.coolDown = coolDown;
        this.projectileDamage = projectileDamage;
        this.projectileSpeed = projectileSpeed;

        this.coolDownTracker = coolDownTracker;
        this.position = position;
        this.destinationCoordinates = new Point(position.getCoordinates());
        this.logicLevel = logicLevel;
        this.collisionDetector = collisionDetector;
    }

    public LogicProjectile shoot() {
        if (!coolDownTracker.isReady()) {
            return null;
        }
        coolDownTracker.resetTracker(coolDown);
        Direction projectileDirection = Direction.getDirectionByOrientation(position.getOrientation());
        Point projectileStart = Point.sum(destinationCoordinates, projectileDirection.getShift());
        return new LogicProjectile(
                projectileSpeed,
                projectileDamage,
                projectileDirection,
                new Position(position.getOrientation(), projectileStart),
                logicLevel,
                collisionDetector
        );
    }

    @Override
    public void activate(float deltaTime) {
        movementProgress = clamp(movementProgress + deltaTime / speed, PROGRESS_MIN, PROGRESS_MAX);
        coolDownTracker.updateTracker(deltaTime);
        Point logicObjectCoordinates = position.getCoordinates();
        if (isMovementFinished() && !logicObjectCoordinates.equals(destinationCoordinates)) {
            position.setCoordinates(new Point(destinationCoordinates));
        }
    }

    @Override
    public void triggerMovement(Direction direction) {
        boolean isCollisionPossible = checkCollisionAfterShift(direction);

        if (!isMovementFinished()) {
            return;
        }

        position.setOrientation(direction.getOrientation());
        if (!isCollisionPossible) {
            movementProgress = PROGRESS_MIN;
            destinationCoordinates.add(direction.getShift().getX(), direction.getShift().getY());
        }
    }

    @Override
    public List<Point> getCurrentPoints() {
        return List.of(position.getCoordinates());
    }

    @Override
    public List<Point> getDestinationPoints() {
        return destinationCoordinates.equals(position.getCoordinates()) ? Collections.emptyList() : List.of(destinationCoordinates);
    }

    @Override
    public void registerHarmfulCollision(float damage) {
        hp -= damage;
        if (hp <= 0) {
            logicLevel.deleteGameObjects(List.of(this));
        }
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

    private boolean checkCollisionAfterShift(Direction direction) {
        return collisionDetector.isCollisionPossible(
                () -> List.of(Point.sum(position.getCoordinates(), direction.getShift()))
        );
    }

    private boolean isMovementFinished() {
        return Math.abs(movementProgress - PROGRESS_MAX) < FLOAT_ROUNDING_ERROR;
    }

}
