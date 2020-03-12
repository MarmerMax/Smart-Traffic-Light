package Objects.Car;

import Objects.DrawObject;
import javafx.scene.image.Image;

public class Car implements DrawObject {

    private Image image;

    public Car(){
        int car = generateRandomNumber();
        String path = "file:images/cars/car" + car + ".png";
        image = new Image(path);
    }

    @Override
    public Image getImage() {
        return image;
    }

    private int generateRandomNumber(){
        int num;
        double random = Math.random();

        if(random < 0.05){
            num = 1;
        } else if (0.05 <= random && random < 0.1){
            num = 2;
        } else if (0.1 < random && random < 0.2){
            num = 3;
        } else if (0.2 <= random && random < 0.5){
            num = 4;
        } else if (0.5 < random && random < 0.8){
            num = 5;
        } else {
            num = 6;
        }
        return num;
    }
}
