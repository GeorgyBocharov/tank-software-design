package ru.mipt.bit.platformer.entities;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.mipt.bit.platformer.geometry.Point;
import ru.mipt.bit.platformer.service.Colliding;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class LogicObject implements Colliding {
    private float rotation;
    private Point coordinates;

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
