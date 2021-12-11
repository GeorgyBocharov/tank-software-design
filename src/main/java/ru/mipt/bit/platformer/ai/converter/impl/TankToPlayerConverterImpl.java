package ru.mipt.bit.platformer.ai.converter.impl;

import lombok.RequiredArgsConstructor;
import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.ai.converter.OrientationConverter;
import ru.mipt.bit.platformer.ai.converter.TankToPlayerConverter;
import ru.mipt.bit.platformer.objects.logic.LogicTank;

@RequiredArgsConstructor
public class TankToPlayerConverterImpl implements TankToPlayerConverter {

    private final OrientationConverter orientationConverter;

    @Override
    public Player convertToPlayer(LogicTank tank) {
        return Player.builder()
                .x(tank.getPosition().getCoordinates().getX())
                .y(tank.getPosition().getCoordinates().getY())
                .destX(tank.getDestinationCoordinates().getX())
                .destY(tank.getDestinationCoordinates().getY())
                .orientation(orientationConverter.convertToOrientation(tank.getPosition().getOrientation()))
                .source(tank)
                .build();
    }
}
