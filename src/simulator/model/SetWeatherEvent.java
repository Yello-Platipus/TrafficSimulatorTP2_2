package simulator.model;

import simulator.misc.Pair;
import java.util.List;

public class SetWeatherEvent extends Event{
    public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
        super(time);
    }

    @Override
    void execute(RoadMap map) {

    }
}
