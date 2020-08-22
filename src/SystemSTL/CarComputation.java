package SystemSTL;

import Tools.Constants;

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
        if (new_speed > speed_limit / Constants.METER_TO_PIXEL) {
            car_info.setCurrentSpeed(speed_limit / Constants.METER_TO_PIXEL);
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
        car_info.setCurrentSpeed(new_speed);
    }

    /**
     * This function calculate distance changing in moving mode.
     *
     * @param time
     * @param speed_limit
     */
    public void movingMode(double time, double speed_limit) {

        speedUp(time, speed_limit);

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
