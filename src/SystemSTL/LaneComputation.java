package SystemSTL;

import Tools.Formulas;

public class LaneComputation {

    private LaneInfo lane_info;
    private double initial_time;

    public LaneComputation(LaneInfo lane_info) {
        this.lane_info = lane_info;
        initial_time = computeInitialTime();
    }

    private double computeInitialTime() {
        double t_start = Formulas.T_start(lane_info.getCarsInLane().size());
        double t_move = Formulas.T_move(lane_info.getLastCar().getMaxSpeed(),
                lane_info.getLastCar().getAcceleration(),
                lane_info.getLastCarDistance());

        //This is 0 if the opposite vehicle is not going left.
        double t_opposite = 0;

        return t_start + t_move + t_opposite;
    }

    public void runComputationForTime(int time){

        int t = 0;
        while(t < time){


            t++;
        }

    }

//    private static double distance(int time, double acc) {
//        double distance = 0;
//        double t = 0;
//        while (t < time) {
//            double speed = acc * t;
//            if (speed > 70) {
//                speed = 70;
//            }
//            distance += speed * 1;
//            t++;
//            System.out.println("speed: " + speed + ", distance: " + distance);
//        }
//        return distance;
//    }

    public double getInitialTime() {
        return initial_time;
    }
}
