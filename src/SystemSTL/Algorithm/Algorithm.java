package SystemSTL.Algorithm;

import SystemSTL.AlgorithmSTL.*;
import Objects.Conditions.Conditions;
import SystemSTL.AlgorithmSTL.AlgorithmCore.DFBnB;
import Tools.ConsoleColors;
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

    private double cars_ratio;
    private volatile boolean isStopped = false;

    private AlgorithmSTL smart_algorithm;
    private AlgorithmRules algorithm_conditions;

    /**
     * Algorithm constructor. Calculates the initial travel times for cars without a smart algorithm.
     *
     * @param conditions - conditions of actual roads.
     */
    public Algorithm(Conditions conditions) {
        this.conditions = conditions;

        algorithm_conditions = new AlgorithmRules(conditions);
//        smart_algorithm = new Astar();
        smart_algorithm = new DFBnB();
        smart_algorithm.setTrafficConditions(algorithm_conditions);
    }

    @Override
    public void run() {
        updateTrafficLightsTimeDistributions();
        if (isStopped) {
            System.out.println(ConsoleColors.RED_BOLD + "Algorithm was stopped!" + ConsoleColors.RESET);
        }
    }

    public void stopAlgorithm() {
        isStopped = true;
        smart_algorithm.stopAlgorithmSTL();
    }

    /**
     * This function updates time of traffic lights due to the priority direction or due to smart algorithm.
     */
    private void updateTrafficLightsTimeDistributions() {

        smart_algorithm.start();

        int actual_duration = 0;
        boolean better_distribution_selected = false;

        while (!conditions.isAllCarsPassed() && !isStopped) {

            //The algorithm works like a priority algorithm until a smart algorithm finds a better distribution.
            //After the smart algorithm has found the better distribution, the conditions must set the found
            //distribution so that the traffic lights will work according to its distribution.
            //The better distribution has a finite time. If during this time not all cars will pass the crossroad,
            //then algorithm will again work as a priority algorithm.
            if (this.conditions.getBetterDistribution().size() != 0 && !better_distribution_selected) {
                better_distribution_selected = true;

                double better_duration = this.conditions.getAlgorithmDuration() - actual_duration;

                try {
                    System.out.println("Smart algorithm will sleep " + (int) better_duration + " seconds");
//                    Thread.sleep((int) better_duration * 1000);

                    while (this.conditions.getBetterDistribution().size() != 0) {
                        Thread.sleep(1000);
                    }

                    double last_phase_time = Constants.TRAFFIC_LIGHT_PHASE_TIME + Constants.TRAFFIC_LIGHT_CHANGING_TIME * 3 * 2;
                    Thread.sleep((int) last_phase_time * 1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {

                if (actual_duration % 5 == 0) {
                    System.out.println(ConsoleColors.YELLOW_BOLD + "Priority algorithm" + ConsoleColors.RESET);

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

            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }

            actual_duration++;
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

        if (north_south_count != 0 && east_west_count != 0) {
            if (east_west_count > north_south_count) {
                is_east_west_high_priority = true;
                is_north_south_high_priority = false;
                cars_ratio = Utils.findRatio(north_south_count, east_west_count);
            } else if (east_west_count < north_south_count) {
                is_north_south_high_priority = true;
                is_east_west_high_priority = false;
                cars_ratio = Utils.findRatio(east_west_count, north_south_count);
            } else {
                is_east_west_high_priority = false;
                is_north_south_high_priority = false;
            }
        } else {
            if (east_west_count > north_south_count) {
                is_east_west_high_priority = true;
                is_north_south_high_priority = false;
                cars_ratio = 2;
            } else if (east_west_count < north_south_count) {
                is_north_south_high_priority = true;
                is_east_west_high_priority = false;
                cars_ratio = 2;
            } else {
                is_east_west_high_priority = false;
                is_north_south_high_priority = false;
            }
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

        int first_crossroad_amount = conditions.getLanesInfoFirstCrossroad().get(dir_1).getCarsInLane().size() +
                conditions.getLanesInfoFirstCrossroad().get(dir_2).getCarsInLane().size();

        int second_crossroad_amount = conditions.getLanesInfoSecondCrossroad().get(dir_1).getCarsInLane().size() +
                conditions.getLanesInfoSecondCrossroad().get(dir_2).getCarsInLane().size();

        return first_crossroad_amount + second_crossroad_amount;
    }
}
