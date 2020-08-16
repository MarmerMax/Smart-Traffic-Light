package SystemSTL;

import Objects.Conditions.Conditions;
import Tools.Constants;
import Tools.Utils;

/**
 * This class is heard of simulation.
 * Here, the calculations of the movement of vehicles and the change of traffic lights take place.
 */
public class Algorithm extends Thread {

    private Conditions conditions;

    private boolean is_east_west_high_priority;
    private boolean is_north_south_high_priority;

    private int cars_ratio;

    /**
     * Algorithm constructor. Calculates the initial travel times for cars without a smart algorithm.
     *
     * @param conditions - conditions of actual roads.
     */
    public Algorithm(Conditions conditions) {
        this.conditions = conditions;
    }

    @Override
    public void run() {
        updateTrafficLightsTimeDistributions();
    }

    /**
     * This function adds time to the priority direction depending on the ratio.
     */
    public void updateTrafficLightsTimeDistributions() {
        while (!conditions.isAllCarsPassed()) {
            checkPriority();

            if (is_north_south_high_priority) {
                for (int i = 0; i < cars_ratio; i++) {
                    conditions.addTimeToNorthSouthRoute();
                }
            } else if (is_east_west_high_priority) {
                for (int i = 0; i < cars_ratio; i++) {
                    conditions.addTimeToEastWestRoute();
                }
            } else {
                conditions.setDefaultTimeDistribution();
            }
        }
    }

    /**
     * This function check priority for traffic lights distributions.
     * Where there are more cars, the higher the priority.
     */
    private void checkPriority() {
        double north_south_count = calculateCarsInRoutes(Constants.NORTH_DIRECTION, Constants.SOUTH_DIRECTION);
        double east_west_count = calculateCarsInRoutes(Constants.EAST_DIRECTION, Constants.WEST_DIRECTION);

        cars_ratio = 1;

        if (east_west_count > north_south_count) {
            is_east_west_high_priority = true;
            cars_ratio = (int) Utils.findRatio(north_south_count, east_west_count);
        } else if (east_west_count < north_south_count) {
            is_north_south_high_priority = true;
            cars_ratio = (int) Utils.findRatio(east_west_count, north_south_count);
        } else {
            is_east_west_high_priority = false;
            is_north_south_high_priority = false;
        }
    }

    /**
     * This function calculate count of two specific directions.
     *
     * @param dir_1 - direction 1
     * @param dir_2 - direction 2
     * @return count of cars in both direction
     */
    private double calculateCarsInRoutes(int dir_1, int dir_2) {

        int first_crossroad_amount = conditions.getCarsInfoFirstCrossroad().get(dir_1).getCarsInLane().size() +
                conditions.getCarsInfoFirstCrossroad().get(dir_2).getCarsInLane().size();

        int second_crossroad_amount = conditions.getCarsInfoSecondCrossroad().get(dir_1).getCarsInLane().size() +
                conditions.getCarsInfoSecondCrossroad().get(dir_2).getCarsInLane().size();

        return first_crossroad_amount + second_crossroad_amount;
    }
}
