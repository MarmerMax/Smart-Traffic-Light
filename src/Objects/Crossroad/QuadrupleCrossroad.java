package Objects.Crossroad;

import Objects.Road.Road;
import Objects.TrafficLight.TrafficLight;

public class QuadrupleCrossroad {

    private final int CROSSROAD_SIZE = 4;
    private Road[] roads;
    private TrafficLight souhtTrafficLight;
    private TrafficLight youthTrafficLight;
    private TrafficLight eastTrafficLight;
    private TrafficLight westTrafficLight;
    private int actualState = 0;

    public QuadrupleCrossroad() {
        roads = new Road[CROSSROAD_SIZE];
        TrafficLight tl1 = new TrafficLight();
        TrafficLight tl2 = new TrafficLight();
        souhtTrafficLight = tl1;
        youthTrafficLight = tl1;
        eastTrafficLight = tl2;
        westTrafficLight = tl2;
    }

    public void changeState() {
        if (actualState < 4) {
            souhtTrafficLight.changeState();
        } else {
            westTrafficLight.changeState();
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
