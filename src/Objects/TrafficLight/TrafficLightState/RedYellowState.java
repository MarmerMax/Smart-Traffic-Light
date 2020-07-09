package Objects.TrafficLight.TrafficLightState;

import javafx.scene.image.Image;

public class RedYellowState extends State {
    private final String NAME = "RedYellow";
    private Image image;

    @Override
    public Image getImage() {
        if (image == null) {
            image = new Image("file:images/lights/long/red-yellow-s.png");
        }
        return image;
    }

    public String getName() {
        return NAME;
    }
}
