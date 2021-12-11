package ru.mipt.bit.platformer.ai.converter;

import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.objects.logic.LogicTank;

public interface TankToPlayerConverter {
    Player convertToPlayer(LogicTank tank);
}
