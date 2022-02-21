package simulator.factories;

import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;
import simulator.model.RoundRobinStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {
    MostCrowdedStrategyBuilder(String type) {
        super(type);
    }

    @Override
    protected LightSwitchingStrategy createTheInstance(JSONObject data) {
        return new MostCrowdedStrategy(data.has("timeslot") ? data.getInt("timeslot") : 1);
    }
}
