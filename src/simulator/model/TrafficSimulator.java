package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.SortedArrayList;

import java.util.Comparator;
import java.util.List;

public class TrafficSimulator implements Observable{
    private RoadMap roadMap;
    private List<Event> eventList;
    private int simulationTime = 0;
    private Comparator<Event> c;

    public TrafficSimulator(){
        roadMap = new RoadMap();

        c = new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                if(e1.getTime() < e2.getTime()){
                    return 1;
                }
                else if(e1.getTime() > e2.getTime()){
                    return -1;
                }
                return 0;
            }
        };
        eventList = new SortedArrayList<Event>(c);
       


    }
    public void addEvent(Event e){
        eventList.add(e);
    }
    
    public void advance(){
        simulationTime++;
        for(int i = 0; i < eventList.size(); i++){
            if(eventList.get(i).getTime() == simulationTime){
                eventList.get(i).execute(roadMap);
                eventList.remove(i);
                i--;
            }
        }
        for(int i = 0; i < roadMap.getJunctions().size(); i++){
            roadMap.getJunctions().get(i).advance(simulationTime);
        }
        for(int i = 0; i < roadMap.getRoads().size(); i++){
            roadMap.getRoads().get(i).advance(simulationTime);
        }
    }
    
    public void reset(){
        roadMap.reset();
        eventList.clear();
        simulationTime = 0;
    }

    public JSONObject report(){
        JSONObject trafficSimulatorReport = new JSONObject();

        trafficSimulatorReport.put("time",simulationTime);
        trafficSimulatorReport.put("state",roadMap.report());


        return  new JSONObject (trafficSimulatorReport.toString());
    }

    //============================================================================
    //NUEVO
    //============================================================================

    public void onAdvanceStart(){//TODO

    }

    public void onAdvanceEnd(){//TODO

    }

    public void onEventAdded(){//TODO

    }

    public void onReset(){//TODO

    }

    public void onRegister(){//TODO

    }

    public void onError(){//TODO

    }

    @Override
    public void addObserver(Object o) {//TODO

    }

    @Override
    public void removeObserver(Object o) {//TODO

    }
}
