package ru.mipt.bit.platformer.objects.logic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.mipt.bit.platformer.placement.Point;
import ru.mipt.bit.platformer.objects.GameObject;
import ru.mipt.bit.platformer.placement.Position;

import java.util.List;

/**
 * Entity class
 * Describes obstacle, i.e. tree
 */
@RequiredArgsConstructor
@Getter
@ToString
public class LogicObstacle implements GameObject {

    private final Position position;

    @Override
    public List<Point> getCurrentPoints() {
        return List.of(position.getCoordinates());
    }

}
