package SystemSTL;

import Objects.Conditions.Conditions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SystemSTL {

    private ExecutorService executor;

    private Conditions conditions;
    private Algorithm algorithm;


    private TrafficLightsComputation traffic_lights_computation;
    private TrafficComputation traffic_computation;

    /**
     * System STL - class that start all executors than will execute work of algorithms, traffic lights, vehicles changes.
     * @param conditions
     */
    public SystemSTL(Conditions conditions) {
        this.conditions = conditions;

        executor = Executors.newFixedThreadPool(4);

        algorithm = new Algorithm(conditions);
        traffic_computation = new TrafficComputation(conditions);
        traffic_lights_computation = new TrafficLightsComputation(conditions);

        this.conditions.setInitialDuration(traffic_computation.getInitialDuration());
    }

    /**
     * This function start all computations.
     */
    public void run() {
        executor.execute(algorithm);
        executor.execute(traffic_computation);
        executor.execute(traffic_lights_computation);

        executor.shutdown();
    }

    public void stop() {
//        algorithm.stop();
    }

    /**
     * This function return flag about finish of executors working.
     * @return
     */
    public boolean isFinished() {
        return conditions.isAllCarsPassed();
    }
}
