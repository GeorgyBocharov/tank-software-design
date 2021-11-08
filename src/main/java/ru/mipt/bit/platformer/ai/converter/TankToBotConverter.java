package ru.mipt.bit.platformer.ai.converter;

import org.awesome.ai.state.movable.Bot;
import ru.mipt.bit.platformer.objects.Tank;

public interface TankToBotConverter {
    Bot convertToBot(Tank tank);
}
