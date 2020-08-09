package SystemSTL;

import Tools.Formulas;

/**
 * This class calculates information about vehicles such as current speed and distance from an intersection.
 */
public class LaneComputation extends Thread {

    private LaneInfo lane_info;
    private double initial_time;
    private boolean moving_mode;

    public LaneComputation(LaneInfo lane_info) {
        this.lane_info = lane_info;
        initial_time = computeInitialTime();
    }


    //https://www.diva-portal.org/smash/get/diva2:1214166/FULLTEXT01.pdf
    private double computeInitialTime() {
        double t_start = Formulas.T_start(lane_info.getCarsInLane().size());
        double t_move = Formulas.T_move(lane_info.getLastCar().getMaxSpeed(),
                lane_info.getLastCar().getAcceleration(),
                lane_info.getLastCarDistance());

        //This is 0 if the opposite vehicle is not going left.
        double t_opposite = Formulas.T_opossite(0);

        return Formulas.T_common(t_start, t_move, t_opposite);
    }

    @Override
    public void run() {
        runLane(1);
    }

    private void stopLane(double time) {

    }

    private void runLane(double time) {
        double part_of_time = 0;

        double add = 0.01;

        while (part_of_time < time) {
            part_of_time += add;

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(part_of_time);

            CarComputation car_computation;

            for (int i = 0; i < lane_info.getCarsInLane().size(); i++) {

//                lane_info.getCarsInLane().get(0).print();

                //ride
                if (moving_mode) {
                    //no need to compute
                    if (lane_info.getCarsInLane().get(i).getDistanceFromCrossroad() < -500) {
                        lane_info.getCarsInLane().remove(i);
                        i--;
                    } else {
                        car_computation = new CarComputation(lane_info.getCarsInLane().get(i));
                        car_computation.movingMode(add, lane_info.getSpeedLimit());
                    }
                }
                //stop
                else {
                    //if car is far from intersection then remove it from array
                    if (lane_info.getCarsInLane().get(i).getDistanceFromCrossroad() < -500) {
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

    public double getInitialTime() {
        return initial_time;
    }

    public boolean getMovingMode() {
        return moving_mode;
    }

    public void setMovingMode(boolean moving_mode) {
        this.moving_mode = moving_mode;
    }
}
