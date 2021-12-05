package ru.mipt.bit.platformer.position.generators;

/**
 * port between application and adapter levels
 */
public interface GameObjectPositionsGenerator {
    TreesAndTanksPositionContainer generateTreesAndTanksPositions();
}
