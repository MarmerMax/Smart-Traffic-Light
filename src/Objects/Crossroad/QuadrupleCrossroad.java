package Objects.Crossroad;

import Objects.Road.Road;
import Objects.TrafficLight.TrafficLight;

public class QuadrupleCrossroad {

    private final int CROSSROAD_SIZE = 4;
    private Road[] roads;
    private TrafficLight southTrafficLight;
    private TrafficLight northTrafficLight;
    private TrafficLight eastTrafficLight;
    private TrafficLight westTrafficLight;
    private int actualState = 0;

    public QuadrupleCrossroad() {
        roads = new Road[CROSSROAD_SIZE];
        TrafficLight tl1 = new TrafficLight();
        TrafficLight tl2 = new TrafficLight();
        TrafficLight tl3 = new TrafficLight();
        TrafficLight tl4 = new TrafficLight();
        southTrafficLight = tl1;
        northTrafficLight = tl2;
        eastTrafficLight = tl3;
        westTrafficLight = tl4;
    }

    public void changeState() {
        if (actualState < 4) {
            southTrafficLight.changeState();
            northTrafficLight.changeState();
        } else {
            westTrafficLight.changeState();
            eastTrafficLight.changeState();
        }
        actualState = (actualState + 1) % 8;
    }

    public void addNorthRoad(Road north) {
        roads[2] = north;
    }

    public void addSouthRoad(Road south) {
        roads[0] = south;
    }

    public void addWestRoad(Road west) {
        roads[1] = west;
    }

    public void addEastRoad(Road east) {
        roads[3] = east;
    }
}
