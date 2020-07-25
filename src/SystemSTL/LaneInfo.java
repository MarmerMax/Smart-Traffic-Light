package SystemSTL;

import Objects.Car.Car;
import Objects.Car.CarFactory;
import Tools.Utils;

import java.util.ArrayList;


//information includes the queue with cars and the queue with distances from each car to the intersection in one lane
public class LaneInfo {

    private ArrayList<CarInfo> cars_in_lane;
    private double speed_limit;

    public LaneInfo(int cars_count, double sl) {
        speed_limit = sl;
        addCarsToQueue(cars_count);
    }

    private void addCarsToQueue(int carsCount) {
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
        }
    }

    public ArrayList<CarInfo> getCarsInLane() {
        return cars_in_lane;
    }

    public double getLastCarDistance() {
        return cars_in_lane.get(cars_in_lane.size() - 1).getDistanceFromCrossroad();
    }

    public Car getLastCar() {
        return cars_in_lane.get(cars_in_lane.size() - 1).getCar();
    }

    public double getSpeedLimit() {
        return speed_limit;
    }
}
