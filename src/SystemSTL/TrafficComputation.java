package SystemSTL;

import Objects.Conditions.Conditions;
import Tools.Constants;
import Tools.Formulas;

import java.util.ArrayList;

/**
 * This class creates and start the traffic executors for both crossroads and all directions.
 */
public class TrafficComputation extends Thread {

    private Conditions conditions;
    private double initial_duration;

    private TrafficExecutor east_west_executor;
    private TrafficExecutor north_south_executor;

    public TrafficComputation(Conditions conditions) {
        this.conditions = conditions;

        createEastWestExecutor();
        createNorthSouthExecutor();

        initial_duration = calculateInitialStateDuration();
    }

    @Override
    public void run() {
        updateTrafficState();
    }

    /**
     * This function is responsible for starting executors.
     */
    public void updateTrafficState() {

        east_west_executor.start();
        north_south_executor.start();

        try {
            east_west_executor.join();
            north_south_executor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ALL CARS IS PASSED");
    }

    /**
     * This function creates an East-West traffic executor at both intersections.
     */
    private void createEastWestExecutor() {

        LaneInfo first_east = conditions.getCarsInfoEastCrossroad_1();
        LaneInfo first_west = conditions.getCarsInfoWestCrossroad_1();
        LaneInfo second_east = conditions.getCarsInfoEastCrossroad_2();
        LaneInfo second_west = conditions.getCarsInfoWestCrossroad_2();

        east_west_executor = new TrafficExecutor(first_east, first_west, second_east, second_west);
        east_west_executor.setExecutorName(Constants.DIRECTION_NAME_EAST_WEST);
        east_west_executor.setConditions(conditions);
    }

    /**
     * This function creates an North-South traffic executor at both intersections.
     */
    private void createNorthSouthExecutor() {

        LaneInfo first_north = conditions.getCarsInfoNorthCrossroad_1();
        LaneInfo first_south = conditions.getCarsInfoSouthCrossroad_1();
        LaneInfo second_north = conditions.getCarsInfoNorthCrossroad_2();
        LaneInfo second_south = conditions.getCarsInfoSouthCrossroad_2();

        north_south_executor = new TrafficExecutor(first_north, first_south, second_north, second_south);
        north_south_executor.setExecutorName(Constants.DIRECTION_NAME_NORTH_SOUTH);
        north_south_executor.setConditions(conditions);
    }

    /**
     * This function calculates the duration of the baseline without using an smart algorithm.
     * The duration tells how long it will take for all vehicles to pass the intersection.
     */
    private double calculateInitialStateDuration() {
        double max_time;

        double max_crossroad_1 = getMaxInitialTimeOfCrossroad(conditions.getCarsInfoFirstCrossroad());
        double max_crossroad_2 = getMaxInitialTimeOfCrossroad(conditions.getCarsInfoSecondCrossroad());

        max_time = Math.max(max_crossroad_1, max_crossroad_2);

        //add phases for opposite lanes
        int phase_amount = (int) (max_time / (Constants.CROSSROAD_PHASE_TIME / 2)) + 1;

        return max_time + (phase_amount * (Constants.CROSSROAD_PHASE_TIME / 2));
    }

    /**
     * @param crossroad_info
     * @return
     */
    private double getMaxInitialTimeOfCrossroad(ArrayList<LaneInfo> crossroad_info) {
        double temp_max = 0;
        for (LaneInfo lane_info : crossroad_info) {

            double lane_time = computeInitialTime(lane_info);
            if (temp_max < lane_time) {
                temp_max = lane_time;
            }
        }

        return temp_max;
    }

    /**
     * This function calculate the time for passing the crossroad for specific lane.
     *
     * @param lane_info - lane to check
     * @return required moving time
     */
    // https://www.diva-portal.org/smash/get/diva2:1214166/FULLTEXT01.pdf
    private double computeInitialTime(LaneInfo lane_info) {
        double t_start = Formulas.T_start(lane_info.getCarsInLane().size());
        double t_move = Formulas.T_move(lane_info.getLastCar().getMaxSpeed(),
                lane_info.getLastCar().getAcceleration(),
                lane_info.getLastCarDistance());

        //It is 0 if the opposite vehicle is not going to turn around.
        double t_opposite = Formulas.T_opossite(0);

        return Formulas.T_common(t_start, t_move, t_opposite);
    }

    /**
     * This function return duration of initial state without smart algorithm.
     *
     * @return initial duration
     */
    public double getInitialDuration() {
        return initial_duration;
    }
}
