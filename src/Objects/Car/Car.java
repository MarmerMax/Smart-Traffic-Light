package Objects.Car;

import Objects.DrawObject;
import javafx.scene.image.Image;

public class Car implements DrawObject {

    private Image image;
    private double length;

    public Car() {
        length = 4.5;
        int car = generateRandomNumber();
        String path = "file:images/cars/car" + car + ".png";
        image = new Image(path);
    }

    @Override
    public Image getImage() {
        return image;
    }

    private int generateRandomNumber() {
        int num;
        double random = Math.random();

        if (random < 0.05) {
            num = 1;
            length = 4.7;
        } else if (0.05 <= random && random < 0.1) {
            num = 2;
            length = 4.7;
        } else if (0.1 < random && random < 0.2) {
            num = 3;
            length = 5.2;
        } else if (0.2 <= random && random < 0.5) {
            num = 4;
            length = 4.6;
        } else if (0.5 < random && random < 0.8) {
            num = 5;
            length = 4.8;
        } else {
            num = 6;
            length = 5.4;
        }
        return num;
    }

    public double getLength() {
        return length;
    }
}
