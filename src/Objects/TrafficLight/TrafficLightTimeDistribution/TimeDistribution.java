package Objects.TrafficLight.TrafficLightTimeDistribution;

public class TimeDistribution {

    private int north_south;
    private int east_west;

    public TimeDistribution() {
        north_south = 10;
        east_west = 10;
    }

    public void setEast_west(int east_west) {
        this.east_west = east_west;
    }

    public void setNorth_south(int north_south) {
        this.north_south = north_south;
    }

    public int getNorth_south() {
        return north_south;
    }

    public int getEast_west() {
        return east_west;
    }
}
