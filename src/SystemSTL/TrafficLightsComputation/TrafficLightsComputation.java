package SystemSTL.TrafficLightsComputation;

import Objects.Conditions.Conditions;
import SystemSTL.SystemSTL;

/**
 * This class is responsible for changing the states of the traffic light.
 */
public class TrafficLightsComputation extends Thread {

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
     * This function is responsible for working of traffic lights.
     * Function has to changes the traffic light states (green, yellow, red).
     */
    public void updateTrafficLightsState() {
        System.err.println("[START]");

        double time = 0;
        double changing_time = 0;

        int phase_count = 0;

        while (!conditions.isAllCarsPassed()) {

            traffic_lights_working_time++;

            System.out.println("north-south: " + conditions.getFirstCrossroadInfo().getCrossroad().getTimeDistribution().getNorthSouth());
            System.out.println("east-west: " + conditions.getFirstCrossroadInfo().getCrossroad().getTimeDistribution().getEastWest());

            //check if exists a better distribution. if yes then choose it times. happen only once per phase.
            //phase = north-south-state + red-state + east-west-state
            if (!conditions.isEastWestActive() && conditions.isNorthSouthActive() && phase_count % 3 == 0) {
                //once at phase time
                if (this.conditions.getBetterDistribution().size() != 0) {
                    phase_count = 1;
                    this.conditions.setTimeDistribution(this.conditions.getNextDistribution());
                    System.err.println("SET SMART TIME");
                }
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (conditions.isEastWestActive()) {

                if (conditions.getEastWestTimeDistribution() < time) {
                    conditions.changeTrafficLightState();
                    phase_count++;
                }
                time++;
            } else if (conditions.isNorthSouthActive()) {

                if (conditions.getNorthSouthTimeDistribution() < time) {
                    conditions.changeTrafficLightState();
                    phase_count++;
                }
                time++;
            }

            //all road are stop
            else {

                time = 0;
                if (changing_time < conditions.getChangingLightsTime()) {
                    changing_time++;
                } else {
                    changing_time = 0;
                    conditions.changeTrafficLightState();
                }
            }
        }

        conditions.setAlgorithmDuration(traffic_lights_working_time);

        System.out.println("ALL CARS IS PASSED");
    }

    /**
     * This function return duration of actual state with smart algorithm.
     *
     * @return actual duration
     */
    public int getTrafficLightsWorkingTime() {
        return traffic_lights_working_time;
    }
}
