package simulator.control;

import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

import java.io.InputStream;

public class Controller {
    private TrafficSimulator simulator;
    private Factory<Event> ef;

    public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
        if(sim == null)
            throw new IllegalArgumentException("TrafficSimulator parameter is null");
        if(eventsFactory == null)
            throw new IllegalArgumentException("eventsFactory parameter is null");
        simulator = sim;
        ef = eventsFactory;
    }

    public void loadEvents(InputStream in){
        JSONObject jo = new JSONObject(new JSONTokener(in));

    }
}
