package SystemSTL;

/**
 * This class calculates information about vehicles such as current speed and distance from an intersection.
 */
public class LaneComputation extends Thread {

    private LaneInfo lane_info;
    private boolean moving_mode;
    private boolean stop_computation;

    public LaneComputation(LaneInfo lane_info) {
        this.lane_info = lane_info;
        stop_computation = false;
    }

    @Override
    public void run() {
//        double time = 100;
//        computeLane(0.1);
        computeLane();
    }

//    private void computeLane(double time) {
    private void computeLane() {
//        double part_of_time = 0;

        double add = 0.1;

//        while (part_of_time < time) {
        while (!stop_computation) {
//            part_of_time += add;

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }

            CarComputation car_computation;

            for (int i = 0; i < lane_info.getCarsInLane().size(); i++) {

                //moving
                if (moving_mode) {
                    //no need to compute
                    if (lane_info.getCarsInLane().get(i).getDistanceFromCrossroad() < -100) {
                        lane_info.getCarsInLane().remove(i);
                        i--;
                    } else {
                        car_computation = new CarComputation(lane_info.getCarsInLane().get(i));
                        car_computation.movingMode(add, lane_info.getSpeedLimit());
                    }
                }
                //stopping
                else {
                    //if car is far from intersection then remove it from array
                    if (lane_info.getCarsInLane().get(i).getDistanceFromCrossroad() < -100) {
                        lane_info.getCarsInLane().remove(i);
                        i--;
                    } else {
                        //if car passed the intersection line then continue it's computation
                        if (lane_info.getCarsInLane().get(i).getCurrentSpeed() > 0 &&
                                lane_info.getCarsInLane().get(i).getDistanceFromCrossroad() < 0) {
                            car_computation = new CarComputation(lane_info.getCarsInLane().get(i));
                            car_computation.movingMode(add, lane_info.getSpeedLimit());
                        } else {
                            car_computation = new CarComputation(lane_info.getCarsInLane().get(i));
                            car_computation.stoppingMode(add);
                        }
                    }
                }
            }
        }
    }

    public void stopComputation(){
        stop_computation = true;
    }

    public boolean getMovingMode() {
        return moving_mode;
    }

    public void setMovingMode(boolean moving_mode) {
        this.moving_mode = moving_mode;
    }
}
