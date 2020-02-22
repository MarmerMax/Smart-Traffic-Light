package Objects.TrafficLight.TrafficLightState;

import javafx.scene.image.Image;

public class YellowState extends State {
    private final String NAME = "Yellow";

    @Override
    public Image getImage() {
        return new Image("file:images/lights/yellow-s.png");
    }

    public String getName() {
        return NAME;
    }
}
