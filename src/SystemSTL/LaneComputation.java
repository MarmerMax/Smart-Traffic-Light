package SystemSTL;

import Tools.Formulas;

import java.util.ArrayList;

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

        if (moving_mode) {
            runLane(1);
        } else {
            stopLane();
        }
    }

    private void stopLane() {

    }

    private void runLane(double time) {
        double part_of_time = 0;

        double add = 0.01;

        while (part_of_time < time) {
            part_of_time += add;

            CarComputation car_computation;

            for (int i = 0; i < lane_info.getCarsInLane().size(); i++) {

                car_computation = new CarComputation(lane_info.getCarsInLane().get(i));

                if (i == 0) {

                    car_computation.movingMode(add, lane_info.getSpeedLimit());

                    if (lane_info.getCarsInLane().get(i).getDistanceFromCrossroad() < 0) {
                        //add car info of lane_info.getCarsInLane().get(i) to structure with passed cars

                        lane_info.getCarsInLane().remove(i);
                        i--;
                    }

                } else if (i == lane_info.getCarsInLane().size() - 1) {//last

                    //check if his prev car is already start moving


                } else {

                    //check if his prev car is already start moving

                }
            }
        }

    }

//    private void runLane(double time) {
//
//        ArrayList<CarInfo> cars_to_compute = new ArrayList<>();
//        ArrayList<CarInfo> passed_cars = new ArrayList<>();
//
//        int t = 0;
//        double next_car = 0;
//        while (t < time) {
//
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            if (next_car % 1 == 0) {
//                //add next car to cars to compute by index (next_car is index)
//                cars_to_compute.add(lane_info.getCarsInLane().peek()); //is wrong
//            }
//
//            CarComputation car_computation;
//            for (CarInfo car_info : cars_to_compute) {
//
//                car_computation = new CarComputation(car_info);
//                car_computation.computeChanges(t);
//
//                if (car_info.getDistance() < 0) {
//                    passed_cars.add(car_info);
//                    lane_info.getCarsInLane().poll();
//                }
//            }
//
//            next_car += 0.01;
//            t += 0.01;
//        }
//
//    }


    public double getInitialTime() {
        return initial_time;
    }

    public boolean isMovingMode() {
        return moving_mode;
    }

    public void setMovingMode(boolean moving_mode) {
        this.moving_mode = moving_mode;
    }
}
