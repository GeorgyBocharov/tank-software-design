package ru.mipt.bit.platformer.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.collision.Colliding;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.graphic.Disposable;

@RequiredArgsConstructor
@Getter
public class Obstacle implements Activated, Colliding, Disposable {

    private final CollidingObject collidingObject;
    private final LibGdxGraphicObject libGdxGraphicObject;

    @Override
    public boolean isCollisionPossible(Point othersCoordinates) {
        return collidingObject.isCollisionPossible(othersCoordinates);
    }

    @Override
    public void dispose() {
        libGdxGraphicObject.dispose();
    }

    @Override
    public void activate(float deltaTime) {

    }
}
