package SystemSTL;

import Objects.Car.Car;
import Objects.Conditions.Conditions;
import Tools.Constants;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Algorithm {

    private Conditions conditions;

    private double initial_duration;
    private double actual_duration;

//    private ArrayList<Queue<Car>> cars_crossroad_1;
//    private ArrayList<Queue<Car>> cars_crossroad_2;

    private ArrayList<LaneInfo> cars_crossroad_1;
    private ArrayList<LaneInfo> cars_crossroad_2;


    public Algorithm(Conditions conditions) {
        this.conditions = conditions;
        createCars();
        checkInitialStateDuration();
    }

    private void checkInitialStateDuration() {

        double max_time = 0;
        LaneComputation lane_computation;

        for (LaneInfo lane_info : cars_crossroad_1) {
            lane_computation = new LaneComputation(lane_info);

            if(max_time < lane_computation.getInitialTime()){
                max_time = lane_computation.getInitialTime();
            }
        }

        for (LaneInfo lane_info : cars_crossroad_2) {
            lane_computation = new LaneComputation(lane_info);

            if(max_time < lane_computation.getInitialTime()){
                max_time = lane_computation.getInitialTime();
            }
        }

        //add phases for opposite lanes
        int phase_amount = (int)(max_time / (Constants.CROSSROAD_PHASE_TIME / 2)) + 1;

        initial_duration = max_time + (phase_amount * (Constants.CROSSROAD_PHASE_TIME / 2));
        System.out.println("initial time for passed all cars: " + initial_duration);
    }

    public void start() {
        System.err.println("[START]");
    }

//    private void checkInitialStateDuration() {
//        System.err.println("[INITIAL STATE START]");
//        ArrayList<Queue<Car>> temp_cars_crossroad_1 = new ArrayList<>(cars_crossroad_1);
//        ArrayList<Queue<Car>> temp_cars_crossroad_2 = new ArrayList<>(cars_crossroad_2);
////        temp_cars_crossroad_1.add(copyQueue(cars_crossroad_1.get(0)));
////        temp_cars_crossroad_1.add(copyQueue(cars_crossroad_1.get(1)));
////        temp_cars_crossroad_1.add(copyQueue(cars_crossroad_1.get(2)));
////        temp_cars_crossroad_1.add(copyQueue(cars_crossroad_1.get(3)));
//
//        Conditions temp_conditions = new Conditions(conditions);
//
//        int first_crossroad_total_cars =
//                temp_conditions.getCrossroadInfo1().getEast().getCarsCount() +
//                        temp_conditions.getCrossroadInfo1().getWest().getCarsCount() +
//                        temp_conditions.getCrossroadInfo1().getNorth().getCarsCount() +
//                        temp_conditions.getCrossroadInfo1().getSouth().getCarsCount();
//
//        int second_crossroad_total_cars =
//                temp_conditions.getCrossroadInfo1().getEast().getCarsCount() +
//                        temp_conditions.getCrossroadInfo1().getWest().getCarsCount() +
//                        temp_conditions.getCrossroadInfo1().getNorth().getCarsCount() +
//                        temp_conditions.getCrossroadInfo1().getSouth().getCarsCount();
//
//        int passed_first_crossroad = 0;
//        int passed_second_crossroad = 0;
//
//        int total_time = 0;
//        int round_time = 0;
//        int changing_time = 0;
//
//        while (first_crossroad_total_cars > passed_first_crossroad || second_crossroad_total_cars > passed_second_crossroad) {
//            if (temp_conditions.getCrossroadInfo1().getCrossroad().isEastWestActive()) {
//                System.out.println("east-west: " + round_time + " ->  " + passed_first_crossroad + " , " + passed_second_crossroad);
//                //calculate cars amount per second and subst it
//                int[] cars_passed = calculatePassedCars(
//                        temp_conditions.getCrossroadInfo1().getEast().getActualSpeed(),
//                        temp_conditions.getCrossroadInfo1().getWest().getActualSpeed(),
//                        temp_conditions.getCrossroadInfo2().getEast().getActualSpeed(),
//                        temp_conditions.getCrossroadInfo2().getWest().getActualSpeed(),
//                        temp_cars_crossroad_1.get(1),
//                        temp_cars_crossroad_1.get(3),
//                        temp_cars_crossroad_2.get(1),
//                        temp_cars_crossroad_2.get(3));
//
//                passed_first_crossroad += cars_passed[0];
//                passed_second_crossroad += cars_passed[1];
//
//                //after calculation how many cars was passed crossroad check if crossroad has to change state
//                if (temp_conditions.getCrossroadInfo1().getCrossroad().getTimeDistribution().getEastWest() > round_time
//                        && temp_conditions.getCrossroadInfo2().getCrossroad().getTimeDistribution().getEastWest() > round_time) {
//                    round_time++;
//                } else {
//                    round_time = 0;
//                    temp_conditions.getCrossroadInfo1().getCrossroad().changeTrafficLightStateOnCrossroad();
//                    temp_conditions.getCrossroadInfo2().getCrossroad().changeTrafficLightStateOnCrossroad();
//                    System.out.println("1 north: " + temp_conditions.getCrossroadInfo1().getCrossroad().getNorthTrafficLight().getTrafficLightStateName() +
//                            ", 1 east: " + temp_conditions.getCrossroadInfo1().getCrossroad().getEastTrafficLight().getTrafficLightStateName() +
//                            ", 1 south: " + temp_conditions.getCrossroadInfo1().getCrossroad().getSouthTrafficLight().getTrafficLightStateName() +
//                            ", 1 west: " + temp_conditions.getCrossroadInfo1().getCrossroad().getWestTrafficLight().getTrafficLightStateName());
//
//
//                    System.out.println("2 north: " + temp_conditions.getCrossroadInfo2().getCrossroad().getNorthTrafficLight().getTrafficLightStateName() +
//                            ", 2 east: " + temp_conditions.getCrossroadInfo2().getCrossroad().getEastTrafficLight().getTrafficLightStateName() +
//                            ", 2 south: " + temp_conditions.getCrossroadInfo2().getCrossroad().getSouthTrafficLight().getTrafficLightStateName() +
//                            ", 2 west: " + temp_conditions.getCrossroadInfo2().getCrossroad().getWestTrafficLight().getTrafficLightStateName());
//                }
//
//            } else if (temp_conditions.getCrossroadInfo1().getCrossroad().isNorthSouthActive()) {
//                System.out.println("north-south: " + round_time + " ->  " + passed_first_crossroad + " , " + passed_second_crossroad);
//                //calculate cars amount per second and subst it
//                int[] cars_passed = calculatePassedCars(
//                        temp_conditions.getCrossroadInfo1().getNorth().getActualSpeed(),
//                        temp_conditions.getCrossroadInfo1().getSouth().getActualSpeed(),
//                        temp_conditions.getCrossroadInfo2().getNorth().getActualSpeed(),
//                        temp_conditions.getCrossroadInfo2().getSouth().getActualSpeed(),
//                        temp_cars_crossroad_1.get(0),
//                        temp_cars_crossroad_1.get(2),
//                        temp_cars_crossroad_2.get(0),
//                        temp_cars_crossroad_2.get(2));
//
//                passed_first_crossroad += cars_passed[0];
//                passed_second_crossroad += cars_passed[1];
//
//                //after calculation how many cars was passed crossroad check if crossroad has to change state
//                if (temp_conditions.getCrossroadInfo1().getCrossroad().getTimeDistribution().getNorthSouth() > round_time
//                        && temp_conditions.getCrossroadInfo2().getCrossroad().getTimeDistribution().getNorthSouth() > round_time) {
//                    round_time++;
//                } else {
//                    round_time = 0;
//                    temp_conditions.getCrossroadInfo1().getCrossroad().changeTrafficLightStateOnCrossroad();
//                    temp_conditions.getCrossroadInfo2().getCrossroad().changeTrafficLightStateOnCrossroad();
//
//                    System.out.println("1 north: " + temp_conditions.getCrossroadInfo1().getCrossroad().getNorthTrafficLight().getTrafficLightStateName() +
//                            ", 1 east: " + temp_conditions.getCrossroadInfo1().getCrossroad().getEastTrafficLight().getTrafficLightStateName() +
//                            ", 1 south: " + temp_conditions.getCrossroadInfo1().getCrossroad().getSouthTrafficLight().getTrafficLightStateName() +
//                            ", 1 west: " + temp_conditions.getCrossroadInfo1().getCrossroad().getWestTrafficLight().getTrafficLightStateName());
//
//
//                    System.out.println("2 north: " + temp_conditions.getCrossroadInfo2().getCrossroad().getNorthTrafficLight().getTrafficLightStateName() +
//                            ", 2 east: " + temp_conditions.getCrossroadInfo2().getCrossroad().getEastTrafficLight().getTrafficLightStateName() +
//                            ", 2 south: " + temp_conditions.getCrossroadInfo2().getCrossroad().getSouthTrafficLight().getTrafficLightStateName() +
//                            ", 2 west: " + temp_conditions.getCrossroadInfo2().getCrossroad().getWestTrafficLight().getTrafficLightStateName());
//                }
//
//            } else {
//
//                if (temp_conditions.getCrossroadInfo1().getCrossroad().getTimeDistribution().getChangingExecutionTime() > changing_time) {
//                    changing_time++;
//                    System.out.println("EXECUTE CHANGING");
//                } else {
////                    System.out.println(changing_time);
//                    temp_conditions.getCrossroadInfo1().getCrossroad().changeTrafficLightStateOnCrossroad();
//                    temp_conditions.getCrossroadInfo2().getCrossroad().changeTrafficLightStateOnCrossroad();
//                    System.out.println("1 north: " + temp_conditions.getCrossroadInfo1().getCrossroad().getNorthTrafficLight().getTrafficLightStateName() +
//                            ", 1 east: " + temp_conditions.getCrossroadInfo1().getCrossroad().getEastTrafficLight().getTrafficLightStateName() +
//                            ", 1 south: " + temp_conditions.getCrossroadInfo1().getCrossroad().getSouthTrafficLight().getTrafficLightStateName() +
//                            ", 1 west: " + temp_conditions.getCrossroadInfo1().getCrossroad().getWestTrafficLight().getTrafficLightStateName());
//
//
//                    System.out.println("2 north: " + temp_conditions.getCrossroadInfo2().getCrossroad().getNorthTrafficLight().getTrafficLightStateName() +
//                            ", 2 east: " + temp_conditions.getCrossroadInfo2().getCrossroad().getEastTrafficLight().getTrafficLightStateName() +
//                            ", 2 south: " + temp_conditions.getCrossroadInfo2().getCrossroad().getSouthTrafficLight().getTrafficLightStateName() +
//                            ", 2 west: " + temp_conditions.getCrossroadInfo2().getCrossroad().getWestTrafficLight().getTrafficLightStateName());
//                    changing_time = 0;
//                }
//
//            }
//
//            total_time++;
//        }
//        initial_duration = total_time;
//        System.err.println(" -> " + total_time + " -> [INITIAL STATE FINISH]");
//    }

//    private int[] calculatePassedCars(double first_speed_1, double second_speed_1, double first_speed_2, double second_speed_2, Queue<Car> first_cars_1, Queue<Car> second_cars_1, Queue<Car> first_cars_2, Queue<Car> second_cars_2) {
//        int[] cars_passed = new int[2];
//
//        int first_direction_cars_1 = calculateCars(first_speed_1, first_cars_1);
//        int second_direction_cars_1 = calculateCars(second_speed_1, second_cars_1);
//
//        int first_direction_cars_2 = calculateCars(first_speed_2, first_cars_2);
//        int second_direction_cars_2 = calculateCars(second_speed_2, second_cars_2);
//
//        int cars_count_1 = first_direction_cars_1 + second_direction_cars_1;
//        int cars_count_2 = first_direction_cars_2 + second_direction_cars_2;
//
//        cars_passed[0] = cars_count_1;
//        cars_passed[1] = cars_count_2;
//
//        return cars_passed;
//    }
//
//    private int calculateCars(double actual_speed, Queue<Car> cars) {
//        double speed = Formulas.convertKMpHtoMpS(actual_speed);
//        int time = 1;
//        double distance = speed * time;
//
//        int cars_passed = 0;
//        int safety_distance = 4;
//
//        while (distance > 0 && !cars.isEmpty()) {
//
//            Car car = cars.poll();
//            distance -= (car.getLength() + safety_distance); //car length + some distance
//            cars_passed++;
//        }
//
//        return cars_passed;
//    }

    public void findBetterDuration() {

    }


    public boolean isBetterDuration(double duration) {
        if (duration < actual_duration) {
            return true;
        }
        return false;
    }


    private void createCars() {
        cars_crossroad_1 = new ArrayList<>();
        cars_crossroad_1.add(new LaneInfo(conditions.getCrossroadInfo1().getNorth().getCarsCount()));
        cars_crossroad_1.add(new LaneInfo(conditions.getCrossroadInfo1().getEast().getCarsCount()));
        cars_crossroad_1.add(new LaneInfo(conditions.getCrossroadInfo1().getSouth().getCarsCount()));
        cars_crossroad_1.add(new LaneInfo(conditions.getCrossroadInfo1().getWest().getCarsCount()));

        cars_crossroad_2 = new ArrayList<>();
        cars_crossroad_2.add(new LaneInfo(conditions.getCrossroadInfo2().getNorth().getCarsCount()));
        cars_crossroad_2.add(new LaneInfo(conditions.getCrossroadInfo2().getEast().getCarsCount()));
        cars_crossroad_2.add(new LaneInfo(conditions.getCrossroadInfo2().getSouth().getCarsCount()));
        cars_crossroad_2.add(new LaneInfo(conditions.getCrossroadInfo2().getWest().getCarsCount()));
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

    //    private void createCars() {
//        cars_crossroad_1 = new ArrayList<>();
//        cars_crossroad_1.add(addCarsToQueue(conditions.getCrossroadInfo1().getNorth().getCarsCount()));
//        cars_crossroad_1.add(addCarsToQueue(conditions.getCrossroadInfo1().getEast().getCarsCount()));
//        cars_crossroad_1.add(addCarsToQueue(conditions.getCrossroadInfo1().getSouth().getCarsCount()));
//        cars_crossroad_1.add(addCarsToQueue(conditions.getCrossroadInfo1().getWest().getCarsCount()));
//
//        cars_crossroad_2 = new ArrayList<>();
//        cars_crossroad_2.add(addCarsToQueue(conditions.getCrossroadInfo2().getNorth().getCarsCount()));
//        cars_crossroad_2.add(addCarsToQueue(conditions.getCrossroadInfo2().getEast().getCarsCount()));
//        cars_crossroad_2.add(addCarsToQueue(conditions.getCrossroadInfo2().getSouth().getCarsCount()));
//        cars_crossroad_2.add(addCarsToQueue(conditions.getCrossroadInfo2().getWest().getCarsCount()));
//    }

//    private Queue<Car> addCarsToQueue(int carsCount) {
//        Queue<Car> cars = new ArrayDeque<>();
//        while (carsCount > 0) {
//            cars.add(CarFactory.createCar(Utils.createRandomCarType()));
//            carsCount--;
//        }
//        return cars;
//    }

//    public Queue<Car> getCarsCountNorthCrossroad_1() {
//        return cars_crossroad_1.get(0);
//    }
//
//    public Queue<Car> getCarsCountEastCrossroad_1() {
//        return cars_crossroad_1.get(1);
//    }
//
//    public Queue<Car> getCarsCountSouthCrossroad_1() {
//        return cars_crossroad_1.get(2);
//    }
//
//    public Queue<Car> getCarsCountWestCrossroad_1() {
//        return cars_crossroad_1.get(3);
//    }
//
//    public Queue<Car> getCarsCountNorthCrossroad_2() {
//        return cars_crossroad_2.get(0);
//    }
//
//    public Queue<Car> getCarsCountEastCrossroad_2() {
//        return cars_crossroad_2.get(1);
//    }
//
//    public Queue<Car> getCarsCountSouthCrossroad_2() {
//        return cars_crossroad_2.get(2);
//    }
//
//    public Queue<Car> getCarsCountWestCrossroad_2() {
//        return cars_crossroad_2.get(3);
//    }


    public double getInitialDuration() {
        return initial_duration;
    }

    public double getActualDuration() {
        return actual_duration;
    }
}
