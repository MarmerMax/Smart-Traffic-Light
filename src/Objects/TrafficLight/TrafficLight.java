package Objects.TrafficLight;

import Objects.TrafficLight.TrafficLightState.*;
import javafx.scene.image.Image;

public class TrafficLight {

    private final State[] STATES = {new GreenState(), new YellowState(), new RedState(), new RedYellowState()};
    private int actualState = 2;

    public TrafficLight() {
    }

    public Image getTrafficLightImage() {
        return STATES[actualState].getImage();
    }

    public String getTrafficLightStateName() {
        return STATES[actualState].getName();
    }

    public void changeState() {
        actualState = (actualState + 1) % 4;
    }
}
