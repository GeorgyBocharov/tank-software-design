package ru.mipt.bit.platformer.ai.converter.impl;

import lombok.RequiredArgsConstructor;
import org.awesome.ai.state.movable.Bot;
import ru.mipt.bit.platformer.ai.converter.OrientationConverter;
import ru.mipt.bit.platformer.ai.converter.TankToBotConverter;
import ru.mipt.bit.platformer.objects.Tank;

@RequiredArgsConstructor
public class TankToBotConverterImpl implements TankToBotConverter {

    private final OrientationConverter orientationConverter;

    @Override
    public Bot convertToBot(Tank tank) {
        return Bot.builder()
                .x(tank.getLibGdxGraphicObject().getCollidingObject().getCoordinates().getX())
                .y(tank.getLibGdxGraphicObject().getCollidingObject().getCoordinates().getY())
                .destX(tank.getDestinationCoordinates().x)
                .destY(tank.getDestinationCoordinates().y)
                .orientation(orientationConverter.convertToOrientation(tank.getLibGdxGraphicObject().getCollidingObject().getOrientation()))
                .source(tank)
                .build();
    }
}
