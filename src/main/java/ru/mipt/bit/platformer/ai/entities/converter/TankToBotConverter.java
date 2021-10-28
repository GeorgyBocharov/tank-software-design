package ru.mipt.bit.platformer.ai.entities.converter;

import org.awesome.ai.state.movable.Bot;
import ru.mipt.bit.platformer.objects.movable.impl.Tank;

public interface TankToBotConverter {
    Bot convertToBot(Tank tank);
}
