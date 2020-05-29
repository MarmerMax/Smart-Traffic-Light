package SystemSTL;

import Objects.SystemConditions.Conditions;

public class SystemSTL {

    private Conditions conditions;
    private Algorithm algorithm;

    public SystemSTL(Conditions conditions){
        algorithm = new Algorithm(conditions);
    }

//    public void setConditions(Conditions conditions) {
//        this.conditions = conditions;
//    }

    public void run() {
        calculateTimeDistribution();
    }

    private void calculateTimeDistribution() {
        algorithm.start();
    }
}
