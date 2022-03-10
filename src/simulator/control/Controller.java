package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

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
    	if(jo.has("events")) {
	    	JSONArray ja = jo.getJSONArray("events");         
	    	for(int i = 0; i < ja.length(); i++){             
				simulator.addEvent(ef.createInstance(ja.getJSONObject(i)));         
			}     
    	}
    	else
    		throw new IllegalArgumentException("JSON Object doesnt have \"events\" key");
	} 
    public void run(int n, OutputStream out){
        JSONObject jo= new JSONObject();
        JSONArray jsonArray = new JSONArray();
        PrintStream ps = new PrintStream(out);
        for(int i =0; i< n;i++){
            simulator.advance();
            jsonArray.put(simulator.report());
        }
        jo.put("states",jsonArray);
        ps.print(jo.toString(3));
    }
    public void reset(){
        simulator.reset();
    }

    //============================================================================
    //NUEVO
    //============================================================================

    public void addObserver(TrafficSimObserver o){
        simulator.addObserver(o);
    }

    public void removeObserver(TrafficSimObserver o){
        simulator.removeObserver(o);
    }

    public void addEvent(Event e){
        simulator.addEvent(e);
    }
}
