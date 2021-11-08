package ru.mipt.bit.platformer.ai.converter.impl;

import org.awesome.ai.state.immovable.Obstacle;
import ru.mipt.bit.platformer.ai.converter.ObstacleConverter;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.objects.CollidingObject;

public class ObstacleConverterImpl implements ObstacleConverter {

    @Override
    public Obstacle convertToObstacle(CollidingObject collidingObject) {
        Point coordinates = collidingObject.getCoordinates();
        return new Obstacle(coordinates.getX(), coordinates.getY());
    }
}
