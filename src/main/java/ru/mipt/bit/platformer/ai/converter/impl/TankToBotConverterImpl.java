package ru.mipt.bit.platformer.ai.converter.impl;

import lombok.RequiredArgsConstructor;
import org.awesome.ai.state.movable.Bot;
import ru.mipt.bit.platformer.ai.converter.OrientationConverter;
import ru.mipt.bit.platformer.ai.converter.TankToBotConverter;
import ru.mipt.bit.platformer.objects.logic.LogicTank;

@RequiredArgsConstructor
public class TankToBotConverterImpl implements TankToBotConverter {

    private final OrientationConverter orientationConverter;

    @Override
    public Bot convertToBot(LogicTank tank) {
        return Bot.builder()
                .x(tank.getPosition().getCoordinates().getX())
                .y(tank.getPosition().getCoordinates().getY())
                .destX(tank.getDestinationCoordinates().getX())
                .destY(tank.getDestinationCoordinates().getY())
                .orientation(orientationConverter.convertToOrientation(tank.getPosition().getOrientation()))
                .source(tank)
                .build();
    }
}
