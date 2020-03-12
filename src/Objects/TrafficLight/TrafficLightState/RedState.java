package Objects.TrafficLight.TrafficLightState;

import javafx.scene.image.Image;

public class RedState extends State {
    private final String NAME = "Red";
    private final Image image = new Image("file:images/lights/red-s.png");

    @Override
    public Image getImage() {
        return image;
    }

    public String getName() {
        return NAME;
    }
}
