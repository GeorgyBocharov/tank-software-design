package ru.mipt.bit.platformer.objects.placement;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.ToString;

import ru.mipt.bit.platformer.objects.CollidingObject;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class TreesAndTanksPositionContainer {
    private final List<CollidingObject> tanks;
    private final List<CollidingObject> trees;
}
