package Objects.Crossroad;

import Objects.Road.Road;
import Objects.TrafficLight.TrafficLight;

public class Crossroad {

    private final int CROSSROAD_SIZE = 4;
    private Road[] roads;
    private TrafficLight southTrafficLight;
    private TrafficLight northTrafficLight;
    private TrafficLight eastTrafficLight;
    private TrafficLight westTrafficLight;
    private int actualState = 0;

    public Crossroad() {
        roads = new Road[CROSSROAD_SIZE];
        TrafficLight tl0 = new TrafficLight();
        TrafficLight tl1 = new TrafficLight();
        TrafficLight tl2 = new TrafficLight();
        TrafficLight tl3 = new TrafficLight();
        northTrafficLight = tl0;
        eastTrafficLight = tl1;
        southTrafficLight = tl2;
        westTrafficLight = tl3;
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
        roads[0] = north;
    }

    public void addSouthRoad(Road south) {
        roads[2] = south;
    }

    public void addWestRoad(Road west) {
        roads[3] = west;
    }

    public void addEastRoad(Road east) {
        roads[1] = east;
    }
}
