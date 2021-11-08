package ru.mipt.bit.platformer.ai.converter;

import org.awesome.ai.state.immovable.Obstacle;
import ru.mipt.bit.platformer.objects.CollidingObject;

public interface ObstacleConverter {
    Obstacle convertToObstacle(CollidingObject collidingObject);
}
