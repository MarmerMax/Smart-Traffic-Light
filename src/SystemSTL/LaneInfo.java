package SystemSTL;

import Objects.Car.Car;
import Objects.Car.CarFactory;
import Tools.Utils;

import java.util.ArrayDeque;
import java.util.Queue;


//information includes the queue with cars and the queue with distances from each car to the intersection in one lane
public class LaneInfo {

    private Queue<CarInfo> cars_in_lane;
    private Car last_car;
    private double last_car_distance;

    public LaneInfo(int cars_count) {
        addCarsToQueue(cars_count);
    }

    private void addCarsToQueue(int carsCount) {
        cars_in_lane = new ArrayDeque<>();
        int iteration = 0;

        double distance = 0;
        while (iteration < carsCount) {

            Car car = CarFactory.createCar(Utils.createRandomCarType());
            distance += car.getLength() + 5;

            cars_in_lane.add(new CarInfo(car, distance));

            iteration++;

            last_car_distance = distance;
            last_car = car;
        }
    }

    public Queue<CarInfo> getCarsInLane() {
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
}
