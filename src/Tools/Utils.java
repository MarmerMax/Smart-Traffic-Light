package Tools;

import Objects.Car.CarTypes;

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
}
