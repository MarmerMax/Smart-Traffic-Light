package Database;

public class DatabaseConditions {

    private int[] cars_first_crossroad; //= {12, 3, 43,13};
    private int[] cars_second_crossroad; //= {12, 4, 31, 2};

    private int[] speed_limit_first_crossroad; // = {50, 50, 50, 50}
    private int[] speed_limit_second_crossroad; // = {50, 50, 50, 50}

    private int[] actual_speed_first_crossroad; // = {50, 50, 50, 50}
    private int[] actual_speed_second_crossroad; // = {50, 50, 50, 50}

    private double initial_time; //123
    private double better_time; // 111
    private double simulation_time; //222
    private double better_distribtuion; // "15:5->10:10..."

    public void setCarsFirstCrossroad(int[] cars) {
        this.cars_first_crossroad = cars;
    }

    public void setCarsSecondCrossroad(int[] cars) {
        this.cars_second_crossroad = cars;
    }

    public void setActualSpeedFirstCrossroad(int[] actual_speed_first_crossroad) {
        this.actual_speed_first_crossroad = actual_speed_first_crossroad;
    }

    public void setActualSpeedSecondCrossroad(int[] actual_speed_second_crossroad) {
        this.actual_speed_second_crossroad = actual_speed_second_crossroad;
    }

    public void setSpeedLimitFirstCrossroad(int[] speed_limit_first_crossroad) {
        this.speed_limit_first_crossroad = speed_limit_first_crossroad;
    }

    public void setSpeedLimitSecondCrossroad(int[] speed_limit_second_crossroad) {
        this.speed_limit_second_crossroad = speed_limit_second_crossroad;
    }
}
