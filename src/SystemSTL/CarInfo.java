package SystemSTL;

import Objects.Car.Car;

public class CarInfo {

    private Car car;
    private double distance;

    public CarInfo(Car c, double d){
        car = c;
        distance = d;
    }

    public Car getCar() {
        return car;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double new_distance) {
        distance = new_distance;
    }
}
