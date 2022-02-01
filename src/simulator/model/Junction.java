package simulator.model;

import org.json.JSONObject;

public class Junction extends SimulatedObject{
    Junction(String id) {
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
