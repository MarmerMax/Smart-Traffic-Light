package SystemSTL.TrafficComputation.Car;

/**
 * This class performs calculations for the vehicle such that speed changing, distance changing.
 */
public class CarComputation {

    private CarInfo car_info;

    /**
     * The constructor receives the vehicle for the following calculations.
     *
     * @param car_info
     */
    public CarComputation(CarInfo car_info) {
        this.car_info = car_info;
    }

    /**
     * This function increases speed of the vehicle.
     *
     * @param time
     * @param speed_limit
     */
    private void speedUp(double time, double speed_limit) {
        double new_speed = car_info.getCurrentSpeed() + car_info.getCar().getAcceleration() * time;

        if (new_speed > car_info.getCar().getMaxSpeed()) {
            new_speed = car_info.getCar().getMaxSpeed();
        }

        if (new_speed > speed_limit) {
            car_info.setCurrentSpeed(speed_limit);
            return;
        }

        car_info.setCurrentSpeed(new_speed);
    }

    /**
     * This function decreases speed of the vehicle.
     *
     * @param time
     */
    private void speedDown(double time) {
        double new_speed = car_info.getCurrentSpeed() - car_info.getCar().getDeceleration() * time;
        if (new_speed < 0) {
            car_info.setCurrentSpeed(0);
            return;
        }
        car_info.setCurrentSpeed(new_speed / 1.2);
    }

    /**
     * This function calculate distance changing in moving mode.
     *
     * @param time
     * @param speed_limit
     */
    public void movingMode(double time, double speed_limit) {

        speedUp(time, speed_limit);

        //TODO: real speed is too high then necessary to decrease it impact to representation (GUI)
        double distance = car_info.getCurrentSpeed() * time;
        double updated_distance = car_info.getDistanceFromCrossroad() - distance;

        car_info.setDistanceFromCrossroad(updated_distance);
    }

    /**
     * This function calculate distance changing in stopping mode.
     *
     * @param time
     */
    public void stoppingMode(double time) {

        speedDown(time);

        double distance = car_info.getCurrentSpeed() * time;
        double updated_distance = car_info.getDistanceFromCrossroad() - distance;

        car_info.setDistanceFromCrossroad(updated_distance);
    }
}
