package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

import java.io.InputStream;
import java.io.OutputStream;

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
        Event e;
        JSONObject jo = new JSONObject(new JSONTokener(in));
        e = ef.createInstance(jo);
        simulator.addEvent(e);
        //?¿¿¿?¿¿?¿?¿?¿?¿Este método debe lanzar una excepción si la entrada JSON no encaja con la de arriba
    }
    public void run(int n, OutputStream out){
        JSONObject jo= new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(int i =0; i< n;i++){
            simulator.advance();
            jsonArray.put(simulator.report());
        }
        jo.put("states",jsonArray);
    }
    public void reset(){
        simulator.reset();
    }
}
