package Objects.TrafficLight.TrafficLightState;

import javafx.scene.image.Image;

public class YellowState extends State {
    private final String NAME = "Yellow";
    private final Image image = new Image("file:images/lights/yellow-s.png");

    @Override
    public Image getImage() {
        return image;
    }

    public String getName() {
        return NAME;
    }
}
