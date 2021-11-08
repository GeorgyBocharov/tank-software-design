package ru.mipt.bit.platformer.ai.converter;

import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.objects.Tank;

public interface TankToPlayerConverter {
    Player convertToPlayer(Tank tank);
}
