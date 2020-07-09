package SystemSTL;

import Objects.Car.Car;
import Objects.Car.CarFactory;
import Tools.Utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;


//information includes the queue with cars and the queue with distances from each car to the intersection in one lane
public class LaneInfo {

    //    private Queue<CarInfo> cars_in_lane;
    private ArrayList<CarInfo> cars_in_lane;
    private Car last_car;
    private double last_car_distance;
    private double speed_limit;

    public LaneInfo(int cars_count, double sl) {
        speed_limit = sl;
        addCarsToQueue(cars_count);
    }

    private void addCarsToQueue(int carsCount) {
//        cars_in_lane = new ArrayDeque<>();
        cars_in_lane = new ArrayList<>();
        int iteration = 0;

        double distance = 0;
        double next_car_distance = 0;

        while (iteration < carsCount) {

            Car car = CarFactory.createCar(Utils.createRandomCarType());

            if (iteration != 0) {
                distance += next_car_distance;
            }

            cars_in_lane.add(new CarInfo(car, distance));

            next_car_distance = car.getLength() + Utils.createRandomDistanceInRange(2, 5);

            iteration++;

            last_car_distance = distance;
            last_car = car;
        }
    }

//    public Queue<CarInfo> getCarsInLane() {
//        return cars_in_lane;
//    }

    public ArrayList<CarInfo> getCarsInLane() {
        return cars_in_lane;
    }

    public double getLastCarDistance() {
        return last_car_distance;
    }

    public void setLastCarDistance(double new_distance) {
        last_car_distance = new_distance;
    }

    public Car getLastCar() {
        return last_car;
    }

    public void setLastCar(Car last_car) {
        this.last_car = last_car;
    }

    public double getSpeedLimit() {
        return speed_limit;
    }
}
