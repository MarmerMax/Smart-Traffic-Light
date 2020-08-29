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

        LaneInfo first_east = conditions.getLaneInfoEastFirstCrossroad();
        LaneInfo first_west = conditions.getLaneInfoWestFirstCrossroad();
        LaneInfo second_east = conditions.getLaneInfoEastSecondCrossroad();
        LaneInfo second_west = conditions.getLaneInfoWestSecondCrossroad();

        east_west_executor = new TrafficExecutor(first_east, first_west, second_east, second_west);
        east_west_executor.setExecutorName(Constants.DIRECTION_NAME_EAST_WEST);
        east_west_executor.setConditions(conditions);
    }

    /**
     * This function creates an North-South traffic executor at both intersections.
     */
    private void createNorthSouthExecutor() {

        LaneInfo first_north = conditions.getLaneInfoNorthFirstCrossroad();
        LaneInfo first_south = conditions.getLaneInfoSouthFirstCrossroad();
        LaneInfo second_north = conditions.getLaneInfoNorthSecondCrossroad();
        LaneInfo second_south = conditions.getLaneInfoSouthSecondCrossroad();

        north_south_executor = new TrafficExecutor(first_north, first_south, second_north, second_south);
        north_south_executor.setExecutorName(Constants.DIRECTION_NAME_NORTH_SOUTH);
        north_south_executor.setConditions(conditions);
    }

    /**
     * This function calculates the duration of the baseline without using an smart algorithm.
     * The duration tells how long it will take for all vehicles to pass the intersection.
     */
    //TODO calculate initial time
    private double calculateInitialStateDuration() {

        //find max initial time of roads in each crossroad
        double max_crossroad_1 = getMaxInitialTimeOfCrossroad(conditions.getLanesInfoFirstCrossroad());
        double max_crossroad_2 = getMaxInitialTimeOfCrossroad(conditions.getLanesInfoSecondCrossroad());

        //find max initial time
        double max_time = Math.max(max_crossroad_1, max_crossroad_2);

        //changing time work of traffic lights
        int first_changing_time = (Constants.TRAFFIC_LIGHT_CHANGING_TIME + 1) * 2;
        int next_changing_time = (Constants.TRAFFIC_LIGHT_CHANGING_TIME + 1) * 3;

        //phase time is working time of the traffic lights to th next state
        int phase_time = (int) (Constants.TRAFFIC_LIGHT_PHASE_TIME / 2) + next_changing_time + 2;

        //last phase is time of last traffic light changing
        int last_phase = (int) (max_time % (Constants.TRAFFIC_LIGHT_PHASE_TIME / 2));

        //amount of phases
        int phase_amount = (int) (max_time / (Constants.TRAFFIC_LIGHT_PHASE_TIME / 2));

        if (phase_amount == 0) {
            phase_amount = 1;
        }


        return (phase_amount * phase_time) + first_changing_time + last_phase;
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
//        double t_move = Formulas.T_move(lane_info.getLastCar().getMaxSpeed(),
        double t_move = Formulas.T_move(0,
                lane_info.getLastCar().getCar().getAcceleration(),
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
