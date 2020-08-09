package Objects.Crossroad;

import Objects.Road.Road;
import Objects.TrafficLight.TrafficLight;
import Objects.TrafficLight.TrafficLightTimeDistribution.TimeDistribution;
import Tools.Constants;

public class Crossroad {

    private Road[] roads;
    private TrafficLight southTrafficLight;
    private TrafficLight northTrafficLight;
    private TrafficLight eastTrafficLight;
    private TrafficLight westTrafficLight;
    private TimeDistribution timeDistribution;
    private int actualState = 0;
    private boolean isNorthSouthActive = false;
    private boolean isEastWestActive = false;

    private boolean isNorthSouthChanging = false;

    public Crossroad(Road[] r) {
        roads = r;
        timeDistribution = new TimeDistribution(Constants.CROSSROAD_PHASE_TIME);
        northTrafficLight = new TrafficLight();
        eastTrafficLight = new TrafficLight();
        southTrafficLight = new TrafficLight();
        westTrafficLight = new TrafficLight();
    }

    public void changeTrafficLightStateOnCrossroad() {

        if (actualState < 4) {
            isNorthSouthChanging = true;
            southTrafficLight.changeState();
            northTrafficLight.changeState();
            actualState++;
            if (actualState == 2) {
                isNorthSouthActive = true;
            } else {
                isNorthSouthActive = false;
            }
        } else {
            isNorthSouthChanging = false;
            westTrafficLight.changeState();
            eastTrafficLight.changeState();
            actualState++;
            if (actualState == 6) {
                isEastWestActive = true;
            } else {
                isEastWestActive = false;
            }
        }
        actualState %= 8;
//        System.out.println("north-south: " + isNorthSouthActive + ", east-west: " + isEastWestActive);
    }

    public Road getNorthRoad() {
        return roads[0];
    }

    public Road getEastRoad() {
        return roads[1];
    }

    public Road getSouthRoad() {
        return roads[2];
    }

    public Road getWestRoad() {
        return roads[3];
    }

    public void addTimeToEastWestRote(int north_south, int east_west) {
        timeDistribution.setNorthSouth(north_south);
        timeDistribution.setEastWest(east_west);
    }

    public TimeDistribution getTimeDistribution() {
        return timeDistribution;
    }

    public void addTimeToEastWestRoute() {
        timeDistribution.addTimeToEastWestRoute();
    }

    public void addTimeToNorthSouthRoute() {
        timeDistribution.addTimeToNorthSouthRoute();
    }

    public TrafficLight getSouthTrafficLight() {
        return southTrafficLight;
    }

    public TrafficLight getWestTrafficLight() {
        return westTrafficLight;
    }

    public TrafficLight getEastTrafficLight() {
        return eastTrafficLight;
    }

    public TrafficLight getNorthTrafficLight() {
        return northTrafficLight;
    }

    public boolean isNorthSouthActive() {
        return isNorthSouthActive;
    }

    public boolean isEastWestActive() {
        return isEastWestActive;
    }

    public TrafficLight[] getActiveTrafficLights() {
        TrafficLight[] activeTrafficLights = new TrafficLight[2];
        if (isNorthSouthActive) {
            activeTrafficLights[0] = southTrafficLight;
            activeTrafficLights[0] = northTrafficLight;
        } else {
            activeTrafficLights[0] = eastTrafficLight;
            activeTrafficLights[0] = westTrafficLight;
        }
        return activeTrafficLights;
    }

    public boolean isNorthSouthActual() {
        return isNorthSouthChanging;
    }
}
