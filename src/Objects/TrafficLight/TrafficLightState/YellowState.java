package Objects.TrafficLight.TrafficLightState;

import javafx.scene.image.Image;

public class YellowState extends State {
    private final String NAME = "Yellow";
    private Image image;

    @Override
    public Image getImage() {
        if (image == null) {
            image = new Image("file:images/lights/yellow-s.png");
        }
        return image;
    }

    public String getName() {
        return NAME;
    }
}
