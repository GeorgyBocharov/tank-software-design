package ru.mipt.bit.platformer.objects;


import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.collision.Colliding;

@ToString
@EqualsAndHashCode
public class LogicObject implements Colliding {
    private float rotation;
    private Point coordinates;

    public LogicObject(float rotation, Point coordinates) {
        this.rotation = rotation;
        this.coordinates = coordinates;
    }

    public LogicObject(Point coordinates) {
        this(0f, coordinates);
    }

    public LogicObject(int x, int y) {
        this(0f, new Point(x,y));
    }

    @Override
    public boolean isCollisionPossible(Point othersCoordinates) {
        return coordinates.equals(othersCoordinates);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }
}
