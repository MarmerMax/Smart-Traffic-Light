package Objects.Car;

import Objects.DrawObject;
import javafx.scene.image.Image;

public abstract class Car implements DrawObject {

    protected Image image;
    protected double length;
    protected double acceleration;
    protected double deceleration;
    protected double max_speed;
    protected double car_type;

    @Override
    public Image getImage() {
        if (image != null) {
            return image;
        } else {
            String path = "file:images/cars/car" + car_type + ".png";
            image = new Image(path);
            return image;
        }
    }

    public double getLength() {
        return length;
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
