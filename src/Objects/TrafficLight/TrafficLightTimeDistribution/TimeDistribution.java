package Objects.TrafficLight.TrafficLightTimeDistribution;

public class TimeDistribution {

    private int north_south;
    private int east_west;


    public TimeDistribution() {
        north_south = 20;
        east_west = 20;
    }

    public void addTimeToEastWestRoute() {
        if (north_south > 10) {
            north_south++;
            east_west--;
        }
    }

    public void addTimeToNorthSouthRoute() {
        if (east_west > 10) {
            north_south++;
            east_west--;
        }
    }

    public void setEastWest(int east_west) {
        this.east_west = east_west;
    }

    public void setNorthSouth(int north_south) {
        this.north_south = north_south;
    }

    public int getNorthSouth() {
        return north_south;
    }

    public int getEastWest() {
        return east_west;
    }
}
