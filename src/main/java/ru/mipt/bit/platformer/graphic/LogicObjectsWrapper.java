package ru.mipt.bit.platformer.graphic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.ToString;

import ru.mipt.bit.platformer.entities.LogicObject;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class LogicObjectsWrapper {
    private final List<LogicObject> tanks;
    private final List<LogicObject> trees;
}
