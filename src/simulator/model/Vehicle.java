package simulator.model;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{
    Vehicle(String id) {
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
