package Tools;

import Objects.Car.CarTypes;
import Objects.Conditions.Conditions;
import Objects.Crossroad.Crossroad;
import Objects.CrossroadInfo.CrossroadInfo;
import Objects.Road.RoadCreator;
import javafx.scene.control.Spinner;

import java.util.ArrayList;
import java.util.Random;

public class Utils {

    public static CarTypes createRandomCarType() {

        CarTypes type;
        double random = Math.random();

        if (random < 0.1) {
            type = CarTypes.Police;
        } else if (0.1 <= random && random < 0.5) {
            type = CarTypes.Taxi;
        }
//        else if (0.5 <= random && random < 0.6) {
//            type = CarTypes.Track;
//        } else if (0.6 <= random && random < 0.7) {
//            type = CarTypes.Ambulance;
//        }
        else {
            type = CarTypes.Usual;
        }

        return type;
    }

    public static double round(double value, int digits) {
        int places = digits;
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    public static double findRatio(double small_value, double big_value) {
        double coefficient = 1;
        while (small_value * coefficient < big_value) {
            coefficient += 0.01;
        }

        return coefficient;
    }

    public static double createRandomInRange(double min, double max) {
        Random r = new Random();
        double randomValue = min + (max - min) * r.nextDouble();

        return round(randomValue, 2);
    }

    public static Conditions createStartConditions() {
        Crossroad crossroad_1 = new Crossroad(RoadCreator.createRoads(54, 1));
        Crossroad crossroad_2 = new Crossroad(RoadCreator.createRoads(433, 1));

        CrossroadInfo crossroad_info_1 = new CrossroadInfo(crossroad_1);
        CrossroadInfo crossroad_info_2 = new CrossroadInfo(crossroad_2);

        int[] cars_start = {3, 3, 3, 3};
        int[] actual_start = {70, 70, 70, 70};
        int[] limit_start = {70, 70, 70, 70};

        crossroad_info_1.setCrossroadInfo(cars_start, actual_start, limit_start);
        crossroad_info_2.setCrossroadInfo(cars_start, actual_start, limit_start);

        Conditions conditions = new Conditions(crossroad_info_1, crossroad_info_2);

        return conditions;
    }

    public static void createRandomCarsCount(ArrayList<Spinner<Integer>> spinners, int exception_road) {
        for (int i = 0; i < spinners.size(); i++) {
            int min = Constants.CARS_COUNT_MIN;
            int max;
            int cars;
            if (i == exception_road) {
                max = Constants.CARS_COUNT_SHORT_ROAD_MAX;
                cars = (int) createRandomInRange(min, max);
                spinners.get(i).getValueFactory().setValue(cars);
            } else {
                max = Constants.CARS_COUNT_LONG_ROAD_MAX;
                cars = (int) createRandomInRange(min, max);
                spinners.get(i).getValueFactory().setValue(cars);
            }
        }
    }

    public static void createRandomSpeedLimit(ArrayList<Spinner<Integer>> spinners) {
        for (int i = 0; i < 4; i++) {
            int speed_limit = (int) createRandomInRange(Constants.SPEED_LIMIT_MIN, Constants.SPEED_LIMIT_MAX);
            speed_limit = ((speed_limit + 5) / 10) * 10;
            spinners.get(i).getValueFactory().setValue(speed_limit);
        }
    }

    public static void createRandomActualSpeed(ArrayList<Spinner<Integer>> spinners) {
        for (int i = 0; i < 4; i++) {
            int actual_speed = (int) createRandomInRange(Constants.ACTUAL_LIMIT_MIN, Constants.ACTUAL_LIMIT_MAX);
            actual_speed = ((actual_speed + 5) / 10) * 10;
            spinners.get(i).getValueFactory().setValue(actual_speed);
        }
    }

    public static void resetCarsCount(ArrayList<Spinner<Integer>> spinners, int exception_road) {
        for (int i = 0; i < spinners.size(); i++) {
            if (i == exception_road) {
                spinners.get(i).getValueFactory().setValue(Constants.CARS_COUNT_SHORT_ROAD);
            } else {
                spinners.get(i).getValueFactory().setValue(Constants.CARS_COUNT_LONG_ROAD);
            }
        }
    }

    public static void resetSpeedLimit(ArrayList<Spinner<Integer>> spinners) {
        for (int i = 0; i < 4; i++) {
            spinners.get(i).getValueFactory().setValue(Constants.SPEED_LIMIT);
        }
    }

    public static void resetActualSpeed(ArrayList<Spinner<Integer>> spinners) {
        for (int i = 0; i < 4; i++) {
            spinners.get(i).getValueFactory().setValue(Constants.ACTUAL_LIMIT);
        }
    }
}
