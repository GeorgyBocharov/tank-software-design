package ru.mipt.bit.platformer.position.generators;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.ToString;

import ru.mipt.bit.platformer.placement.Position;

import java.util.List;

/**
 * Application level
 */
@AllArgsConstructor
@Getter
@ToString
public class TreesAndTanksPositionContainer {
    private final List<Position> tanks;
    private final List<Position> trees;
}
