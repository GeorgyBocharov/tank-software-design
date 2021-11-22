package ru.mipt.bit.platformer.objects;

import ru.mipt.bit.platformer.placement.Point;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Colliding {
    List<Point> getCurrentPoints();

    default List<Point> getDestinationPoints() {
        return Collections.emptyList();
    }

    default boolean collides(Colliding colliding) {
        List<Point> allOtherPoints = Stream
                .concat(colliding.getCurrentPoints().stream(), colliding.getDestinationPoints().stream())
                .collect(Collectors.toList());

        boolean result;
        if (getDestinationPoints().isEmpty()) {
            result = oneContainsFromAnother(allOtherPoints, getCurrentPoints());
        } else {
            result = oneContainsFromAnother(colliding.getCurrentPoints(), getCurrentPoints()) ||
                    oneContainsFromAnother(colliding.getDestinationPoints(), getDestinationPoints());
        }

        return result;
    }

    default void registerHarmfulCollision(float damage) {
    }

    private boolean oneContainsFromAnother(List<Point> otherPoints, List<Point> thisPoints) {
        for (Point other : otherPoints) {
            for (Point thisPoint : thisPoints) {
                if (thisPoint.equals(other)) {
                    return true;
                }
            }
        }
        return false;
    }

}
