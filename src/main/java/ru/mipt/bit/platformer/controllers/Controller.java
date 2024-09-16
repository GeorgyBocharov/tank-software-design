package ru.mipt.bit.platformer.controllers;

/**
 * Port between application and adapter levels
 */
public interface Controller {
    void generate(float deltaTime);
}
