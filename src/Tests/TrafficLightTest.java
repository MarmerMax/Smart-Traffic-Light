package Tests;

import Objects.TrafficLight.TrafficLight;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrafficLightTest {

    private static TrafficLight trafficLight;

    @BeforeAll
    static void setup() {
        trafficLight = new TrafficLight();
    }

    @Test
    void getTrafficLightStateName() {
        assertEquals(trafficLight.getTrafficLightStateName(), "Red");
        trafficLight.changeState();
        assertEquals(trafficLight.getTrafficLightStateName(), "RedYellow");
        trafficLight.changeState();
        assertEquals(trafficLight.getTrafficLightStateName(), "Green");
        trafficLight.changeState();
        assertEquals(trafficLight.getTrafficLightStateName(), "Yellow");
        trafficLight.changeState();
    }

    @Test
    void changeState() {
        for (int i = 0; i < 100; i++) {
            if (i % 4 == 0) {
                assertEquals(trafficLight.getTrafficLightStateName(), "Red");
            }
            if (i % 4 == 1) {
                assertEquals(trafficLight.getTrafficLightStateName(), "RedYellow");
            }
            if (i % 4 == 2) {
                assertEquals(trafficLight.getTrafficLightStateName(), "Green");
            }
            if (i % 4 == 3) {
                assertEquals(trafficLight.getTrafficLightStateName(), "Yellow");
            }
            trafficLight.changeState();
        }
    }

    @Test
    void getActualState() {
        assertNotNull(trafficLight.getActualState());
        assertNotNull(trafficLight.getActualState());
        assertNotNull(trafficLight.getActualState());
        assertNotNull(trafficLight.getActualState());
    }
}