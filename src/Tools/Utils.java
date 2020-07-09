package Tools;

import Objects.Car.CarTypes;

import java.util.Random;

public class Utils {

    public static CarTypes createRandomCarType() {

        CarTypes type;
        double random = Math.random();

        if (random < 0.1) {
            type = CarTypes.Police;
        } else if (0.1 <= random && random < 0.2) {
            type = CarTypes.Ambulance;
        } else if (0.2 <= random && random < 0.5) {
            type = CarTypes.Taxi;
        } else if (0.5 <= random && random < 0.8) {
            type = CarTypes.Usual;
        } else {
            type = CarTypes.Track;
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

    public static double createRandomDistanceInRange(double min, double max){
        Random r = new Random();
        double randomValue = min + (max - min) * r.nextDouble();

        return round(randomValue, 2);
    }
}
