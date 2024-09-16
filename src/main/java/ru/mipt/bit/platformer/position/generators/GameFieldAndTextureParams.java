package ru.mipt.bit.platformer.position.generators;

/**
 * port between application and adapter levels
 */
public interface GameFieldAndTextureParams {

    int getTextureHeight();
    int getTextureWidth();
    int getGameFieldWidth();
    int getGameFieldHeight();
}
