package ru.mipt.bit.platformer.placement;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Domain class
 */
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Position {
    private Orientation orientation;
    private Point coordinates;

    public Position(Orientation orientation, Point coordinates) {
        this.orientation = orientation;
        this.coordinates = coordinates;
    }

    public Position(Point coordinates) {
        this(Orientation.RIGHT, coordinates);
    }

    public Position(int x, int y) {
        this(Orientation.RIGHT, new Point(x,y));
    }
}
