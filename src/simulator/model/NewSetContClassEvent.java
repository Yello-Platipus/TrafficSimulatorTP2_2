package simulator.model;

import simulator.misc.Pair;
import java.util.List;

public class NewSetContClassEvent extends Event{
    public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs) {
        super(time);
    }

    @Override
    void execute(RoadMap map) {

    }
}
