package Objects.Conditions;

import Objects.CrossroadInfo.CrossroadInfo;

public class Conditions {

    private CrossroadInfo crossroadInfo1;
    private CrossroadInfo crossroadInfo2;

    public Conditions(CrossroadInfo info1, CrossroadInfo info2){
        crossroadInfo1 = info1;
        crossroadInfo2 = info2;
    }

    public Conditions(Conditions conditions){
        crossroadInfo1 = new CrossroadInfo(conditions.getCrossroadInfo1());
        crossroadInfo2 = new CrossroadInfo(conditions.getCrossroadInfo2());
    }

    public CrossroadInfo getCrossroadInfo1() {
        return crossroadInfo1;
    }

    public CrossroadInfo getCrossroadInfo2() {
        return crossroadInfo2;
    }
}
