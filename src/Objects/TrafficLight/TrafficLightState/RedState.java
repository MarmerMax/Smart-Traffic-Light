package Objects.TrafficLight.TrafficLightState;

import javafx.scene.image.Image;

public class RedState extends State {
    private final String NAME = "Red";
    private Image image;

    @Override
    public Image getImage() {
        if (image == null) {
            image = new Image("file:images/lights/long/red-s.png");
        }
        return image;
    }

    public String getName() {
        return NAME;
    }
}
