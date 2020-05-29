package System;

import Objects.Car.Car;
import Objects.SystemConditions.Conditions;
import Utils.Formulas;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Algorithm {

    private Conditions conditions;
    private double initial_duration;
    private double actual_duration;
    private Queue<Car> cars_count_north_crossroad_1;
    private Queue<Car> cars_count_east_crossroad_1;
    private Queue<Car> cars_count_south_crossroad_1;
    private Queue<Car> cars_count_west_crossroad_1;

    private Queue<Car> cars_count_north_crossroad_2;
    private Queue<Car> cars_count_east_crossroad_2;
    private Queue<Car> cars_count_south_crossroad_2;
    private Queue<Car> cars_count_west_crossroad_2;


    public Algorithm(Conditions conditions) {
        this.conditions = conditions;
        checkInitialStateDuration();
        createCars();
    }

    private void checkInitialStateDuration() {
        Queue<Car> temp_cars_count_north_crossroad_1 = copyQueue(cars_count_north_crossroad_1);
        Queue<Car> temp_cars_count_east_crossroad_1 = copyQueue(cars_count_east_crossroad_1);
        Queue<Car> temp_cars_count_south_crossroad_1 = copyQueue(cars_count_south_crossroad_1);
        Queue<Car> temp_cars_count_west_crossroad_1 = copyQueue(cars_count_west_crossroad_1);

        Queue<Car> temp_cars_count_north_crossroad_2 = copyQueue(cars_count_north_crossroad_2);
        Queue<Car> temp_cars_count_east_crossroad_2 = copyQueue(cars_count_east_crossroad_2);
        Queue<Car> temp_cars_count_south_crossroad_2 = copyQueue(cars_count_south_crossroad_2);
        Queue<Car> temp_cars_count_west_crossroad_2 = copyQueue(cars_count_west_crossroad_2);


        int time_unit = 1;
        Conditions temp = new Conditions(conditions);

        int first_crossroad_total_cars =
                conditions.getCrossroadInfo1().getEast().getCarsCount() +
                        conditions.getCrossroadInfo1().getWest().getCarsCount() +
                        conditions.getCrossroadInfo1().getNorth().getCarsCount() +
                        conditions.getCrossroadInfo1().getSouth().getCarsCount();

        int second_crossroad_total_cars =
                conditions.getCrossroadInfo1().getEast().getCarsCount() +
                        conditions.getCrossroadInfo1().getWest().getCarsCount() +
                        conditions.getCrossroadInfo1().getNorth().getCarsCount() +
                        conditions.getCrossroadInfo1().getSouth().getCarsCount();

        int passed_first_crossroad = 0;
        int passed_second_crossroad = 0;

        int total_time = 0;
        int round_time = 0;


        while (first_crossroad_total_cars > passed_first_crossroad || second_crossroad_total_cars > passed_second_crossroad) {
//            initial_duration += calculateDuration();
            int cars_count = 0;
            if (conditions.getCrossroadInfo1().getCrossroad().isEastWestActive()) {
                //calculate cars amount per second and subst it
                int actual_east_speed = conditions.getCrossroadInfo1().getEast().getActualSpeed();
                int actual_west_speed = conditions.getCrossroadInfo1().getEast().getActualSpeed();
                int east_cars = calculateCars(actual_east_speed, temp_cars_count_east_crossroad_1);
                int west_cars = calculateCars(actual_west_speed, temp_cars_count_west_crossroad_1);
                cars_count = east_cars + west_cars;
                passed_first_crossroad += cars_count;

            } else if (conditions.getCrossroadInfo1().getCrossroad().isNorthSouthActive()) {
                //calculate cars amount per second and subst it

                int actual_north_speed = conditions.getCrossroadInfo1().getNorth().getActualSpeed();
                int actual_south_speed = conditions.getCrossroadInfo1().getSouth().getActualSpeed();
                int north_cars = calculateCars(actual_north_speed, temp_cars_count_north_crossroad_1);
                int south_cars = calculateCars(actual_south_speed, temp_cars_count_south_crossroad_1);
                cars_count = north_cars + south_cars;
                passed_first_crossroad += cars_count;
            }

            if (conditions.getCrossroadInfo2().getCrossroad().isEastWestActive()) {
                //calculate cars amount per second and subst it
                int actual_east_speed = conditions.getCrossroadInfo2().getEast().getActualSpeed();
                int actual_west_speed = conditions.getCrossroadInfo2().getEast().getActualSpeed();
                int east_cars = calculateCars(actual_east_speed, temp_cars_count_east_crossroad_2);
                int west_cars = calculateCars(actual_west_speed, temp_cars_count_west_crossroad_2);
                cars_count = east_cars + west_cars;
                passed_first_crossroad += cars_count;

            } else if (conditions.getCrossroadInfo2().getCrossroad().isNorthSouthActive()) {
                //calculate cars amount per second and subst it
                int actual_north_speed = conditions.getCrossroadInfo2().getNorth().getActualSpeed();
                int actual_south_speed = conditions.getCrossroadInfo2().getSouth().getActualSpeed();
                int north_cars = calculateCars(actual_north_speed, temp_cars_count_north_crossroad_2);
                int south_cars = calculateCars(actual_south_speed, temp_cars_count_south_crossroad_2);
                cars_count = north_cars + south_cars;
                passed_first_crossroad += cars_count;

            }

//            if () {
//
//            }
        }

    }

    private int calculateCars(int actual_speed, Queue<Car> cars) {
        double speed = Formulas.convertKilometersPefHourToMetersPerSecond(actual_speed);
        int time = 1;
        double distance = speed * time;

        int cars_passed = 0;
        int safety_distance = 4;

        while (distance > 0) {
            Car car = cars.poll();
            distance -= (car.getLength() + safety_distance); //car length + some distance
            cars_passed++;
        }

        return cars_passed;
    }

    public void run() {

    }

    private void createCars() {
        cars_count_north_crossroad_1 = addCarsToQueue(conditions.getCrossroadInfo1().getNorth().getCarsCount());
        cars_count_east_crossroad_1 = addCarsToQueue(conditions.getCrossroadInfo1().getEast().getCarsCount());
        cars_count_south_crossroad_1 = addCarsToQueue(conditions.getCrossroadInfo1().getSouth().getCarsCount());
        cars_count_west_crossroad_1 = addCarsToQueue(conditions.getCrossroadInfo1().getWest().getCarsCount());

        cars_count_north_crossroad_2 = addCarsToQueue(conditions.getCrossroadInfo2().getNorth().getCarsCount());
        cars_count_east_crossroad_2 = addCarsToQueue(conditions.getCrossroadInfo2().getEast().getCarsCount());
        cars_count_south_crossroad_2 = addCarsToQueue(conditions.getCrossroadInfo2().getSouth().getCarsCount());
        cars_count_west_crossroad_2 = addCarsToQueue(conditions.getCrossroadInfo2().getWest().getCarsCount());
    }

    private Queue<Car> addCarsToQueue(int carsCount) {
        Queue<Car> cars = new ArrayDeque<>();
        while (carsCount > 0) {
            cars.add(new Car());
        }
        return cars;
    }

    private Queue<Car> copyQueue(Queue<Car> cars) {
        return new ArrayDeque<>(cars);
    }


    public Queue<Car> getCarsCountNorthCrossroad_1() {
        return cars_count_north_crossroad_1;
    }

    public Queue<Car> getCarsCountEastCrossroad_1() {
        return cars_count_east_crossroad_1;
    }

    public Queue<Car> getCarsCountWestCrossroad_2() {
        return cars_count_west_crossroad_2;
    }

    public Queue<Car> getCarsCountSouthCrossroad_2() {
        return cars_count_south_crossroad_2;
    }

    public Queue<Car> getCarsCountEastCrossroad_2() {
        return cars_count_east_crossroad_2;
    }

    public Queue<Car> getCarsCountNorthCrossroad_2() {
        return cars_count_north_crossroad_2;
    }


    public Queue<Car> getCarsCountWestCrossroad_1() {
        return cars_count_west_crossroad_1;
    }

    public Queue<Car> getCarsCountSouthCrossroad_1() {
        return cars_count_south_crossroad_1;
    }

    public double getInitialDuration() {
        return initial_duration;
    }

    public double getActualDuration() {
        return actual_duration;
    }
}
