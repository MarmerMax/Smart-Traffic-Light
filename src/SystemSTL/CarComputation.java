package SystemSTL;

public class CarComputation {

    private CarInfo car_info;

    public CarComputation(CarInfo car_info) {
        this.car_info = car_info;
    }

    private void speedUp(double time, double speed_limit) {
        double new_speed = car_info.getCurrentSpeed() + car_info.getCar().getAcceleration() * time;
        if (new_speed > speed_limit) {
            car_info.setCurrentSpeed(car_info.getCar().getMaxSpeed());
        }
        car_info.setCurrentSpeed(new_speed);
    }

    private void speedDown(double time) {
        double new_speed = car_info.getCurrentSpeed() - car_info.getCar().getDeceleration() * time;
        if (new_speed < 0) {
            car_info.setCurrentSpeed(0);
        }
        car_info.setCurrentSpeed(new_speed);
    }

    public void movingMode(double time, double speed_limit) {

        speedUp(time, speed_limit);

        double distance = car_info.getCurrentSpeed() * time;
        double updated_distance = car_info.getDistanceFromCrossroad() - distance;

        car_info.setDistanceFromCrossroad(updated_distance);
    }

    public void stoppingMode(double time) {

        speedDown(time);

        double distance = car_info.getCurrentSpeed() * time;
        double updated_distance = car_info.getDistanceFromCrossroad() - distance;

        car_info.setDistanceFromCrossroad(updated_distance);

    }
}
