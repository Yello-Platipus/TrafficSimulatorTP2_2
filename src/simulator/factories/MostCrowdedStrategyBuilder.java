package simulator.factories;

import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {
    MostCrowdedStrategyBuilder(String type) {
        super(type);
    }

    @Override
    protected LightSwitchingStrategy createTheInstance(JSONObject data) {
        return null;
    }
}
