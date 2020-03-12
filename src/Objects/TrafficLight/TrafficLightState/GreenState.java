package Objects.TrafficLight.TrafficLightState;

import javafx.scene.image.Image;

public class GreenState extends State {
    private final String NAME = "Green";
    private final Image image = new Image("file:images/lights/green-s.png");

    @Override
    public Image getImage() {
        return image;
    }

    public String getName() {
        return NAME;
    }
}
