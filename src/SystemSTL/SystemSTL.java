package SystemSTL;

import Objects.Conditions.Conditions;
import javafx.scene.layout.Pane;

public class SystemSTL {

    private Conditions conditions;
    private Algorithm algorithm;
    private boolean is_finished;

    public SystemSTL(Conditions conditions) {
        algorithm = new Algorithm(conditions);
        is_finished = false;
    }

    public void run() {
        algorithm.start();
    }

    public void stop() {
//        algorithm.stop();
    }

    public boolean isFinished() {
        return algorithm.getIsFinished();
    }
}
