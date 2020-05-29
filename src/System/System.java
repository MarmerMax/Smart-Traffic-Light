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
}
