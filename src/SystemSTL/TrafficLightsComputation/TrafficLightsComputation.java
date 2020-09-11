package SystemSTL.TrafficLightsComputation;

import Objects.Conditions.Conditions;
import Tools.ConsoleColors;

/**
 * This class is responsible for changing the states of the traffic light.
 */
public class TrafficLightsComputation extends Thread {

    private Conditions conditions;
    private int traffic_lights_working_time;
    private volatile boolean isStopped = false;

    public TrafficLightsComputation(Conditions conditions) {
        this.conditions = conditions;
        traffic_lights_working_time = 0;
    }

    @Override
    public void run() {
        updateTrafficLightsState();
        if (isStopped) {
            System.out.println(ConsoleColors.RED_BOLD + "Traffic lights computation was stopped!" + ConsoleColors.RESET);
        }
    }

    public void stopTrafficLightComputation() {
        isStopped = true;
    }

    /**
     * This function is responsible for working of traffic lights.
     * Function has to changes the traffic light states (green, yellow, red).
     */
    public void updateTrafficLightsState() {
        double time = 0;
        double changing_time = 0;

        int phase_count = 0;

        while (!conditions.isAllCarsPassed() && !isStopped) {

            traffic_lights_working_time++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //check if exists a better distribution. if yes then choose it times. happen only once per phase.
            //phase = north-south-state + red-state + east-west-state
            if (!conditions.isEastWestActive() && conditions.isNorthSouthActive() && phase_count % 3 == 0) {
                //once at phase time
                if (conditions.getBetterDistribution().size() != 0) {
                    phase_count = 1;
                    System.out.println(ConsoleColors.GREEN_BOLD + "New time was set" + ConsoleColors.RESET);
                    conditions.setTimeDistribution(conditions.getNextDistribution());
                    int phases_left_count = conditions.getBetterDistribution().size();
                    System.out.println(ConsoleColors.YELLOW + "(" + phases_left_count + " phases left)" + ConsoleColors.RESET);
                }
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
        conditions.setSimulationDuration(traffic_lights_working_time);
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
