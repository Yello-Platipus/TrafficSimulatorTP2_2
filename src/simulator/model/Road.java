package simulator.model;

import org.json.JSONObject;

public class Road extends SimulatedObject{
    Road(String id) {
        super(id);
    }

    @Override
    void advance(int time) {

    }

    @Override
    public JSONObject report() {
        return null;
    }
}
