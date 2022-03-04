package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Junction extends SimulatedObject{
    private List<Road> incomingRoadList;
    private Map<Junction,Road> outGoingRoadMap;
    private List<List<Vehicle>> queueList;
    private Map<Road,List<Vehicle>> queueMap;
    private int greenInd = -1;
    private int lastSwitchingTime = 0;
    private LightSwitchingStrategy lss;
    private DequeuingStrategy ds;
    private int xCoor, yCoor;

    Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
        super(id);
        if(lsStrategy != null && dqStrategy != null && xCoor >= 0 && yCoor >= 0){
            this.xCoor = xCoor;
            this.yCoor = yCoor;
            lss = lsStrategy;
            ds = dqStrategy;
        }
        else{
            throw new IllegalArgumentException("Datos introducidos en la Junction erroneos");
        }
        incomingRoadList = new ArrayList<Road>();
        outGoingRoadMap = new HashMap<Junction, Road>();
        queueList = new ArrayList<List<Vehicle>>();
        queueMap = new HashMap<Road, List<Vehicle>>();
    }

    void addIncomingRoad(Road r){
        if(r.getDest() != this)
            throw new IllegalArgumentException("The road doesn't lead to this junction");
        incomingRoadList.add(r);
        queueList.add(new LinkedList<Vehicle>());
        queueMap.put(r, queueList.get(queueList.size() - 1));
    }

    void addOutGoingRoad(Road r){
        if(outGoingRoadMap.containsKey(r.getDest()))
            throw new IllegalArgumentException("The road already exists");
        if(r.getSrc() != this)
            throw new IllegalArgumentException("The road doesn't come out from this junction");
        outGoingRoadMap.put(r.getDest(), r);
    }

    void enter(Vehicle v){
        if(!incomingRoadList.isEmpty()) {
            queueList.get(incomingRoadList.indexOf(v.getRoad())).add(v);
        }
    }

    Road roadTo(Junction j){
        return outGoingRoadMap.get(j);
    }

    @Override
    void advance(int time) {
        // Mover coches
    	if(greenInd != -1) {
	        List<Vehicle> aux = ds.dequeue(queueList.get(greenInd));
	        for(Vehicle v : aux){
	            queueList.get(greenInd).remove(v);
	            queueMap.get(incomingRoadList.get(greenInd)).remove(v);
	            v.moveToNextRoad();
	        }
    	}
        // Cambio de semáforo
        int newGreen = lss.chooseNextGreen(incomingRoadList, queueList, greenInd, lastSwitchingTime, time);
        if(newGreen != greenInd)
            lastSwitchingTime = time;
        greenInd = newGreen;
    }

    @Override
    public JSONObject report() {
        JSONArray array = new JSONArray();
        for(int i = 0; i < queueList.size(); i++){
            JSONObject jo = new JSONObject();
            jo.put("road", incomingRoadList.get(i).toString());
            JSONArray auxV = new JSONArray();
            for(int j = 0; j < queueList.get(i).size(); j++)
                auxV.put(queueList.get(i).get(j).toString());
            jo.put("vehicles", auxV);
            array.put(jo);
        }
        JSONObject road = new JSONObject();
        road.put("id", _id);
        road.put("green", greenInd == -1 ? "none" : incomingRoadList.get(greenInd));
        road.put("queues", array);
        return road;
    }

    public boolean connectedTo(Junction j){
        return outGoingRoadMap.containsKey(j);
    }
}
