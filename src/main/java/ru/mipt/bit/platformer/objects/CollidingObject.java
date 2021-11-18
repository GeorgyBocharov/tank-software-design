package ru.mipt.bit.platformer.objects;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.collision.Colliding;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class CollidingObject implements Colliding, Activated {
    private Orientation orientation;
    private Point coordinates;

    public CollidingObject(Orientation orientation, Point coordinates) {
        this.orientation = orientation;
        this.coordinates = coordinates;
    }

    public CollidingObject(Point coordinates) {
        this(Orientation.RIGHT, coordinates);
    }

    public CollidingObject(int x, int y) {
        this(Orientation.RIGHT, new Point(x,y));
    }

    @Override
    public void activate(float deltaTime) {

    }

    @Override
    public boolean isCollisionPossible(Point othersCoordinates) {
        return coordinates.equals(othersCoordinates);
    }
}
