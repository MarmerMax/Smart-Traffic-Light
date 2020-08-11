package SystemSTL;

import Objects.Conditions.Conditions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SystemSTL {

    private Conditions conditions;
    private Algorithm algorithm;
    private TrafficLightsComputation traffic_lights_computation;
    private TrafficComputation traffic_computation;
    private boolean is_finished;
    private double initial_duration;
    private double actual_duration;
    private ExecutorService executor;

    public SystemSTL(Conditions conditions) {
        this.conditions = conditions;

        executor = Executors.newFixedThreadPool(3);

        algorithm = new Algorithm(conditions);
        traffic_computation = new TrafficComputation(conditions);
        traffic_lights_computation = new TrafficLightsComputation(conditions);

        initial_duration = traffic_computation.getInitialDuration();
//        System.out.println("initial time for passed all cars: " + initial_duration);
        is_finished = false;
    }

    public void run() {
        executor.execute(algorithm);
        executor.execute(traffic_computation);
        executor.execute(traffic_lights_computation);

        executor.shutdown();
        actual_duration = traffic_lights_computation.getTrafficLightsWorkingTime();
    }

    public void stop() {
//        algorithm.stop();
    }

    public boolean isFinished() {
        return conditions.isAllCarsPassed();
    }

    public double getInitialDuration() {
        return initial_duration;
    }

    /**
     * This function return duration of actual state with smart algorithm.
     * @return actual duration
     */
    public double getActualDuration() {
        return actual_duration;
    }
}
