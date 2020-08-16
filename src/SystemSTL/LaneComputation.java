package SystemSTL;

/**
 * This class calculates information about vehicles such as current speed and distance from an intersection.
 */
public class LaneComputation extends Thread {

    private LaneInfo lane_info;
    private boolean moving_mode; //true-moving, false-stopping. Received from the outside.
    private boolean stop_computation; //when the all cars are passed is necessary to stop the thread

    public LaneComputation(LaneInfo lane_info) {
        this.lane_info = lane_info;
        stop_computation = false;
        moving_mode = false;
    }

    @Override
    public void run() {
        computeLane();
    }

    private void computeLane() {
//        System.out.println(this.getName() + " start lane computation");
        double add = 0.1;

        while (!stop_computation) {

            CarComputation car_computation;

//            for (int i = 0; i < lane_info.getCarsInLane().size() && !stop_computation; i++) {
            for (int i = 0; i < lane_info.getCarsInLane().size(); i++) {
//                System.out.println(this.getName() + ": " + lane_info.getCarsInLane().size());

                car_computation = new CarComputation(lane_info.getCarsInLane().get(i));

                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //if the car passed the intersection and placed out of the inspected area then remove this car
                if (lane_info.getCarsInLane().get(i).getDistanceFromCrossroad() < -50) {
                    lane_info.getCarsInLane().remove(i);
                    i--;
                    continue;
                }

                if (moving_mode) {
//                    System.out.println(this.getName() + " moving");

                    car_computation.movingMode(add, lane_info.getSpeedLimit());
                } else {
//                    System.out.println(this.getName() + " stopping");
                    //if car passed the intersection line then continue it's computation
                    if (lane_info.getCarsInLane().get(i).getCurrentSpeed() > 0 &&
                            lane_info.getCarsInLane().get(i).getDistanceFromCrossroad() < 0) {

                        car_computation.movingMode(add, lane_info.getSpeedLimit());
                    } else {

                        car_computation.stoppingMode(add);
                    }
                }
            }

            if (lane_info.getCarsInLane().size() == 0) {
                stop_computation = true;
            }
        }
        System.out.println(this.getName() + " finished lane computation");
    }

    public void setMovingMode(boolean moving_mode) {
        this.moving_mode = moving_mode;
    }

    public boolean getMovingMode() {
        return moving_mode;
    }
}
