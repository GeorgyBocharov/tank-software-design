package ru.mipt.bit.platformer.collision;

import ru.mipt.bit.platformer.objects.Colliding;
import ru.mipt.bit.platformer.publisher.Subscriber;


public interface CollisionDetector extends Subscriber {
    boolean isCollisionPossible(Colliding objectToCheck);
}
