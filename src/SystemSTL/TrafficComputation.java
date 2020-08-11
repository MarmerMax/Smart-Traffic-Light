package SystemSTL;

import Objects.Conditions.Conditions;
import Tools.Constants;
import Tools.Formulas;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class TrafficComputation extends Thread {

    private Conditions conditions;
    private double initial_duration;
    private boolean is_finished;

    public TrafficComputation(Conditions conditions) {
        this.conditions = conditions;
        initial_duration = calculateInitialStateDuration();
    }

    @Override
    public void run() {
        updateTrafficState();
    }

    public void updateTrafficState() {

        while (!conditions.isAllCarsPassed()) {

            if (conditions.isEastWestActive()) {

                updateEastWestLaneInfo(true);

            } else if (conditions.isNorthSouthActive()) {

                updateNorthSouthLaneInfo(true);

            } else {

                updateEastWestLaneInfo(false);
                updateNorthSouthLaneInfo(false);
                System.out.println("ALL STOPS");

            }
        }
    }

    /**
     * This function update the lanes information of North-South direction on two crossroads. (Update traffic)
     *
     * @param moving_mode - start/stop mode
     */
    private void updateNorthSouthLaneInfo(boolean moving_mode) {

        if (moving_mode) {
//            System.out.println("north-south moving");
        }

        LaneInfo first_north = conditions.getCarsInfoNorthCrossroad_1();
        LaneInfo first_south = conditions.getCarsInfoSouthCrossroad_1();
        LaneInfo second_north = conditions.getCarsInfoNorthCrossroad_2();
        LaneInfo second_south = conditions.getCarsInfoSouthCrossroad_2();

        computeTraffic(moving_mode, first_north, first_south, second_north, second_south);

    }

    /**
     * This function update the lanes information of East-West direction on two crossroads. (Update traffic)
     *
     * @param moving_mode - start/stop mode
     */
    private void updateEastWestLaneInfo(boolean moving_mode) {

        if (moving_mode) {
//            System.out.println("east-west moving");
        }

        LaneInfo first_east = conditions.getCarsInfoEastCrossroad_1();
        LaneInfo first_west = conditions.getCarsInfoWestCrossroad_1();
        LaneInfo second_east = conditions.getCarsInfoEastCrossroad_2();
        LaneInfo second_west = conditions.getCarsInfoWestCrossroad_2();

        computeTraffic(moving_mode, first_east, first_west, second_east, second_west);
    }

    /**
     * This function calculate changes of vehicles on selected directions.
     *
     * @param moving_mode  - start/stop mode
     * @param first_dir_1  - first crossroad direction 1
     * @param first_dir_2  - first crossroad direction 2
     * @param second_dir_1 - second crossroad direction 1
     * @param second_dir_2 - second crossroad direction 2
     */
    private void computeTraffic(
            boolean moving_mode,
            LaneInfo first_dir_1,
            LaneInfo first_dir_2,
            LaneInfo second_dir_1,
            LaneInfo second_dir_2) {

        LaneComputation lane_computation_first_dir_1 = new LaneComputation(first_dir_1);
        LaneComputation lane_computation_first_dir_2 = new LaneComputation(first_dir_2);
        LaneComputation lane_computation_second_dir_1 = new LaneComputation(second_dir_1);
        LaneComputation lane_computation_second_dir_2 = new LaneComputation(second_dir_2);

        lane_computation_first_dir_1.setMovingMode(moving_mode);
        lane_computation_first_dir_2.setMovingMode(moving_mode);
        lane_computation_second_dir_1.setMovingMode(moving_mode);
        lane_computation_second_dir_2.setMovingMode(moving_mode);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.execute(lane_computation_first_dir_1);
        executor.execute(lane_computation_first_dir_2);
        executor.execute(lane_computation_second_dir_1);
        executor.execute(lane_computation_second_dir_2);

//        executor.shutdown();
//        executor.shutdownNow();
//        Thread.currentThread().interrupt();

        //needed for stopping executor manually
        if (moving_mode) {
            try {
                executor.awaitTermination(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lane_computation_first_dir_1.stopComputation();
            lane_computation_first_dir_2.stopComputation();
            lane_computation_second_dir_1.stopComputation();
            lane_computation_second_dir_2.stopComputation();

            executor.shutdownNow();
        } else {
            executor.shutdown();
        }


    }

    /**
     * This function calculates the duration of the baseline without using an intelligent algorithm.
     * The duration tells how long it will take for all vehicles to pass the intersection.
     */
    private double calculateInitialStateDuration() {
        double max_time = 0;

        double max_crossroad_1 = getMaxInitialTimeOfCrossroad(conditions.getCarsInfoFirstCrossroad());
        double max_crossroad_2 = getMaxInitialTimeOfCrossroad(conditions.getCarsInfoSecondCrossroad());

        max_time = Math.max(max_crossroad_1, max_crossroad_2);

        //add phases for opposite lanes
        int phase_amount = (int) (max_time / (Constants.CROSSROAD_PHASE_TIME / 2)) + 1;

        return max_time + (phase_amount * (Constants.CROSSROAD_PHASE_TIME / 2));
    }


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
    //https://www.diva-portal.org/smash/get/diva2:1214166/FULLTEXT01.pdf
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
