package ru.mipt.bit.platformer.movables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.movement.LibGdxMovementService;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class Tank extends AbstractLibGdxMovableObject {

    private final float speed;

    public Tank(float speed, TiledMapTileLayer tileLayer, LibGdxMovementService movementService,
                Texture texture, GridPoint2 startCoordinates, float rotation) {

        super(tileLayer, movementService, texture, startCoordinates, rotation);
        this.speed = speed;
    }

    @Override
    public void move(float deltaTime) {
        interpolateMovement();
        movementProgress = clamp(movementProgress + deltaTime / speed, PROGRESS_MIN, PROGRESS_MAX);
        if (isMovementFinished()) {
            graphicObject
                    .getLogicObject()
                    .setCoordinates(new GridPoint2(destinationCoordinates));
        }
    }
}
