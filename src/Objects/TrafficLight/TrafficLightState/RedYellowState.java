package Objects.TrafficLight.TrafficLightState;

import Objects.DrawObject;
import javafx.scene.image.Image;

public class RedYellowState extends State {
    private final String NAME = "RedYellow";

    @Override
    public Image getImage() {
        return new Image("file:images/lights/red-yellow-s.png");
    }

    public String getName() {
        return NAME;
    }
}
