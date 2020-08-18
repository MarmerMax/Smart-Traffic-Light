package SystemSTL;

import Objects.Conditions.Conditions;
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

    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }

    @Override
    public void run() {

        System.out.println(this.getName() + " start");

//        setMovingMode(moving_mode);
//        setStopComputation(false);

//        executor.shutdown();

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

        System.out.println("ALL CARS PASSED");


//        executor.shutdown();
//        if (moving_mode) {
//            try {
//                executor.awaitTermination(100, TimeUnit.MILLISECONDS);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            setStopComputation(true);
//        }
//            executor.shutdownNow();
//        } else {
//            executor.shutdown();
//        }

        System.out.println(this.getName() + " finished");
    }

    public void setExecutorName(String name) {
        this.setName(name);
        this.executor_name = name;
        createLabels();
    }

    private void setMovingMode(boolean mode) {
        lane_computation_first_dir_1.setMovingMode(mode);
        lane_computation_first_dir_2.setMovingMode(mode);
        lane_computation_second_dir_1.setMovingMode(mode);
        lane_computation_second_dir_2.setMovingMode(mode);

    }

    private void createLabels() {
        if (this.getName().toLowerCase().equals(Constants.DIRECTION_NAME_EAST_WEST.toLowerCase())) {
            lane_computation_first_dir_1.setName("east-west first dir 1");
            lane_computation_first_dir_2.setName("east-west first dir 2");
            lane_computation_second_dir_1.setName("east-west second dir 1");
            lane_computation_second_dir_2.setName("east-west second dir 2");
        } else {
            lane_computation_first_dir_1.setName("north-south first dir 1");
            lane_computation_first_dir_2.setName("north-south first dir 2");
            lane_computation_second_dir_1.setName("north-south second dir 1");
            lane_computation_second_dir_2.setName("north-south second dir 2");
        }
    }
}
