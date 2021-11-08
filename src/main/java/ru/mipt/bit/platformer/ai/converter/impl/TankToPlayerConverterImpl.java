package ru.mipt.bit.platformer.ai.converter.impl;

import lombok.RequiredArgsConstructor;
import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.ai.converter.OrientationConverter;
import ru.mipt.bit.platformer.ai.converter.TankToPlayerConverter;
import ru.mipt.bit.platformer.objects.Tank;

@RequiredArgsConstructor
public class TankToPlayerConverterImpl implements TankToPlayerConverter {

    private final OrientationConverter orientationConverter;

    @Override
    public Player convertToPlayer(Tank tank) {
        return Player.builder()
                .x(tank.getLibGdxGraphicObject().getCollidingObject().getCoordinates().getX())
                .y(tank.getLibGdxGraphicObject().getCollidingObject().getCoordinates().getY())
                .destX(tank.getDestinationCoordinates().x)
                .destY(tank.getDestinationCoordinates().y)
                .orientation(orientationConverter.convertToOrientation(tank.getLibGdxGraphicObject().getCollidingObject().getOrientation()))
                .source(tank)
                .build();
    }
}
