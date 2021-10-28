package ru.mipt.bit.platformer.ai.entities.converter.impl;

import org.awesome.ai.state.immovable.Obstacle;
import ru.mipt.bit.platformer.ai.entities.converter.GraphicObjectToObstacleConverter;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.objects.LibGdxGraphicObject;

public class GraphicObjectToObstacleConverterImpl implements GraphicObjectToObstacleConverter {

    @Override
    public Obstacle convertToObstacle(LibGdxGraphicObject gdxGraphicObject) {
        Point coordinates = gdxGraphicObject.getLogicObject().getCoordinates();
        return new Obstacle(coordinates.getX(), coordinates.getY());
    }
}
