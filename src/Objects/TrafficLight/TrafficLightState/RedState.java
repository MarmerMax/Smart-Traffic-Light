package Objects.TrafficLight.TrafficLightState;

import Objects.DrawObject;
import javafx.scene.image.Image;

public class RedState extends State {
    private final String NAME = "Red";

    @Override
    public Image getImage() {
        return new Image("file:images/lights/red-s.png");
    }

    public String getName() {
        return NAME;
    }
}
