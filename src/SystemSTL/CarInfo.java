package SystemSTL;

import Objects.Car.Car;

public class CarInfo {

    private Car car;
    private double distance_from_crossroad;
    private double current_speed;

    public CarInfo(Car c, double d) {
        car = c;
        distance_from_crossroad = d;
        current_speed = 0;
    }

    public Car getCar() {
        return car;
    }

    public double getDistanceFromCrossroad() {
        return distance_from_crossroad;
    }

    public void setDistanceFromCrossroad(double new_distance) {
        distance_from_crossroad = new_distance;
    }

    public double getCurrentSpeed() {
        return current_speed;
    }

    public void setCurrentSpeed(double cs) {
        current_speed = cs;
    }
}
