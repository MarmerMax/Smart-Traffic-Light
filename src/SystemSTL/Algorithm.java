package SystemSTL;

import Objects.Car.Car;
import Objects.Conditions.Conditions;
import Objects.CrossroadInfo.CrossroadInfo;
import Tools.Constants;
import Tools.Utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Algorithm {

    private Conditions conditions;

    private double initial_duration;
    private double actual_duration;
    private boolean is_finished;

    private boolean is_east_west_high_priority;
    private boolean is_north_south_high_priority;
    private int cars_ratio;

    private ArrayList<LaneInfo> cars_crossroad_1;
    private ArrayList<LaneInfo> cars_crossroad_2;

    private ArrayList<CarInfo> passed_cars_first_crossroad_south;


    public Algorithm(Conditions conditions) {
        this.conditions = conditions;
        is_finished = false;
        createLanesInfo();
        checkInitialStateDuration();
    }

    public void start() {
        System.err.println("[START]");

        double time = 0;
        double changing_time = 0;

        //function to choose first active direction by priority

        //must to be in thread
        while (!isAllCarsPassed()) {

            checkPriority();
            updateTrafficLightsTimeDistributions();//wrong place

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //main road active
            if (conditions.isEastWestActive()) {

                System.out.println("EAST AND WEST ACTIVE");

                //start moving
                updateEastWestLaneInfo(true);

                if (conditions.getEastWestTimeDistribution() < time) {
                    //stop moving
                    updateEastWestLaneInfo(false);

                    conditions.changeTrafficLightState();
                }
                time++;
            }

            //others road active
            else if (conditions.isNorthSouthActive()) {

                System.out.println("NORTH AND SOUTH ACTIVE");

                //start moving
                updateNorthSouthLaneInfo(true);

                if (conditions.getNorthSouthTimeDistribution() < time) {
                    //stop moving
                    updateNorthSouthLaneInfo(false);

                    conditions.changeTrafficLightState();
                }
                time++;
            }

            //all road are stop
            else {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                time = 0;
                System.out.println("ALL STOPS");
                if (changing_time < conditions.getChangingLightsTime()) {
                    changing_time++;
                } else {
                    changing_time = 0;
                    conditions.changeTrafficLightState();
                }
            }
        }
        is_finished = true;
    }


    private void updateNorthSouthLaneInfo(boolean moving_mode) {

    }


    private void updateEastWestLaneInfo(boolean moving_mode) {

//        LaneComputation lane_computation_first_north = new LaneComputation(cars_crossroad_1.get(0));
//        lane_computation_first_north.setMovingMode(moving_mode);
//
//        lane_computation_first_north.start();
//
//        try {
//            lane_computation_first_north.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        ExecutorService executor = Executors.newFixedThreadPool(4);
//        double time = 0;
//        LaneComputation lane_computation_first_north = new LaneComputation(cars_crossroad_1.get(0));
//        LaneComputation lane_computation_first_south = new LaneComputation(cars_crossroad_1.get(2));
//        LaneComputation lane_computation_second_north = new LaneComputation(cars_crossroad_2.get(0));
//        LaneComputation lane_computation_second_south = new LaneComputation(cars_crossroad_2.get(2));
//
//        while (time < 1000) {
//            time += 10;
//
//            executor.execute(lane_computation_first_north);
//            executor.execute(lane_computation_first_south);
//            executor.execute(lane_computation_second_north);
//            executor.execute(lane_computation_second_south);
//
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void checkInitialStateDuration() {

        double max_time = 0;
        LaneComputation lane_computation;

        for (LaneInfo lane_info : cars_crossroad_1) {
            lane_computation = new LaneComputation(lane_info);

            if (max_time < lane_computation.getInitialTime()) {
                max_time = lane_computation.getInitialTime();
            }
        }

        for (LaneInfo lane_info : cars_crossroad_2) {
            lane_computation = new LaneComputation(lane_info);

            if (max_time < lane_computation.getInitialTime()) {
                max_time = lane_computation.getInitialTime();
            }
        }

        //add phases for opposite lanes
        int phase_amount = (int) (max_time / (Constants.CROSSROAD_PHASE_TIME / 2)) + 1;

        initial_duration = max_time + (phase_amount * (Constants.CROSSROAD_PHASE_TIME / 2));
        System.out.println("initial time for passed all cars: " + initial_duration);
    }

    private boolean isAllCarsPassed() {
        for (LaneInfo lane_info : cars_crossroad_1) {
            if (lane_info.getCarsInLane().size() > 0) {
                return false;
            }
        }
        for (LaneInfo lane_info : cars_crossroad_2) {
            if (lane_info.getCarsInLane().size() > 0) {
                return false;
            }
        }
        return true;
    }

    private void createLanesInfo() {
        cars_crossroad_1 = new ArrayList<>();
        CrossroadInfo first_crossroad = conditions.getCrossroadInfo1();
        createLanesPerCrossroad(cars_crossroad_1, first_crossroad);

        cars_crossroad_2 = new ArrayList<>();
        CrossroadInfo second_crossroad = conditions.getCrossroadInfo2();
        createLanesPerCrossroad(cars_crossroad_2, second_crossroad);

        checkPriority();
    }

    private void createLanesPerCrossroad(ArrayList<LaneInfo> cars, CrossroadInfo actual_crossroad) {
        cars.add(new LaneInfo(actual_crossroad.getNorth().getCarsCount(), actual_crossroad.getNorth().getSpeedLimit()));
        cars.add(new LaneInfo(actual_crossroad.getEast().getCarsCount(), actual_crossroad.getEast().getSpeedLimit()));
        cars.add(new LaneInfo(actual_crossroad.getSouth().getCarsCount(), actual_crossroad.getSouth().getSpeedLimit()));
        cars.add(new LaneInfo(actual_crossroad.getWest().getCarsCount(), actual_crossroad.getWest().getSpeedLimit()));
    }

    //find priority direction
    private void checkPriority() {
        double north_south_count = calculateCarsInRoutes(0, 2);
        double east_west_count = calculateCarsInRoutes(1, 3);

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

    //add time to priorities directions
    private synchronized void updateTrafficLightsTimeDistributions() {
        if (is_north_south_high_priority) {
            for (int i = 0; i < cars_ratio; i++) {
                conditions.addTimeToNorthSouthRoute();
            }
        } else if (is_east_west_high_priority) {
            for (int i = 0; i < cars_ratio; i++) {
                conditions.addTimeToEastWestRoure();
            }
        } else {
            conditions.setDefaultTimeDistribution();
        }
    }

    private double calculateCarsInRoutes(int ind_1, int ind_2) {
        int first_crossroad_amount = cars_crossroad_1.get(ind_1).getCarsInLane().size() +
                cars_crossroad_1.get(ind_2).getCarsInLane().size();

        int second_crossroad_amount = cars_crossroad_2.get(ind_1).getCarsInLane().size() +
                cars_crossroad_2.get(ind_2).getCarsInLane().size();

        return first_crossroad_amount + second_crossroad_amount;
    }

    private Queue<Car> copyQueue(Queue<Car> cars) {
        return new ArrayDeque<>(cars);
    }

    public LaneInfo getCarsCountNorthCrossroad_1() {
        return cars_crossroad_1.get(0);
    }

    public LaneInfo getCarsCountEastCrossroad_1() {
        return cars_crossroad_1.get(1);
    }

    public LaneInfo getCarsCountSouthCrossroad_1() {
        return cars_crossroad_1.get(2);
    }

    public LaneInfo getCarsCountWestCrossroad_1() {
        return cars_crossroad_1.get(3);
    }

    public LaneInfo getCarsCountNorthCrossroad_2() {
        return cars_crossroad_2.get(0);
    }

    public LaneInfo getCarsCountEastCrossroad_2() {
        return cars_crossroad_2.get(1);
    }

    public LaneInfo getCarsCountSouthCrossroad_2() {
        return cars_crossroad_2.get(2);
    }

    public LaneInfo getCarsCountWestCrossroad_2() {
        return cars_crossroad_2.get(3);
    }

    public double getInitialDuration() {
        return initial_duration;
    }

    public double getActualDuration() {
        return actual_duration;
    }

    public boolean getIsFinished() {
        return is_finished;
    }
}
