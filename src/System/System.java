package System;

import Objects.SystemConditions.Conditions;

public class System {

    private Conditions conditions;

    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }

    public void run() {
        calculateTimeDistribution();
    }

    private void calculateTimeDistribution() {
        Algorithm algo = new Algorithm(conditions);
        algo.run();
    }

    public double convertKilometersPefHourToMetersPerSecond(int speed) {
        return (speed * 1000) / 3600;
    }
}
