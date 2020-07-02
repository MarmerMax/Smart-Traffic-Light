package SystemSTL;

public class CarComputation {

    private CarInfo car_info;

    public CarComputation(CarInfo car_info) {
        this.car_info = car_info;
    }


    public void computeChanges(double time) {
        double speed_limit = 70;

        double speed = car_info.getCar().getAcceleration() * time;

        if (speed > car_info.getCar().getMaxSpeed()) {
            speed = car_info.getCar().getMaxSpeed();
            if (speed > speed_limit) {
                speed = speed_limit;
            }
        }

        double distance = speed * 1;
        double updated_distance = car_info.getDistance() - distance;

        car_info.setDistance(updated_distance);

        System.out.println("speed: " + speed + ", distance: " + distance);
    }
}
