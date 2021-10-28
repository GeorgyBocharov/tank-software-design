package ru.mipt.bit.platformer.ai.entities.converter.impl;

import lombok.RequiredArgsConstructor;
import org.awesome.ai.state.movable.Bot;
import ru.mipt.bit.platformer.ai.entities.converter.OrientationConverter;
import ru.mipt.bit.platformer.ai.entities.converter.TankToBotConverter;
import ru.mipt.bit.platformer.objects.movable.impl.Tank;

@RequiredArgsConstructor
public class TankToBotConverterImpl implements TankToBotConverter {

    private final OrientationConverter orientationConverter;

    @Override
    public Bot convertToBot(Tank tank) {
        return Bot.builder()
                .x(tank.getGraphicObject().getLogicObject().getCoordinates().getX())
                .y(tank.getGraphicObject().getLogicObject().getCoordinates().getY())
                .destX(tank.getDestinationCoordinates().x)
                .destY(tank.getDestinationCoordinates().y)
                .orientation(orientationConverter.convertToOrientation(tank.getGraphicObject().getLogicObject().getOrientation()))
                .source(tank)
                .build();
    }
}
