package SystemSTL;

import Objects.Conditions.Conditions;
import SystemSTL.Algorithm.Algorithm;
import SystemSTL.TrafficComputation.TrafficComputation;
import SystemSTL.TrafficLightsComputation.TrafficLightsComputation;
import Tools.ConsoleColors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is the main core of the application.
 * It gets the road condition and runs all calculations.
 * From here, the calculation of traffic lights, traffic and algorithm will begin.
 */
public class SystemSTL {

    private ExecutorService executor;
    private boolean isStopped;

    private Conditions conditions;
    private Algorithm algorithm;


    private TrafficLightsComputation traffic_lights_computation;
    private TrafficComputation traffic_computation;

    /**
     * System STL - class that start all executors than will execute work of algorithms, traffic lights, vehicles changes.
     *
     * @param conditions
     */
    public SystemSTL(Conditions conditions) {
        this.conditions = conditions;
        this.isStopped = false;

        executor = Executors.newFixedThreadPool(3);

        algorithm = new Algorithm(conditions);
        traffic_computation = new TrafficComputation(conditions);
        traffic_lights_computation = new TrafficLightsComputation(conditions);
    }

    /**
     * This function start all computations.
     */
    public void run() {
        System.out.println(ConsoleColors.GREEN_BOLD + "[START]" + ConsoleColors.RESET);

        executor.execute(algorithm);
        executor.execute(traffic_computation);
        executor.execute(traffic_lights_computation);

    }

    public void stop() {
        algorithm.stopAlgorithm();
        traffic_computation.stopTrafficComputation();
        traffic_lights_computation.stopTrafficLightComputation();
        isStopped = true;

        System.out.println(ConsoleColors.RED_BOLD + "System was stopped!" + ConsoleColors.RESET);
    }

    /**
     * This function return flag about finish of executors working.
     *
     * @return
     */
    public boolean getIsFinished() {
        return conditions.isAllCarsPassed();
    }

    public boolean getIsStopped(){
        return isStopped;
    }
}
