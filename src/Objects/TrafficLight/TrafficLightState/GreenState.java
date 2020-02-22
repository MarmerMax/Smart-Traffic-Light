package Objects.TrafficLight.TrafficLightState;

import Objects.DrawObject;
import javafx.scene.image.Image;

public class GreenState extends State {
    private final String NAME = "Green";

    @Override
    public Image getImage() {
        return new Image("file:images/lights/green-s.png");
    }

    public String getName() {
        return NAME;
    }
}
