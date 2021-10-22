package ru.mipt.bit.platformer.objects.placement;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.ToString;

import ru.mipt.bit.platformer.objects.LogicObject;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class LogicObjectsWrapper {
    private final List<LogicObject> tanks;
    private final List<LogicObject> trees;
}
