package SystemSTL.TrafficComputation;

import Objects.Conditions.Conditions;
import SystemSTL.TrafficComputation.Lane.LaneComputation;
import SystemSTL.TrafficComputation.Lane.LaneInfo;
import Tools.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class calculate changes of vehicles on selected directions.
 */
public class TrafficExecutor extends Thread {

    private ExecutorService executor;

    private LaneComputation lane_computation_first_dir_1;
    private LaneComputation lane_computation_first_dir_2;
    private LaneComputation lane_computation_second_dir_1;
    private LaneComputation lane_computation_second_dir_2;

    private Conditions conditions;
    private String executor_name;

    /**
     * This constructor function create LaneComputation objects for chosen directions.
     * This object will start compute the changes of vehicles.
     *
     * @param first_dir_1  - first crossroad direction 1
     * @param first_dir_2  - first crossroad direction 2
     * @param second_dir_1 - second crossroad direction 1
     * @param second_dir_2 - second crossroad direction 2
     */
    public TrafficExecutor(LaneInfo first_dir_1, LaneInfo first_dir_2, LaneInfo second_dir_1, LaneInfo second_dir_2) {
        executor = Executors.newFixedThreadPool(4);

        lane_computation_first_dir_1 = new LaneComputation(first_dir_1);
        lane_computation_first_dir_2 = new LaneComputation(first_dir_2);
        lane_computation_second_dir_1 = new LaneComputation(second_dir_1);
        lane_computation_second_dir_2 = new LaneComputation(second_dir_2);

    }

    /**
     * This function sets the actual conditions of road to executors.
     * @param conditions
     */
    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
        if (this.getName().toLowerCase().equals(Constants.DIRECTION_NAME_EAST_WEST.toLowerCase())) {
            lane_computation_first_dir_2.setNextLaneInfo(conditions.getLaneInfoWestSecondCrossroad());
            lane_computation_second_dir_1.setNextLaneInfo(conditions.getLaneInfoEastFirstCrossroad());
        }
    }


    @Override
    public void run() {
        updateTrafficExecutors();
    }

    /**
     * This function stop traffic executors.
     */
    public void stopTrafficExecutor() {
        lane_computation_first_dir_1.stopLaneComputation();
        lane_computation_first_dir_2.stopLaneComputation();
        lane_computation_second_dir_1.stopLaneComputation();
        lane_computation_second_dir_2.stopLaneComputation();
    }

    /**
     * This function executes the lane computation for traffic executors.
     */
    private void updateTrafficExecutors() {
        System.out.println(this.getName() + " start");


        executor.execute(lane_computation_first_dir_1);
        executor.execute(lane_computation_first_dir_2);
        executor.execute(lane_computation_second_dir_1);
        executor.execute(lane_computation_second_dir_2);

        executor.shutdown();


        //this thread sets moving mode for each executor
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!executor.isTerminated() && !conditions.isAllCarsPassed()) {
                    if (executor_name.toLowerCase().equals(Constants.DIRECTION_NAME_EAST_WEST.toLowerCase())) {
                        setMovingMode(conditions.isEastWestActive());
                    } else {
                        setMovingMode(conditions.isNorthSouthActive());
                    }
                }
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(this.getName() + " finished");
    }

    /**
     * This function sets name of chosen executor.
     * @param name - north-south/east-west
     */
    public void setExecutorName(String name) {
        this.setName(name);
        this.executor_name = name;
        createLabels();
    }

    /**
     * This function set moving mode of chosen executor.
     * @param mode - go/stop
     */
    private void setMovingMode(boolean mode) {
        lane_computation_first_dir_1.setMovingMode(mode);
        lane_computation_first_dir_2.setMovingMode(mode);
        lane_computation_second_dir_1.setMovingMode(mode);
        lane_computation_second_dir_2.setMovingMode(mode);

    }

    /**
     * This function creates labels for directions.
     */
    private void createLabels() {
        String first = Constants.CROSSROAD_NAME_FIRST;
        String second = Constants.CROSSROAD_NAME_SECOND;

        if (this.getName().toLowerCase().equals(Constants.DIRECTION_NAME_EAST_WEST.toLowerCase())) {
            lane_computation_first_dir_1.setName(first + " " + Constants.DIRECTION_NAME_EAST);
            lane_computation_first_dir_2.setName(first + " " + Constants.DIRECTION_NAME_WEST);
            lane_computation_second_dir_1.setName(second + " " + Constants.DIRECTION_NAME_EAST);
            lane_computation_second_dir_2.setName(second + " " + Constants.DIRECTION_NAME_WEST);
        } else {
            lane_computation_first_dir_1.setName(first + " " + Constants.DIRECTION_NAME_NORTH);
            lane_computation_first_dir_2.setName(first + " " + Constants.DIRECTION_NAME_SOUTH);
            lane_computation_second_dir_1.setName(second + " " + Constants.DIRECTION_NAME_NORTH);
            lane_computation_second_dir_2.setName(second + " " + Constants.DIRECTION_NAME_SOUTH);
        }
    }
}
