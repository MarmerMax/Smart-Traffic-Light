package Objects.Car;

import Objects.DrawObject;
import javafx.scene.image.Image;

public abstract class Car implements DrawObject {

    protected Image image;
    protected double length;
    protected double width;
    protected double acceleration; // m/s
    protected double deceleration; // m/s
    protected double max_speed; // km/h
    protected double car_type;

    @Override
    public Image getImage() {
        if (image != null) {
            return image;
        } else {
            String path = "file:images/cars/car" + (int) car_type + ".png";
            image = new Image(path);
            return image;
        }
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getDeceleration() {
        return deceleration;
    }

    public double getMaxSpeed() {
        return max_speed;
    }

    public double getAcceleration() {
        return acceleration;
    }
}
