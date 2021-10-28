package ru.mipt.bit.platformer.ai.entities.converter;

import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.objects.movable.impl.Tank;

public interface TankToPlayerConverter {
    Player convertToPlayer(Tank tank);
}
