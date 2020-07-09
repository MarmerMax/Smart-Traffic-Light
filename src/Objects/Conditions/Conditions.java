package Objects.Conditions;

import Objects.CrossroadInfo.CrossroadInfo;

public class Conditions {

    private CrossroadInfo crossroad_info_1;
    private CrossroadInfo crossroad_info_2;

    public Conditions(CrossroadInfo info1, CrossroadInfo info2) {
        crossroad_info_1 = info1;
        crossroad_info_2 = info2;
    }

    public Conditions(Conditions conditions) {
        crossroad_info_1 = new CrossroadInfo(conditions.getCrossroadInfo1());
        crossroad_info_2 = new CrossroadInfo(conditions.getCrossroadInfo2());
    }

    public void changeTrafficLightState() {
        crossroad_info_1.getCrossroad().changeTrafficLightStateOnCrossroad();
        crossroad_info_2.getCrossroad().changeTrafficLightStateOnCrossroad();
    }

    public boolean isEastWestActive() {
        return crossroad_info_1.getCrossroad().isEastWestActive() && crossroad_info_2.getCrossroad().isEastWestActive();
    }

    public boolean isNorthSouthActive() {
        return crossroad_info_1.getCrossroad().isNorthSouthActive() && crossroad_info_2.getCrossroad().isNorthSouthActive();
    }

    public double getNorthSouthTimeDistribution() {
        if (crossroad_info_1.getCrossroad().getTimeDistribution().getNorthSouth() !=
                crossroad_info_2.getCrossroad().getTimeDistribution().getNorthSouth()) {
            throw new RuntimeException("Bad time distribution...");
        }
        return crossroad_info_1.getCrossroad().getTimeDistribution().getNorthSouth();
    }

    public double getEastWestTimeDistribution() {
        if (crossroad_info_1.getCrossroad().getTimeDistribution().getEastWest() !=
                crossroad_info_2.getCrossroad().getTimeDistribution().getEastWest()) {
            throw new RuntimeException("Bad time distribution...");
        }
        return crossroad_info_1.getCrossroad().getTimeDistribution().getEastWest();
    }

    public double getChangingLightsTime(){
        return crossroad_info_1.getCrossroad().getTimeDistribution().getChangingExecutionTime();
    }

    public void addTimeToEastWestRoure(){
        crossroad_info_1.getCrossroad().getTimeDistribution().addTimeToEastWestRoute();
        crossroad_info_2.getCrossroad().getTimeDistribution().addTimeToEastWestRoute();
    }

    public void addTimeToNorthSouthRoute(){
        crossroad_info_1.getCrossroad().getTimeDistribution().addTimeToNorthSouthRoute();
        crossroad_info_2.getCrossroad().getTimeDistribution().addTimeToNorthSouthRoute();
    }

    public void setDefaultTimeDistribution(){
        crossroad_info_1.getCrossroad().getTimeDistribution().setDefaultDistribution();
        crossroad_info_2.getCrossroad().getTimeDistribution().setDefaultDistribution();
    }

    public CrossroadInfo getCrossroadInfo1() {
        return crossroad_info_1;
    }

    public CrossroadInfo getCrossroadInfo2() {
        return crossroad_info_2;
    }
}
