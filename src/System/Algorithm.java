package System;

import Objects.SystemConditions.Conditions;

public class Algorithm {

    Conditions conditions;
    private double actual_execute_time;


    public Algorithm(Conditions conditions) {
        this.conditions = conditions;
        checkInitialState();
    }

    private void checkInitialState() {
        int time_unit = 1;
        int traffic_light_changing = 5;

//        int first_crossroad_south_cars = conditions.getCrossroadInfo1().getSouth().getCarsCount();
//        int first_crossroad_south_actual_speed = conditions.getCrossroadInfo1().getSouth().getActualSpeed();
//        int first_crossroad_south_limit_speed = conditions.getCrossroadInfo1().getSouth().getSpeedLimit();
//
//        int first_crossroad_north_cars = conditions.getCrossroadInfo1().getNorth().getCarsCount();
//        int first_crossroad_north_actual_speed = conditions.getCrossroadInfo1().getNorth().getActualSpeed();
//        int first_crossroad_north_limit_speed = conditions.getCrossroadInfo1().getNorth().getSpeedLimit();
//
//        int first_crossroad_east_cars = conditions.getCrossroadInfo1().getEast().getCarsCount();
//        int first_crossroad_east_limit_speed = conditions.getCrossroadInfo1().getEast().getSpeedLimit();
//        int first_crossroad_east_actual_speed = conditions.getCrossroadInfo1().getEast().getActualSpeed();
//
//        int first_crossroad_west_cars = conditions.getCrossroadInfo1().getWest().getCarsCount();
//        int first_crossroad_west_actual_speed = conditions.getCrossroadInfo1().getWest().getActualSpeed();
//        int first_crossroad_west_limit_speed = conditions.getCrossroadInfo1().getWest().getSpeedLimit();
//
//        int second_crossroad_south_cars = conditions.getCrossroadInfo2().getSouth().getCarsCount();
//        int second_crossroad_south_actual_speed = conditions.getCrossroadInfo2().getSouth().getActualSpeed();
//        int second_crossroad_south_limit_speed = conditions.getCrossroadInfo2().getSouth().getSpeedLimit();
//
//        int second_crossroad_north_cars = conditions.getCrossroadInfo2().getNorth().getCarsCount();
//        int second_crossroad_north_actual_speed = conditions.getCrossroadInfo2().getNorth().getActualSpeed();
//        int second_crossroad_north_limit_speed = conditions.getCrossroadInfo2().getNorth().getSpeedLimit();
//
//        int second_crossroad_east_cars = conditions.getCrossroadInfo2().getEast().getCarsCount();
//        int second_crossroad_east_actual_speed = conditions.getCrossroadInfo2().getEast().getActualSpeed();
//        int second_crossroad_east_limit_speed = conditions.getCrossroadInfo2().getEast().getSpeedLimit();
//
//        int second_crossroad_west_cars = conditions.getCrossroadInfo2().getWest().getCarsCount();
//        int second_crossroad_west_actual_speed = conditions.getCrossroadInfo2().getWest().getActualSpeed();
//        int second_crossroad_west_limit_speed = conditions.getCrossroadInfo2().getWest().getSpeedLimit();
//

        Conditions temp = new Conditions(conditions);

        int first_crossroad_total_cars =
                conditions.getCrossroadInfo1().getEast().getCarsCount() +
                        conditions.getCrossroadInfo1().getWest().getCarsCount() +
                        conditions.getCrossroadInfo1().getNorth().getCarsCount() +
                        conditions.getCrossroadInfo1().getSouth().getCarsCount();

        int second_crossroad_total_cars =
                conditions.getCrossroadInfo1().getEast().getCarsCount() +
                        conditions.getCrossroadInfo1().getWest().getCarsCount() +
                        conditions.getCrossroadInfo1().getNorth().getCarsCount() +
                        conditions.getCrossroadInfo1().getSouth().getCarsCount();

        while (first_crossroad_total_cars > 0 && second_crossroad_total_cars > 0) {
            //first direction two crossroad

            for (int i = 0; i < traffic_light_changing; i++) {

            }

            //second direction two crossroad
        }

    }

    public void run() {

    }


}
