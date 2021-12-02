package ru.mipt.bit.platformer.ai.converter.impl;

import org.awesome.ai.state.immovable.Obstacle;
import ru.mipt.bit.platformer.ai.converter.ObstacleConverter;
import ru.mipt.bit.platformer.placement.Point;
import ru.mipt.bit.platformer.objects.logic.LogicObstacle;

/**
 * Adapter
 */
public class ObstacleConverterImpl implements ObstacleConverter {

    @Override
    public Obstacle convertToObstacle(LogicObstacle logicObstacle) {
        Point coordinates = logicObstacle.getPosition().getCoordinates();
        return new Obstacle(coordinates.getX(), coordinates.getY());
    }
}
