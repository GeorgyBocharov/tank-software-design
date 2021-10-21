package ru.mipt.bit.platformer.geometry;

import com.badlogic.gdx.math.GridPoint2;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Point {
    private final int x;
    private final int y;

    public boolean isEqualToGridPoint2(GridPoint2 gridPoint2) {
        return gridPoint2.x == x && gridPoint2.y == y;
    }
}
