package ru.mipt.bit.platformer.objects;


import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.collision.Colliding;

@ToString
@EqualsAndHashCode
public class LogicObject implements Colliding {
    private Orientation orientation;
    private Point coordinates;

    public LogicObject(Orientation orientation, Point coordinates) {
        this.orientation = orientation;
        this.coordinates = coordinates;
    }

    public LogicObject(Point coordinates) {
        this(Orientation.RIGHT, coordinates);
    }

    public LogicObject(int x, int y) {
        this(Orientation.RIGHT, new Point(x,y));
    }

    @Override
    public boolean isCollisionPossible(Point othersCoordinates) {
        return coordinates.equals(othersCoordinates);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }
}
