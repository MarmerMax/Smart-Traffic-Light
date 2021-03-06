package Tools;

import Objects.Car.Car;
import SystemSTL.TrafficComputation.Lane.LaneInfo;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Queue;

public class Formulas {
    public static double convertKMpHtoMpS(double speed) {
        double result = speed * 1000 / 3600;
        return result;
    }

    public static double convertMpStoKMpH(double speed) {
        double result = speed * 3600 / 1000;
        return result;
    }

    public double calculateSpeed(double acceleration, double time) {
        double result = acceleration * time;
        return result;
    }

    public double calculateDistance(double speed, double time) {
        double result = speed * time;
        return result;
    }

    //return meters
    public static double calculateDistanceByVelocityAndTime(double time, double velocity) {
        return time * velocity;
    }

    //return meters
    public static double calculateDistanceByAccelerationAndTime(double time, double acc, double start_velocity) {
        double result = start_velocity * time + ((acc * Math.pow(time, 2) / 2));
        return result;
    }

    //return m/s
    public static double calculateVelocity(double time, double acc, double start_velocity) {
        double result = start_velocity + time * acc;
        return result;
    }

    //return seconds
    public static double calculateTimeForMaxSpeed(double acc, double velocity) {
        velocity = Formulas.convertKMpHtoMpS(velocity);
        double result = velocity / acc;
        return result;
    }

    public static double calculateTimeForPassingDistanceByAcc(double acc, double dist) {
        double result = Math.sqrt(2 * dist / acc);
        return result;
    }


    /**
     * @param N_lane - number of vehicles in the lane
     * @return - start time for lane
     */
    public static double T_start(int N_lane) {
        return 0.5 * (double) N_lane;
    }

    /**
     * @param V_last_vehicle - is the velocity for the last vehicle in the lane’s queue.
     * @param A_last_vehicle - is the acceleration for the last vehicle in the lane’s queue.
     * @param D_last_vehicle - is the vehicle’s distance to the center of the intersection.
     * @return - time to move intersection for the all vehicles in the lane
     */
    public static double T_move(double V_last_vehicle, double A_last_vehicle, double D_last_vehicle) {
        return -(V_last_vehicle / A_last_vehicle)
                + Math.sqrt(Math.pow(V_last_vehicle / A_last_vehicle, 2) + ((2 * D_last_vehicle) / A_last_vehicle));
    }

    /**
     * Time of opposite lane if there are vehicle that must to turn.
     *
     * @param N_opposite - Is the amount of vehicles from the opposite road that the last vehicle must wait for.
     *                   This is 0 if the vehicle is not going left.
     * @return - time in opposite lane.
     */
    public static double T_opposite(int N_opposite) {
        return 0.1 * (double) N_opposite;
    }

    /**
     * @param t_start    - start time of lane
     * @param t_move     - time of moving
     * @param t_opposite - time of the opposite lane
     * @return - common time of intersection
     */
    public static double T_common(double t_start, double t_move, double t_opposite) {
        return t_start + t_move + t_opposite;
    }

    /**
     * This function calculate the time for passing the crossroad for specific lane.
     *
     * @param lane_info - lane to check
     * @return required moving time
     */
    // https://www.diva-portal.org/smash/get/diva2:1214166/FULLTEXT01.pdf
    private double computeInitialTime(LaneInfo lane_info) {
        double t_start = T_start(lane_info.getCarsInLane().size());
//        double t_move = Formulas.T_move(lane_info.getLastCar().getMaxSpeed(),
        double t_move = T_move(0,
                lane_info.getLastCar().getCar().getAcceleration(),
                lane_info.getLastCarDistance());

        //It is 0 if the opposite vehicle is not going to turn around.
        double t_opposite = T_opposite(0);

        return T_common(t_start, t_move, t_opposite);
    }

    //average waiting time
    public static double AWT(ArrayList<Double> times) {
        double cars = times.size();
        double result = 0;

        for (double time : times) {
            result += time;
        }

        result /= cars;

        return result;
    }
}
