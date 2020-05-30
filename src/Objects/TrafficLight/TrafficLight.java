package Objects.TrafficLight;

import Objects.TrafficLight.TrafficLightState.*;
import javafx.scene.image.Image;

public class TrafficLight {

    private final State[] STATES = {
            new RedState(),
            new RedYellowState(),
            new GreenState(),
            new YellowState()
    };

    private int actualState;

    public TrafficLight() {
        actualState = 0;
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

    public State getActualState() {
        return STATES[actualState];
    }
}
