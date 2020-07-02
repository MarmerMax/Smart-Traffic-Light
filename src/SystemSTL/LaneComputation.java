package SystemSTL;

import Tools.Formulas;

import java.util.ArrayList;

public class LaneComputation {

    private LaneInfo lane_info;
    private double initial_time;

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

    public void runComputationForTime(int time){

        ArrayList<CarInfo> cars_to_compute = new ArrayList<>();
        ArrayList<CarInfo> passed_cars = new ArrayList<>();

        int t = 0;
        double next_car = 0;
        while(t < time){

            if(next_car % 1 == 0){
                //add next car to cars to compute by index (next_car is index)
                cars_to_compute.add(lane_info.getCarsInLane().peek()); //is wrong
            }

            CarComputation car_computation;
            for(CarInfo car_info : cars_to_compute){

                car_computation = new CarComputation(car_info);
                car_computation.computeChanges(t);

                if(car_info.getDistance() < 0){
                    passed_cars.add(car_info);
                    lane_info.getCarsInLane().poll();
                }
            }

            next_car += 0.01;
            t += 0.01;
        }

    }

    public double getInitialTime() {
        return initial_time;
    }
}
