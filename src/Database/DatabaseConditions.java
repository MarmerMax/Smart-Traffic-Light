package Database;

import Tools.Utils;

public class DatabaseConditions {

    private int[] cars_first_crossroad; //= {12, 3, 43, 13};
    private int[] cars_second_crossroad; //= {12, 4, 31, 2};

    private int[] speed_limit_first_crossroad; // = {50, 50, 50, 50}
    private int[] speed_limit_second_crossroad; // = {50, 50, 50, 50}

    private int[] actual_speed_first_crossroad; // = {50, 50, 50, 50}
    private int[] actual_speed_second_crossroad; // = {50, 50, 50, 50}

    private double initial_time; //123
    private double better_time; // 111
    private double simulation_time; //222

    private double initial_aws;
    private double better_aws;
    
    private double phase_time;

    private String better_distribution; // "15:5->10:10..."

    public DatabaseConditions(int[] cars_first_crossroad,
                              int[] cars_second_crossroad,
                              int[] speed_limit_first_crossroad,
                              int[] speed_limit_second_crossroad,
                              int[] actual_speed_first_crossroad,
                              int[] actual_speed_second_crossroad,
                              double initial_time,
                              double better_time,
                              double simulation_time,
                              double initial_aws,
                              double better_aws,
                              double phase_time,
                              String better_distribution) {

        this.cars_first_crossroad = cars_first_crossroad;
        this.cars_second_crossroad = cars_second_crossroad;

        this.speed_limit_first_crossroad = speed_limit_first_crossroad;
        this.speed_limit_second_crossroad = speed_limit_second_crossroad;

        this.actual_speed_first_crossroad = actual_speed_first_crossroad;
        this.actual_speed_second_crossroad = actual_speed_second_crossroad;

        this.initial_time = initial_time;
        this.better_time = better_time;
        this.simulation_time = simulation_time;

        this.initial_aws = initial_aws;
        this.better_aws = better_aws;
        
        this.phase_time = phase_time;

        this.better_distribution = better_distribution;
    }


    public int[] getCarsFirstCrossroad() {
        return cars_first_crossroad;
    }

    public int[] getCarsSecondCrossroad() {
        return cars_second_crossroad;
    }

    public int[] getSpeedLimitFirstCrossroad() {
        return speed_limit_first_crossroad;
    }

    public int[] getSpeedLimitSecondCrossroad() {
        return speed_limit_second_crossroad;
    }

    public int[] getActualSpeedFirstCrossroad() {
        return actual_speed_first_crossroad;
    }

    public int[] getActualSpeedSecondCrossroad() {
        return actual_speed_second_crossroad;
    }

    public double getInitialTime() {
        return initial_time;
    }

    public double getBetterTime() {
        return better_time;
    }

    public double getInitialAWS() {
        return initial_aws;
    }

    public double getBetterAWS() {
        return better_aws;
    }

    public double getPhaseTime() {
    	return phase_time;
    }
    
    public String toString() {
    	String res = "";
    	res += "First crossroad: \n"
    			+ "cars : " + Utils.arrayToString(cars_first_crossroad) + "\n"
    			+ "speed limit : " + Utils.arrayToString(speed_limit_first_crossroad) + "\n" 
    			+ "actual speed : " + Utils.arrayToString(actual_speed_first_crossroad) + "\n"
    			+ "Second crossroad: \n"
    			+ "cars : " + Utils.arrayToString(cars_second_crossroad) + "\n"
    			+ "speed limit : " + Utils.arrayToString(speed_limit_second_crossroad) + "\n" 
    			+ "actual speed : " + Utils.arrayToString(actual_speed_first_crossroad) + "\n";
    	return res;
    }
}
