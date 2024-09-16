package ru.mipt.bit.platformer.commands.impl;

import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.commands.Command;
import ru.mipt.bit.platformer.level.impl.LibGdxGraphicLevel;

/**
 * Domain level
 */
@RequiredArgsConstructor
public class ShowHealthBarsCommand implements Command {
    private final LibGdxGraphicLevel level;

    @Override
    public void execute() {
        level.setPrintHealthBars(!level.printHealthBars());
    }
}
