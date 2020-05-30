package Objects.TrafficLight.TrafficLightState;

import javafx.scene.image.Image;

public class GreenState extends State {
    private final String NAME = "Green";
    private Image image;

    @Override
    public Image getImage() {
        if (image == null) {
            image = new Image("file:images/lights/green-s.png");
        }
        return image;
    }

    public String getName() {
        return NAME;
    }
}
