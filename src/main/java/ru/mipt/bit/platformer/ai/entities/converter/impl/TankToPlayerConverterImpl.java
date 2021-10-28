package ru.mipt.bit.platformer.ai.entities.converter.impl;

import lombok.RequiredArgsConstructor;
import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.ai.entities.converter.OrientationConverter;
import ru.mipt.bit.platformer.ai.entities.converter.TankToPlayerConverter;
import ru.mipt.bit.platformer.objects.movable.impl.Tank;

@RequiredArgsConstructor
public class TankToPlayerConverterImpl implements TankToPlayerConverter {

    private final OrientationConverter orientationConverter;

    @Override
    public Player convertToPlayer(Tank tank) {
        return Player.builder()
                .x(tank.getGraphicObject().getLogicObject().getCoordinates().getX())
                .y(tank.getGraphicObject().getLogicObject().getCoordinates().getY())
                .destX(tank.getDestinationCoordinates().x)
                .destY(tank.getDestinationCoordinates().y)
                .orientation(orientationConverter.convertToOrientation(tank.getGraphicObject().getLogicObject().getOrientation()))
                .source(tank)
                .build();
    }
}
