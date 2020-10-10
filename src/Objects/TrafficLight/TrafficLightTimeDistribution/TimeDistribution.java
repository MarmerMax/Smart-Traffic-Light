package Objects.TrafficLight.TrafficLightTimeDistribution;

import Tools.ConsoleColors;
import Tools.Constants;

public class TimeDistribution {

    private double north_south;
    private double east_west;
    private double phase_time;
    private double min_time;
    private double changing_execution_time;


    public TimeDistribution(double time) {
        phase_time = time;
        min_time = Constants.TRAFFIC_LIGHT_MIN_DISTRIBUTION;
        setDefaultDistribution();
        changing_execution_time = Constants.TRAFFIC_LIGHT_CHANGING_TIME;
    }

    public void addTimeToEastWestRoute() {
        if (north_south > min_time) {
            east_west++;
            north_south--;
        }
    }

    public void addTimeToNorthSouthRoute() {
        if (east_west > min_time) {
            north_south++;
            east_west--;
        }
    }

    public void setEastWest(double time) {
        if (time > phase_time || time < min_time) {
            System.err.println("Set north-south new time failed...");
        } else {
            east_west = time;
            north_south = phase_time - east_west;
        }
    }

    public void setNorthSouth(double time) {
        if (time > phase_time || time < min_time) {
            System.err.println("Set north-south new time failed...");
        } else {
            north_south = time;
            east_west = phase_time - north_south;
        }
    }

    public void setDefaultDistribution() {
        east_west = phase_time / 2;
        north_south = phase_time / 2;
    }

    public double getNorthSouth() {
        return north_south;
    }

    public double getEastWest() {
        return east_west;
    }

    public double getMinTime() {
        return min_time;
    }

    public double getMaxTime() {
        return phase_time - min_time;
    }

    public double getPhaseTime() {
        return phase_time;
    }

    public void setPhaseTime(double time) {
        phase_time = time;
        setDefaultDistribution();
        min_time = (int) Math.ceil(phase_time / 2 / 2);
    }


    public double getChangingExecutionTime() {
        return changing_execution_time;
    }

    public void printTimeDistributionAfterChange() {
        System.out.println(ConsoleColors.GREEN + "north-south: " + north_south + ", east-west: " + east_west + ConsoleColors.RESET);
    }
}
