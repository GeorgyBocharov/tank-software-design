package ru.mipt.bit.platformer.ai.entities.converter;

import org.awesome.ai.state.immovable.Obstacle;
import ru.mipt.bit.platformer.objects.LibGdxGraphicObject;

public interface GraphicObjectToObstacleConverter {
    Obstacle convertToObstacle(LibGdxGraphicObject gdxGraphicObject);
}
