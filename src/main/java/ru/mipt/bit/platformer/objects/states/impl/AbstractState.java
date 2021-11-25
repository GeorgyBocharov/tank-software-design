package ru.mipt.bit.platformer.objects.states.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.collision.CollisionDetector;
import ru.mipt.bit.platformer.level.LogicLevel;
import ru.mipt.bit.platformer.objects.logic.LogicProjectile;
import ru.mipt.bit.platformer.objects.logic.LogicTank;
import ru.mipt.bit.platformer.objects.states.State;
import ru.mipt.bit.platformer.objects.support.CoolDownTracker;
import ru.mipt.bit.platformer.placement.Direction;
import ru.mipt.bit.platformer.placement.Point;
import ru.mipt.bit.platformer.placement.Position;

import java.util.List;


@RequiredArgsConstructor
public abstract class AbstractState implements State {

    protected final LogicTank tank;

    @Override
    public void shoot() {
        CoolDownTracker coolDownTracker = tank.getCoolDownTracker();
        float coolDown = tank.getCoolDown();
        Position position = tank.getPosition();
        Point destinationCoordinates = tank.getDestinationCoordinates();
        float projectileSpeed = tank.getProjectileSpeed();
        float projectileDamage = tank.getProjectileDamage();
        LogicLevel logicLevel = tank.getLogicLevel();
        CollisionDetector collisionDetector = tank.getCollisionDetector();

        if (!coolDownTracker.isReady()) {
            return;
        }
        coolDownTracker.resetTracker(coolDown);
        Direction projectileDirection = Direction.getDirectionByOrientation(position.getOrientation());
        Point projectileStart = Point.sum(destinationCoordinates, projectileDirection.getShift());
        LogicProjectile projectile = new LogicProjectile(
                projectileSpeed,
                projectileDamage,
                projectileDirection,
                new Position(position.getOrientation(), projectileStart),
                logicLevel,
                collisionDetector
        );
    }

    @Override
    public void recalculateProgress(float deltaTime, float speed) {
        tank.recalculateProgress(deltaTime, speed);
    }

    @Override
    public void registerHarmfulCollision(float damage) {
        float hp = tank.getHp();
        float maxHp = tank.getMaxHp();
        LogicLevel logicLevel = tank.getLogicLevel();
        hp -= damage;
        tank.setHp(hp);
        if (hp <= 0) {
            logicLevel.deleteGameObjects(List.of(tank));
        } else if (hp < maxHp / 3) {
            tank.setState(new SeverelyDamagedState(tank));
        } else if (hp < maxHp / 2) {
            tank.setState(new ModeratelyDamagedState(tank));
        }
    }
}
