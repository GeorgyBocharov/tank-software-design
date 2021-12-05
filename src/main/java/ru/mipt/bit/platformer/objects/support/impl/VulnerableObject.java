package ru.mipt.bit.platformer.objects.support.impl;

import ru.mipt.bit.platformer.objects.Vulnerable;

/**
 * Domain class
 */
public class VulnerableObject implements Vulnerable {

    private final float maxHp;
    private float hp;

    public VulnerableObject(float maxHp) {
        this.maxHp = maxHp;
        this.hp = maxHp;
    }

    @Override
    public float getHP() {
        return hp;
    }

    @Override
    public float getMaxHP() {
        return maxHp;
    }

    @Override
    public void hurt(float damage) {
        if (damage <= 0) {
            return;
        }
        if (damage >= hp) {
            hp = 0;
        } else {
            hp -= damage;
        }
    }

    @Override
    public void heal(float extraHP) {
        if (extraHP <= 0) {
            return;
        }
        float delta = maxHp - hp;
        if (delta < extraHP) {
            hp = maxHp;
        } else {
            hp += extraHP;
        }
    }

    @Override
    public boolean isAlive() {
        return hp > 0;
    }
}
