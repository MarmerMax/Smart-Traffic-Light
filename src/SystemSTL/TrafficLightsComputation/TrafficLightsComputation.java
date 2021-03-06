package SystemSTL.TrafficLightsComputation;

import Objects.Conditions.Conditions;
import Tools.ConsoleColors;

/**
 * This class is responsible for changing the states of the traffic light.
 */
public class TrafficLightsComputation extends Thread {

    private Conditions conditions;
    private int traffic_lights_working_time;
    private volatile boolean isStopped;

    public TrafficLightsComputation(Conditions conditions) {
        this.conditions = conditions;
        isStopped = false;
        traffic_lights_working_time = 0;
    }

    @Override
    public void run() {
        System.out.println(ConsoleColors.PURPLE_BOLD + "[Traffic lights computation started]" + ConsoleColors.RESET);
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

        int phase_state = 0;
        int phases_count = 0;

        while (!conditions.isAllCarsPassed() && !isStopped) {

            traffic_lights_working_time++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //check if exists a better distribution. if yes then choose it times. happen only once per phase.
            //phase = north-south-state + red-state + east-west-state
            if (!conditions.isEastWestActive() && conditions.isNorthSouthActive() && phase_state % 3 == 0) {
                //once at phase time
                if (conditions.getBetterDistribution().size() != 0) {
                    phase_state = 1;
                    System.out.println(ConsoleColors.GREEN_BOLD + "New time was set" + ConsoleColors.RESET);
                    conditions.setTimeDistribution(conditions.getNextDistribution());
                    int phases_left_count = conditions.getBetterDistribution().size();
                    System.out.println(ConsoleColors.YELLOW + "(" + phases_left_count + " phases left)" + ConsoleColors.RESET);
                    phases_count++;
                }
            }

            if (conditions.isEastWestActive()) {

                if (conditions.getEastWestTimeDistribution() < time) {
                    conditions.changeTrafficLightState();
                    phase_state++;
                }
                time++;
            } else if (conditions.isNorthSouthActive()) {

                if (conditions.getNorthSouthTimeDistribution() < time) {
                    conditions.changeTrafficLightState();
                    phase_state++;
                }
                time++;
            }

            //all traffic lights are not green
            else {

                time = 0;
                if (changing_time < conditions.getChangingLightsTime()) {
                    changing_time++;
                } else {
                    changing_time = 0;
                    conditions.changeTrafficLightState();
                }
            }

            if (conditions.isAllCarsPassed()) {
                conditions.setSimulationDuration(traffic_lights_working_time);
                conditions.setPhasesCount(phases_count);
            }
        }
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
