package ru.mipt.bit.platformer.objects.states.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.collision.CollisionDetector;
import ru.mipt.bit.platformer.level.LogicLevel;
import ru.mipt.bit.platformer.objects.Vulnerable;
import ru.mipt.bit.platformer.objects.logic.LogicProjectile;
import ru.mipt.bit.platformer.objects.logic.LogicTank;
import ru.mipt.bit.platformer.objects.states.TankState;
import ru.mipt.bit.platformer.objects.support.CoolDownTracker;
import ru.mipt.bit.platformer.placement.Direction;
import ru.mipt.bit.platformer.placement.Point;
import ru.mipt.bit.platformer.placement.Position;

import java.util.List;

/**
 * Application class
 */
@RequiredArgsConstructor
public abstract class AbstractTankState implements TankState {

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
    public void hurtTank(float damage) {
        float maxHP = tank.getMaxHP();
        LogicLevel logicLevel = tank.getLogicLevel();
        Vulnerable vulnerable = tank.getVulnerableObject();
        vulnerable.hurt(damage);
        float hp = tank.getHP();
        if (!tank.isAlive()) {
            logicLevel.deleteGameObjects(List.of(tank));
        } else if (hp < maxHP / 3) {
            tank.setState(new SeverelyDamagedTankState(tank));
        } else if (hp < maxHP / 2) {
            tank.setState(new ModeratelyDamagedTankState(tank));
        }
    }

    @Override
    public void healTank(float extraHP) {
        float maxHP = tank.getMaxHP();
        Vulnerable vulnerable = tank.getVulnerableObject();
        vulnerable.heal(extraHP);
        float hp = tank.getHP();
        if (hp > maxHP / 2) {
            tank.setState(new SlightlyDamagedTankState(tank));
        } else if (hp > maxHP / 3) {
            tank.setState(new ModeratelyDamagedTankState(tank));
        }
    }
}
