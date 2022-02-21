package simulator.factories;

import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

import java.util.Deque;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {
    MoveFirstStrategyBuilder(String type) {
        super(type);
    }

    @Override
    protected DequeuingStrategy createTheInstance(JSONObject data) {
        return new MoveFirstStrategy();
    }
}
