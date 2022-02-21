package simulator.factories;

import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

    RoundRobinStrategyBuilder(String type) {
        super(type);
    }

    @Override
    protected LightSwitchingStrategy createTheInstance(JSONObject data) {
        return new RoundRobinStrategy(data.has("timeslot") ? data.getInt("timeslot") : 1);
    }
}
