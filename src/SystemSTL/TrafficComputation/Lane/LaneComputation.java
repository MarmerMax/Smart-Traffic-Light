package SystemSTL.TrafficComputation.Lane;

import SystemSTL.TrafficComputation.Car.CarComputation;
import SystemSTL.TrafficComputation.Car.CarInfo;
import Tools.ConsoleColors;
import Tools.Constants;

import java.time.Duration;
import java.time.Instant;

/**
 * This class calculates changes of vehicles in the lane such as current speed and distance from an intersection.
 */
public class LaneComputation extends Thread {

    private LaneInfo lane_info;
    private boolean moving_mode; //true-moving, false-stopping. Received from the outside.
    private boolean stop_computation; //when the all cars are passed is necessary to stop the thread
    private LaneInfo next_lane_info;

    public LaneComputation(LaneInfo lane_info) {
        this.lane_info = lane_info;
        stop_computation = false;
        moving_mode = false;
    }

    @Override
    public void run() {
        computeLane();
    }


    /**
     * This function computes changes of each car on the given lane such that current speed and distance from crossroad.
     */
    private void computeLane() {
        double add = 0.01; // =10ms

        while (!stop_computation) {

            CarComputation car_computation;

            Instant before = Instant.now();


            for (int i = 0; i < lane_info.getCarsInLane().size(); i++) {

                car_computation = new CarComputation(lane_info.getCarsInLane().get(i));

                String lane_name = this.getName().toLowerCase();

                //if the car passed the intersection and placed out of the inspected area then remove this car
                if (lane_info.getCarsInLane().get(i).getDistanceFromCrossroad() < -5) {

                    //move this car from first west to second west
                    if (lane_name.contains("first") && lane_name.contains("west")) {

                        next_lane_info.addCarFromPreviousCrossroad(lane_info.getCarsInLane().get(i));
                        lane_info.getCarsInLane().remove(i);
                        i--;
                        continue;

                        //move this car from first east to second east
                    } else if (lane_name.contains("second") && lane_name.contains("east")) {

                        next_lane_info.addCarFromPreviousCrossroad(lane_info.getCarsInLane().get(i));
                        lane_info.getCarsInLane().remove(i);
                        i--;
                        continue;

                    } else if (lane_info.getCarsInLane().get(i).getDistanceFromCrossroad() < -40) {

                        lane_info.getCarsInLane().remove(i);
                        i--;

                        continue;
                    }
                }

                //traffic light state is green
                if (moving_mode) {
                    //if this is first car in lane
                    if (i == 0) {

                        //TODO: check distance between cars in another lanes
                        if (lane_name.contains("first") && lane_name.contains("west")) {
                            CarInfo front_car = next_lane_info.getLastCar();
                            CarInfo current_car = lane_info.getCarsInLane().get(i);
                            double dist = (current_car.getDistanceFromCrossroad() + 35) - (front_car.getDistanceFromCrossroad() + front_car.getCar().getLength());

                            dist = Math.abs(dist);
                            if (dist > Constants.SAFETY_DISTANCE_TO_START) {
                                car_computation.movingMode(add, lane_info.getSpeedLimit());
                            } else {
                                car_computation.stoppingMode(add);
                            }
//
                        } else if (lane_name.contains("second") && lane_name.contains("east")) {
                            CarInfo front_car = next_lane_info.getLastCar();
                            CarInfo current_car = lane_info.getCarsInLane().get(i);
                            double dist = (current_car.getDistanceFromCrossroad() + 35) - (front_car.getDistanceFromCrossroad() + front_car.getCar().getLength());

                            dist = Math.abs(dist);
                            if (dist > Constants.SAFETY_DISTANCE_TO_START) {
                                car_computation.movingMode(add, lane_info.getSpeedLimit());
                            } else {
                                car_computation.stoppingMode(add);
                            }
                        } else {
                            car_computation.movingMode(add, lane_info.getSpeedLimit());
                        }


                    } else {
                        //if distance from the front car is correct for moving then start
                        CarInfo front_car = lane_info.getCarsInLane().get(i - 1);
                        CarInfo current_car = lane_info.getCarsInLane().get(i);
                        double dist = current_car.getDistanceFromCrossroad() - (front_car.getDistanceFromCrossroad() + front_car.getCar().getLength());

                        dist = Math.abs(dist);
                        if (dist > Constants.SAFETY_DISTANCE_TO_START) {
                            car_computation.movingMode(add, lane_info.getSpeedLimit());
                        } else {
                            car_computation.stoppingMode(add);
                        }
                    }

                    //traffic light state is not green
                } else {

                    //if the traffic light state is not green but the vehicle passed the intersection line
                    //then continue computation
                    if (lane_info.getCarsInLane().get(i).getCurrentSpeed() > 0 &&
                            lane_info.getCarsInLane().get(i).getDistanceFromCrossroad() < 0) {
                        car_computation.movingMode(add, lane_info.getSpeedLimit());

                        //if the traffic light is not green but the vehicle is at a safe distance from the front vehicle
                        //then continue computation at a slower speed
                    } else if (lane_info.getCarsInLane().get(i).getDistanceFromCrossroad() > 10) {
                        if (i == 0) {
                            car_computation.movingMode(add, lane_info.getSpeedLimit() / 5);
                        } else {
                            CarInfo front_car = lane_info.getCarsInLane().get(i - 1);
                            CarInfo current_car = lane_info.getCarsInLane().get(i);
                            double dist = current_car.getDistanceFromCrossroad() - (front_car.getDistanceFromCrossroad() + front_car.getCar().getLength());
                            dist = Math.abs(dist);
                            if (dist > Constants.SAFETY_DISTANCE - 1) {
                                car_computation.movingMode(add, lane_info.getSpeedLimit() / 5);
                            } else {
                                car_computation.stoppingMode(add);
                            }
                        }

                        //if the traffic light is not green but the vehicle is at a safe distance from the front vehicle
                        //then continue computation at a more slower speed
                    } else if (lane_info.getCarsInLane().get(i).getDistanceFromCrossroad() > 1) {
                        if (i == 0) {
                            car_computation.movingMode(add, lane_info.getSpeedLimit() / 7);
                        } else {
                            CarInfo front_car = lane_info.getCarsInLane().get(i - 1);
                            CarInfo current_car = lane_info.getCarsInLane().get(i);
                            double dist = current_car.getDistanceFromCrossroad() - (front_car.getDistanceFromCrossroad() + front_car.getCar().getLength());
                            dist = Math.abs(dist);
                            if (dist > Constants.SAFETY_DISTANCE - 1) {
                                car_computation.movingMode(add, lane_info.getSpeedLimit() / 7);
                            } else {
                                car_computation.stoppingMode(add);
                            }
                        }

                        //otherwise stop driving
                    } else {
                        car_computation.stoppingMode(add);
                    }
                }
            }

            //if all cars of this lane have passed the intersection then stop this lane computation
            if (lane_info.getCarsInLane().size() == 0) {
                stop_computation = true;
            }

            //TODO calculate sleep time more smart than now
            Instant after = Instant.now();
            long delta = Duration.between(before, after).toMillis(); //

            if (delta > 10) {
                delta = 10;
            }

            for (int i = 3; i < 10; i++) {
                if (delta < i) {
                    delta = i;
                    break;
                }
            }


            try {
                Thread.sleep((int) (10 - delta));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        System.out.println(this.getName() + " finished lane computation");
    }

    public void setMovingMode(boolean moving_mode) {
        this.moving_mode = moving_mode;
    }

    public void setNextLaneInfo(LaneInfo next_lane_info) {
        this.next_lane_info = next_lane_info;
    }
}
