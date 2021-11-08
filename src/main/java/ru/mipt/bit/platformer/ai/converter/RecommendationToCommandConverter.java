package ru.mipt.bit.platformer.ai.converter;

import org.awesome.ai.Recommendation;
import ru.mipt.bit.platformer.commands.Command;

public interface RecommendationToCommandConverter {
    Command convertToCommand(Recommendation recommendation);
}
