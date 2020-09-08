package SystemSTL.TrafficComputation.Lane;

import Objects.Car.Car;
import Objects.Car.CarFactory;
import Objects.CrossroadInfo.DirectionInfo.DirectionInfo;
import SystemSTL.TrafficComputation.Car.CarInfo;
import Tools.Constants;
import Tools.Formulas;
import Tools.Utils;

import java.util.ArrayList;


/**
 * This class keep information that includes cars and speed limit of specific direction on road.
 */
public class LaneInfo {

    private ArrayList<CarInfo> cars_in_lane;
    private double speed_limit;

    /**
     * Constructor.
     *
     * @param cars_count - count of cars in to create
     * @param sl         - speed limit of the direction
     */
    public LaneInfo(int cars_count, double sl, double as) {

        if (sl > as) {
            speed_limit = Formulas.convertKMpHtoMpS(as);
        } else {
            speed_limit = Formulas.convertKMpHtoMpS(sl);
        }

        addCarsToList(cars_count);
    }


    public LaneInfo(DirectionInfo direction_info) {

        if (direction_info.getSpeedLimit() > direction_info.getActualSpeed()) {
            speed_limit = Formulas.convertKMpHtoMpS(direction_info.getActualSpeed());
        } else {
            speed_limit = Formulas.convertKMpHtoMpS(direction_info.getSpeedLimit());
        }

        addCarsToList(direction_info.getCarsCount());
    }

    /**
     * This function creates cars, calculates their distance to the intersection and adds them to the list.
     *
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

            //random distance between two cars
//            next_car_distance = car.getLength() + Utils.createRandomInRange(2, 5);
            next_car_distance = car.getLength() + Constants.SAFETY_DISTANCE;

            iteration++;
        }
    }

    public void addCarFromPreviousCrossroad(CarInfo car) {
        car.setDistanceFromCrossroad(30);

        cars_in_lane.add(car);
    }

    public ArrayList<CarInfo> getCarsInLane() {
        return cars_in_lane;
    }

    public double getLastCarDistance() {
        return cars_in_lane.get(cars_in_lane.size() - 1).getDistanceFromCrossroad();
    }

    public CarInfo getLastCar() {
        return cars_in_lane.get(cars_in_lane.size() - 1);
    }

    public double getSpeedLimit() {
        return speed_limit;
    }
}
