package SystemSTL;

import Objects.Car.Car;
import Objects.Car.CarFactory;
import Tools.Utils;

import java.util.ArrayList;


//information includes the queue with cars and the queue with distances from each car to the intersection in one lane

/**
 * This class keep information that includes cars and speed limit of specific direction on road.
 */
public class LaneInfo {

    private ArrayList<CarInfo> cars_in_lane;
    private double speed_limit;

    /**
     * Constructor.
     * @param cars_count - count of cars in to create
     * @param sl - speed limit of the direction
     */
    public LaneInfo(int cars_count, double sl) {
        speed_limit = sl;
        addCarsToList(cars_count);
    }

    /**
     * This function creates cars, calculates their distance to the intersection and adds them to the list.
     * @param carsCount
     */
    private void addCarsToList(int carsCount) {
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

//            next_car_distance = car.getLength() + Utils.createRandomDistanceInRange(2, 5);
            next_car_distance = car.getLength() + 3;

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
