package SystemSTL;

import Objects.Conditions.Conditions;

public class TrafficLightsComputation extends Thread{

    private Conditions conditions;
    private int traffic_lights_working_time;

    public TrafficLightsComputation(Conditions conditions) {
        this.conditions = conditions;
        traffic_lights_working_time = 0;
    }

    @Override
    public void run() {
        updateTrafficLightsState();
    }

    /**
     * This function is responsible for working of  traffic lights.
     */
    public void updateTrafficLightsState() {
        System.err.println("[START]");

        double time = 0;
        double changing_time = 0;

        //must to be in thread
        while (!conditions.isAllCarsPassed()) {

            traffic_lights_working_time++;
//            updateTrafficLightsTimeDistributions();//wrong place

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //main road active
            if (conditions.isEastWestActive()) {
//                System.out.println("EAST AND WEST ACTIVE");
                if (conditions.getEastWestTimeDistribution() < time) {
                    //stop moving
                    conditions.changeTrafficLightState();
                }
                time++;
            }

            //others road active
            else if (conditions.isNorthSouthActive()) {
//                System.out.println("NORTH AND SOUTH ACTIVE");
                if (conditions.getNorthSouthTimeDistribution() < time) {
                    //stop moving
                    conditions.changeTrafficLightState();
                }
                time++;
            }

            //all road are stop
            else {

                time = 0;
//                System.out.println("ALL STOPS");
                if (changing_time < conditions.getChangingLightsTime()) {
                    changing_time++;
                } else {
                    changing_time = 0;
                    conditions.changeTrafficLightState();
                }
            }
        }
    }

    /**
     * This function return duration of actual state with smart algorithm.
     * @return actual duration
     */
    public int getTrafficLightsWorkingTime() {
        return traffic_lights_working_time;
    }
}
