package Objects.TrafficLight.TrafficLightState;

import javafx.scene.image.Image;

public class RedYellowState extends State {
    private final String NAME = "RedYellow";
    private final Image image = new Image("file:images/lights/red-yellow-s.png");

    @Override
    public Image getImage() {
        return image;
    }

    public String getName() {
        return NAME;
    }
}
