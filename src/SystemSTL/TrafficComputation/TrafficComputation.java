package SystemSTL.TrafficComputation;

import Objects.Conditions.Conditions;
import SystemSTL.TrafficComputation.Lane.LaneInfo;
import SystemSTL.TrafficExecutor;
import Tools.Constants;
import Tools.Utils;

/**
 * This class creates and start the traffic executors for both crossroads and all directions.
 */
public class TrafficComputation extends Thread {

    private Conditions conditions;

    private TrafficExecutor east_west_executor;
    private TrafficExecutor north_south_executor;

    public TrafficComputation(Conditions conditions) {
        this.conditions = conditions;

        double initial_duration = Utils.calculateInitialDuration(conditions);
        this.conditions.setInitialDuration(initial_duration);

        createEastWestExecutor();
        createNorthSouthExecutor();
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
}
