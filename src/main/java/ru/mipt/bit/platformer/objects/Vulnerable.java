package ru.mipt.bit.platformer.objects;

public interface Vulnerable {
    float getHP();
    float getMaxHP();
    void hurt(float damage);
    void heal(float extraHP);
    boolean isAlive();
}
